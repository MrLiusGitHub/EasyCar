package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldc.easycar.R;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import adapter.JieGuoAdapter;
import bean.JieGuoBean;
import utils.OKHttpUtils;

/**
 * Created by ldc on 2016/6/9.
 */
public class JieGuoFragment extends Fragment{

    private List<JieGuoBean.ListBean> data;
    private Handler handler=new Handler();
    private JieGuoAdapter adapter;

    public static JieGuoFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url",url);
        JieGuoFragment fragment = new JieGuoFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jieguo, null);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public void initData(){
        String url=getArguments().getString("url");
        OKHttpUtils.buildGetAsync(getActivity(), url, "jieguo", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json=response.body().string();
                final JieGuoBean bean=new Gson().fromJson(json, JieGuoBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        data = bean.data.List;
                        adapter.setData(data);
                    }
                });
            }
        });
    }

    public void initView(View view){
        RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.jieguo_recyclerview);
        adapter = new JieGuoAdapter(getActivity(),data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }
}
