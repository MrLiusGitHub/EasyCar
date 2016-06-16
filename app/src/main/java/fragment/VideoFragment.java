package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ldc.easycar.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activity_item.SameActivity;
import adapter.VideoAdapter;
import bean.VedioBean;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import utils.OKHttpUtils;

/**
 * Created by Mr.Liu on 2016/6/9.
 */
public class VideoFragment extends Fragment {

    List<VedioBean.DataBean.ListBean> data = new ArrayList<>();

    String url1 = "http://api.ycapp.yiche.com/video/GetAppVideoList/?pageindex=";
    String url2 = "&pagesize=20&plat=1";
    int pager = 1;
    private VideoAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, null);


        initView(view);

        return view;

    }

    private void initView(View view) {
        ListView list = (ListView) view.findViewById(R.id.list_vedio);
        final PtrClassicFrameLayout ptrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_video);

        ptrClassicFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh();
                ptrClassicFrameLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        getData(getUrl());
                       // ptrClassicFrameLayout.refreshComplete();
                    }
                });
            }
        });

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                ptrClassicFrameLayout.refreshComplete();
            }
        });

        adapter = new VideoAdapter(getActivity(), data);

        list.setAdapter(adapter);

        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            boolean isBottom = false;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && isBottom){
                    pager++;
                    getData(getUrl());
                    adapter.setData(data);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom = firstVisibleItem+visibleItemCount == totalItemCount;
            }
        });


        //list点击跳转
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SameActivity.class);

                intent.putExtra("isVideo",true);

                if(data.get(position).user == null){
                    intent.putExtra("same",
                            "http://h5.ycapp.yiche.com/newvideo/"+data.get(position).videoid+
                                    ".html?plat=1&appver=7.1&lastmodify="+data.get(position).modifytime);
                }else {
                    intent.putExtra("same",
                            "http://h5.ycapp.yiche.com/newvideo/"+data.get(position).user.userId+"/"+data.get(position).videoid+
                                    ".html?plat=1&appver=7.1&lastmodify="+data.get(position).modifytime);
                }

                startActivity(intent);
            }
        });
    }

    public void getData(final String url){
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = OKHttpUtils.buildGetString(url, "vedio", getActivity());

                    VedioBean bean = new Gson().fromJson(result, VedioBean.class);

                    data.addAll(data.size(), bean.data.list);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String getUrl() {

        String url = url1 + pager + url2;

        return url;
    }
}
