package com.example.ldc.easycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import activity_item.SameActivity;
import activity_item.StoreActivtiy;
import adapter.LVFuwuAdapter;
import adapter.RVFuwuAdapter;
import bean.RVFuwuBean;
import bean.VedioBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import constants.Constants;
import mapp.ExitApplication;
import utils.OKHttpUtils;

public class FuwuActivity extends AppCompatActivity {

    @InjectView(R.id.iv_toutiao)
    ImageView ivToutiao;
    @InjectView(R.id.iv_shequ)
    ImageView ivShequ;
    @InjectView(R.id.iv_xuanche)
    ImageView ivXuanche;
    @InjectView(R.id.iv_fuwu)
    ImageView ivFuwu;
    @InjectView(R.id.iv_wode)
    ImageView ivWode;
    @InjectView(R.id.iv_ad_1)
    ImageView ivAd1;
    @InjectView(R.id.iv_ad_2)
    ImageView ivAd2;

    private List<RVFuwuBean.DataBean> data = new ArrayList<>();
    private List<VedioBean.DataBean.ListBean> data_lv= new ArrayList<>();
    private String url = "http://api.ycapp.yiche.com/UseCar/GetServicesTagsByCityId?cityId=2401&plat=1";
    private RVFuwuAdapter adapter;

    private RecyclerView rvFuwu;
    private LVFuwuAdapter adapter_lv;
    private ListView lvFuwu;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplication());
        setContentView(R.layout.activity_fuwu);

        ExitApplication.getInstance().addActivity(this);
        ButterKnife.inject(this);
        ivFuwu.setEnabled(false);

        initLocation();
        initViews();
        initData();

    }

    private void initLocation() {

        LocationClient mLocationCLient = new LocationClient(getApplicationContext());
        mLocationCLient.registerLocationListener(new MyListener());
        mLocationCLient.start();

    }

    class MyListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //通过location对象可以判断出定位的类型
            int type = location.getLocType(); //返回定位的类型
            if(type == BDLocation.TypeGpsLocation) { //说明GPS定位成功
                Toast.makeText(getApplicationContext(), "GPS定位成功",
                        Toast.LENGTH_LONG).show();
                showLocation(location);
            } else if(type == BDLocation.TypeNetWorkLocation) {  //网络定位成功
                Toast.makeText(getApplicationContext(), "网络定位成功",
                        Toast.LENGTH_LONG).show();
                showLocation(location);
            } else if(type == BDLocation.TypeOffLineLocation) { //离线定位成功
                Toast.makeText(getApplicationContext(), "离线定位成功",
                        Toast.LENGTH_LONG).show();
                showLocation(location);
            }
        }
    }

    private void showLocation(BDLocation location) {
//        Log.e("123", "lat is " + location.getLatitude()
//                + " lng is " + location.getLongitude());

        latLng = new LatLng(
                location.getLatitude(), location.getLongitude());

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String result_lv = OKHttpUtils.buildGetString(getUrl(),"store",FuwuActivity.this);
                    VedioBean bean_lv = new Gson().fromJson(result_lv,VedioBean.class);
                    data_lv.addAll(bean_lv.data.list);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter_lv.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //创建最新地图状态的工厂类Builder
        //MapStatus.Builder builder = new MapStatus.Builder();

    }

    public String getUrl(){
        String url = "http://api.ycapp.yiche.com/kuantu/getnearshoplist?latitude="+latLng.latitude+"&longitude="
                +latLng.longitude+"&distance=30000&length=10&page=1&cityid=2401&token=faf95c30c6f022b7c09d125ac8c022c2";
        return url;
    }


    private void initViews() {

        rvFuwu = (RecyclerView) findViewById(R.id.rv_fuwu);
        lvFuwu = (ListView) findViewById(R.id.lv_fuwu);

        adapter = new RVFuwuAdapter(this, data);

        rvFuwu.setAdapter(adapter);

        rvFuwu.setLayoutManager(new GridLayoutManager(this, 4));

        adapter_lv = new LVFuwuAdapter(this,data_lv);

        lvFuwu.setAdapter(adapter_lv);

        lvFuwu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FuwuActivity.this, StoreActivtiy.class);

                intent.putExtra("store",data_lv.get(position));
/*              Bundle bundle = new Bundle();
                bundle.putSerializable("store",data_lv.get(position));
                intent.putExtra("a",bundle);*/

                startActivity(intent);
            }
        });


    }


    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = OKHttpUtils.buildGetString(url, "fuwu", FuwuActivity.this);
                    String result_ad = OKHttpUtils.buildGetString(Constants.URL_AD_IMG, "ad", FuwuActivity.this);

                    final RVFuwuBean bean = new Gson().fromJson(result, RVFuwuBean.class);
                    final RVFuwuBean bean_ad = new Gson().fromJson(result_ad, RVFuwuBean.class);

                    data.addAll(bean.data);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();

                            Picasso.with(FuwuActivity.this).load(bean_ad.data.get(0).image).into(ivAd1);
                            Picasso.with(FuwuActivity.this).load(bean_ad.data.get(1).image).into(ivAd2);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    /**
     * 底部导航栏的跳转
     *
     * @param view
     */
    @OnClick({R.id.iv_toutiao, R.id.iv_shequ, R.id.iv_xuanche, R.id.iv_fuwu, R.id.iv_wode,R.id.iv_ad_1,R.id.iv_ad_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toutiao:
                startActivity(new Intent(FuwuActivity.this, ToutiaoActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_shequ:
                startActivity(new Intent(FuwuActivity.this, ShequActivty.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_xuanche:
                startActivity(new Intent(FuwuActivity.this, XuancheActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_fuwu:
                break;
            case R.id.iv_wode:
                startActivity(new Intent(FuwuActivity.this, WodeActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_ad_1:

                Intent intent = new Intent(this, SameActivity.class);
                intent.putExtra("same", Constants.URL_AD_1);
                intent.putExtra("isVideo", true);
                startActivity(intent);
                break;
            case R.id.iv_ad_2:
                Intent intent1 = new Intent(this, SameActivity.class);
                intent1.putExtra("same", Constants.URL_AD_2);
                intent1.putExtra("isVideo", true);
                startActivity(intent1);
                break;
        }
    }

    /**
     * 返回事件的监听，点击返回键谈对话框，直接确定退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("退出程序");
            builder.setMessage("确定要退出程序吗?");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ExitApplication.getInstance().exit();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return false;
    }
}
