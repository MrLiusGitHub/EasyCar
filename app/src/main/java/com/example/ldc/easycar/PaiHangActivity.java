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
import fragment.PaiHangFragment;

public class PaiHangActivity extends AppCompatActivity {

    @InjectView(R.id.iv_back_xc_ph)
    ImageView ivBackXc;
    @InjectView(R.id.xc_ph_viewpager)
    ViewPager viewpager;
    @InjectView(R.id.paihang_tablayout)
    TabLayout tablayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paihang);
        ButterKnife.inject(this);

        initData();

        initView();
    }

    /**
     * 创建数据源
     * 1。fragment
     * 2.title
     */
    private void initData() {
        for (int i = 1; i < 8; i++) {
            if (i < 6) {
                Fragment fragment = PaiHangFragment.newInstance(i);
                fragments.add(fragment);

            } else {
                Fragment fragment = PaiHangFragment.newInstance(i + 1);
                fragments.add(fragment);
            }
            //adapter.notifyDataSetChanged();

        }
        list.add("小型车");//1
        list.add("微型车");//2
        list.add("紧凑型车");//3
        list.add("中型车");//4
        list.add("中大型车");//5
        list.add("SUV");//7
        list.add("MPV");//8

    }

    private void initView() {

        ivBackXc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaiHangActivity.this, XuancheActivity.class));
                PaiHangActivity.this.finish();
            }
        });

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, list);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setSelectedTabIndicatorHeight(0);

    }
}
