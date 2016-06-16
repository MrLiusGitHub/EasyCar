package com.example.ldc.easycar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.XcXcAdapter;
import bean.XinCheBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import utils.OKHttpUtils;

public class XinCheActivity extends AppCompatActivity {

    @InjectView(R.id.xc_xinche_listview)
    ListView mListview;
    @InjectView(R.id.iv_back_xc)
    ImageView ivBackXc;

    private String url = "http://api.ycapp.yiche.com/car/getserialinfoforNew";
    private Handler hander = new Handler();
    private List<XinCheBean.ItemBean> mData = new ArrayList<>();
    private XcXcAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinche);
        ButterKnife.inject(this);

        initData();

        initView();

    }

    private void initView() {

        Log.e("TAG", "onCreate: " + mData);
        adapter = new XcXcAdapter(this, mData);
        mListview.setAdapter(adapter);
        ivBackXc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XinCheActivity.this,XuancheActivity.class));
                XinCheActivity.this.finish();
            }
        });
    }

    private void initData() {

        OKHttpUtils.buildGetAsync(this, url, "xinche", new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                final XinCheBean bean = new Gson().fromJson(json, XinCheBean.class);
                hander.post(new Runnable() {
                    @Override
                    public void run() {
                        mData.addAll(bean.data);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }
}
