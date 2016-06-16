package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ldc.easycar.R;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.PaiHangAdapter;
import bean.PaiHangBean;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import utils.OKHttpUtils;

/**
 * Created by ldc on 2016/6/8.
 */
public class PaiHangFragment extends BaseFragment{

    private String start_url="http://api.ycapp.yiche.com/indexdata/GetSerialSaleDataList/?cityid=0&carlevel=";
    private String end_url="&month=2016-04-01&page=1&length=20";
    private List<PaiHangBean.DataCount.ListDataBean> mData=new ArrayList<>();
    private RecyclerView recyclerView;
    private PaiHangAdapter adapter;
    private Handler hander=new Handler();
    private PtrClassicFrameLayout ptr;
    private String url;


    public static PaiHangFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type",type);
        PaiHangFragment fragment = new PaiHangFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 网络请求获得数据源，并把数据赋给fragment中的relativelayout
     */
    @Override
    protected void initData() {
        int type=getArguments().getInt("type");
        url = start_url.concat(type+"").concat(end_url);

        OKHttpUtils.buildGetAsync(getActivity(), url, "paihang", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json=response.body().string();
                final PaiHangBean paiHangBean=new Gson().fromJson(json, PaiHangBean.class);
                hander.post(new Runnable() {
                    @Override
                    public void run() {
                        mData=paiHangBean.data.list;
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * 初始化布局
     * @param view
     */
    @Override
    protected void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView_base);
        ptr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_base);
        ptr.post(new Runnable() {
            @Override
            public void run() {
                ptr.autoRefresh();
            }
        });

        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                long start =System.currentTimeMillis();
                getData(url);
                long end =System.currentTimeMillis();
                if (end -start <1500){
                    hander.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ptr.refreshComplete();
                        }
                    },1500-end+start);
                }else{
                    hander.post(new Runnable() {
                        @Override
                        public void run() {
                            ptr.refreshComplete();
                        }
                    });
                }
            }
        });
        adapter = new PaiHangAdapter(getActivity(),mData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getData(String url) {

        OKHttpUtils.buildGetAsync(getActivity(), url, "tupian", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                final PaiHangBean paiHangBean=new Gson().fromJson(json, PaiHangBean.class);

                hander.post(new Runnable() {
                    @Override
                    public void run() {
                        mData.addAll(0,paiHangBean.data.list);
                        adapter.setData(mData);
                    }
                });
            }
        });
    }
}
