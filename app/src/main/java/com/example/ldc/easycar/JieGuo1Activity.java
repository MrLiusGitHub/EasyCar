package com.example.ldc.easycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import fragment.JieGuoFragment;

public class JieGuo1Activity extends AppCompatActivity {

    @InjectView(R.id.iv_back_xc_jg)
    ImageView ivBackXc;
    @InjectView(R.id.jieguo_tablayout)
    TabLayout tabLayout;
    @InjectView(R.id.jieguo_viewpager)
    ViewPager viewpager;
    private List<Fragment> fragments=new ArrayList<>();
    private List<String> list=new ArrayList<>();
    private String url="";


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
        url=intent.getStringExtra("url");


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
                startActivity(new Intent(JieGuo1Activity.this,TiaoJianActivity.class));
                JieGuo1Activity.this.finish();
            }
        });

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(),fragments,list);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
    }
}
