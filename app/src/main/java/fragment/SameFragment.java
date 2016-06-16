package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.ldc.easycar.R;

import activity_item.SameActivity;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.SameFragmentAdapter;
import bean.SameBean;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import utils.OKHttpUtils;
import viewholder.HeaderVHolder;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class SameFragment extends BaseFragment {

    int pager = 1;

    private SameFragmentAdapter adapter;
    private PtrClassicFrameLayout ptrClassicFrameLayout;

    private List<SameBean.ListBean> data = new ArrayList<>();

    private Handler handler = new Handler();

    Map<String, String> post = new HashMap<>();

    LayoutInflater inflater;

    private boolean isTop;
    private boolean isHaveHead;
    private boolean isPost;

    private boolean isTopFlag = false;

    //private boolean isTopOne = false;

    public static SameFragment getInstance(String str, String str1, boolean isTop, boolean isHaveHeader, boolean isPost) {
        SameFragment fragment = new SameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", str);
        bundle.putString("url1", str1);
        bundle.putBoolean("isTop", isTop);
        bundle.putBoolean("isHaveHeader", isHaveHeader);
        bundle.putBoolean("isPost", isPost);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SameFragment getInstance(String str, boolean isTop, boolean isHaveHeader, boolean isPost) {
        SameFragment fragment = new SameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", str);
        bundle.putBoolean("isTop", isTop);
        bundle.putBoolean("isHaveHeader", isHaveHeader);
        bundle.putBoolean("isPost", isPost);
        fragment.setArguments(bundle);
        return fragment;
    }


    /**
     *
     * 判断
     * 是否添加头部 是否在头部加载更多 GET还是POST
     *
     */
    @Override
    protected void initData() {

        inflater = LayoutInflater.from(getActivity());

        isTop = getArguments().getBoolean("isTop");
        isHaveHead = getArguments().getBoolean("isHaveHeader");
        isPost = getArguments().getBoolean("isPost");

        if (isTop){
            adapter.setCommond(true);
        }else {
            adapter.setCommond(false);
        }

        if (isHaveHead) {
            adapter.setHeadView(new HeaderVHolder(inflater.inflate(R.layout.view_head, null)));
            if (getArguments().getString("url1") != null){
                adapter.setFirstPager(true);
            }else {
                adapter.setFirstPager(false);
            }
        }

    }

    /**
     *
     * 1 通过GET请求数据
     * 2 将数据添加进数据源
     * 3 通知适配器刷新
     *
     * @param url
     */

    private void getData(String url) {

        OKHttpUtils.buildGetAsync(getActivity(), url, "tupian", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                final SameBean bean = new Gson().fromJson(result, SameBean.class);

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (isTop) {
                            data.addAll(0, bean.data.list);
                        } else {
                            data.addAll(data.size(), bean.data.list);
                        }

                        adapter.setData(data);
                    }
                });
            }
        });
    }

    /**
     *
     * 1 通过GET请求数据
     * 2 将数据添加进数据源
     * 3 通知适配器刷新
     *
     * @param url
     */
    private void getDataPost(final String url) {

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result = OKHttpUtils.postKeyValue(getActivity(), post, url, "post");

                    final SameBean bean = new Gson().fromJson(result, SameBean.class);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            if (isTop) {
                                data.addAll(0, bean.data.list);
                            } else {
                                data.addAll(data.size(), bean.data.list);
                            }

                            adapter.setData(data);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }


    /**
     *
     * 1 初始化控件
     * 2 自动刷新
     * 3 上拉加载
     *
     * @param view
     */
    @Override
    protected void initView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recylerView_base);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_base);

        //自动刷新
        ptrClassicFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
                ptrClassicFrameLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isPost) {
                            post.put("pageindex", pager + "");
                            getDataPost(getUrl());
                        } else {
                            getData(getUrl());
                        }
                        //ptrClassicFrameLayout.refreshComplete();
                    }
                });

            }
        });


        //刷新监听
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                Log.e("123", getUrl());

                if (isTopFlag){
                    isTop = true;
                }
                if(isTop){
                    getData(getUrl());
                }

                //pager++;
                ptrClassicFrameLayout.refreshComplete();
            }
        });

        adapter = new SameFragmentAdapter(getActivity(), data);
        recyclerView.setAdapter(adapter);

        //滑动监听
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingBottom = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    int isLastItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    if (isLastItem == (totalItemCount - 1) && isSlidingBottom) {

                        pager++;
                        if (isPost) {
                            post.clear();
                            post.put("pageindex", pager + "");
                            getDataPost(getUrl());
                        } else {
                            if (isTop){
                                isTopFlag = true;
                                isTop = false;
                            }
                            getData(getUrl());
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    isSlidingBottom = true;
                } else {
                    isSlidingBottom = false;
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }


    /**
     *
     * 获取URL
     *
     * @return
     */
    String getUrl() {
        if (getArguments().getString("url1") != null) {
            String url = getArguments().getString("url") + pager + getArguments().getString("url1");
            return url;
        } else {
            String url = getArguments().getString("url");
            return url;
        }
    }
}
