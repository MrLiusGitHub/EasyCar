package com.example.ldc.easycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import fragment.JieGuoFragment;

public class JieGuoActivity extends AppCompatActivity {

    @InjectView(R.id.iv_back_xc_jg)
    ImageView ivBackXc;
    @InjectView(R.id.jieguo_tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.jieguo_viewpager)
    ViewPager viewpager;
    private List<Fragment> fragments=new ArrayList<>();
    private List<String> list=new ArrayList<>();
    private String url="";
    private String money_start_url="http://extapi.ycapp.yiche.com/car/pickcar/?dtshijian=1465302266004&p=";
    private String money_end_url="&l=0&g=0&b=0&dt=0&lv=0&t=0&d=&f=0&bd=0&sn=0&m=0000000000000000000000000000&page=1&s=3&pagesize=20";
    private String type_start_url="http://extapi.ycapp.yiche.com/car/pickcar/?dtshijian=1465302925278&p=0-9999&l=";
    private String type_end_url="&g=0&b=0&dt=0&lv=0&t=0&d=&f=0&bd=0&sn=0&m=0000000000000000000000000000&page=1&s=3&pagesize=20";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jieguo);
        ButterKnife.inject(this);


        initData();

        initViews();
    }
    private void initData() {
        Intent intent=getIntent();
        int type=intent.getIntExtra("type",-1);
        int code=intent.getIntExtra("code",-1);
        //拿到要加载的url
        if(type>= 0 && type< 4){
            if(type == 0){
                url=money_start_url+"0-5"+money_end_url;
            }if(type == 1){
                url=money_start_url+"5-8"+money_end_url;
            }if(type == 2){
                url=money_start_url+"8-12"+money_end_url;
            }if(type == 3){
                url=money_start_url+"12-18"+money_end_url;
            }
        }else if(type == 4){
            url=type_start_url+code+type_end_url;
        }else {
            Toast.makeText(this,"数据异常",Toast.LENGTH_SHORT).show();
        }


        //创建viewpager的数据源，fragment和tabllayout的title
        String url1=url.replace("s=3","s=4");
        String url3=url.replace("s=3","s=2");
        Fragment fragment1= JieGuoFragment.newInstance(url1);
        Fragment fragment2= JieGuoFragment.newInstance(url);
        Fragment fragment3= JieGuoFragment.newInstance(url3);

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        list.add("最畅销");
        list.add("最贵");
        list.add("最便宜");
    }

    private void initViews() {

        ivBackXc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JieGuoActivity.this,XuancheActivity.class));
                JieGuoActivity.this.finish();
            }
        });

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(),fragments,list);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
    }
}
