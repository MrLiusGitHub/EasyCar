package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.ldc.easycar.R;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activity_item.TupianActivity;
import adapter.TupianFragmentAdapter;
import bean.TupianBean;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import utils.OKHttpUtils;

/**
 * Created by Mr.Liu on 2016/6/6.
 */
public class TupianFragment extends BaseFragment {

    private List<TupianBean.DataItemBean> data = new ArrayList<>();
    private TupianFragmentAdapter adapter;

    private Handler handler = new Handler();

    private int pager = 1;

    private PtrClassicFrameLayout ptrClassicFrameLayout;


    public static TupianFragment getInstance(String str, String str1) {
        TupianFragment fragment = new TupianFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", str);
        bundle.putString("url1", str1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {


        ptrClassicFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
            }
        });


        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(getUrl());
                Log.e("123", getUrl());
                pager++;
                ptrClassicFrameLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        ptrClassicFrameLayout.refreshComplete();
                    }
                });
            }
        });

    }


    private void getData(String url) {

        OKHttpUtils.buildGetAsync(getActivity(), url, "tupian", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                final TupianBean bean = new Gson().fromJson(result, TupianBean.class);

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        data.addAll(data.size(), bean.data);

                        adapter.setData(data);
                    }
                });
            }
        });
    }

    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recylerView_base);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_base);

        adapter = new TupianFragmentAdapter(getActivity(), data, new TupianFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(String url,String pinglun) {

                Intent intent = new Intent(getActivity(), TupianActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("pinglun",pinglun);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

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
                        getData(getUrl());
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

    public String getUrl() {

        String url = getArguments().getString("url") + pager + getArguments().getString("url1");

        return url;

    }

    ;
}
