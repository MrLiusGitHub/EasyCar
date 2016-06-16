package com.example.ldc.easycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import fragment.VideoFragment;
import mapp.ExitApplication;
import butterknife.ButterKnife;
import butterknife.InjectView;
import fragment.MyFragment;
import fragment.SameFragment;
import fragment.TupianFragment;

public class ToutiaoActivity extends AppCompatActivity {

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
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.tablayout_title)
    TabLayout tablayoutTitle;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> title_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toutiao);
        ExitApplication.getInstance().addActivity(this);
        ButterKnife.inject(this);
        ivToutiao.setEnabled(false);

        initData();
        initViewPager();

    }

    private void initData() {
        for (int i = 0; i < 8; i++) {

            if (i == 0){
                fragments.add(SameFragment.getInstance("http://api.ycapp.yiche.com/appnews/toutiaov64/?page="
                        ,"&length=20&platform=1&deviceid=A00000484F4EC8",false,true,false));
            }else if (i == 1) {
                fragments.add(SameFragment.getInstance("http://api.ycapp.yiche.com/media/indexlist",false, true,true));
            }else if (i == 2){
                fragments.add(TupianFragment.getInstance("http://api.ycapp.yiche.com/AppNews/GetAppNewsAlbumList?page=", "&length=20&platform=1"));
            }else if(i == 3){
                fragments.add(new VideoFragment());
//                fragments.add(SameFragment.getInstance("http://api.ycapp.yiche.com/video/GetAppVideoList/?pageindex=",
//                        "&pagesize=20&plat=1",false,false,false));
            }else if (i == 4) {
                fragments.add(SameFragment.getInstance("http://extapi.ycapp.yiche.com/appnews/getrecommendnewslist?cachetime=0&platform=1&deviceid=A00000484F4EC8&dvtype=1&nettype=1&osvs=5.0",true, false,false));
            } else if (i == 5) {
                fragments.add(SameFragment.getInstance("http://api.ycapp.yiche.com/news/getnewslist?categoryid=3&serialid=&pageindex=", "&pagesize=20&appver=7.1", false,false,false));

            } else if (i == 6) {
                fragments.add(SameFragment.getInstance("http://api.ycapp.yiche.com/news/getnewslist?categoryid=1&serialid=&pageindex=", "&pagesize=20&appver=7.1", false, false,false));
            } else if (i == 7) {
                fragments.add(SameFragment.getInstance("http://api.ycapp.yiche.com/news/getnewslist?categoryid=2&serialid=&pageindex=", "&pagesize=20&appver=7.1", false, false,false));
            }
        }
        title_list.add("要闻");
        title_list.add("说车");
        title_list.add("图片");
        title_list.add("视频");
        title_list.add("推荐");
        title_list.add("新车");
        title_list.add("评测");
        title_list.add("导购");

    }

    private void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, title_list);
        viewpager.setAdapter(adapter);
        tablayoutTitle.setupWithViewPager(viewpager);
        tablayoutTitle.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

    /**
     * 底部导航栏的跳转
     *
     * @param view
     */
    public void iv_intent(View view) {
        switch (view.getId()) {
            case R.id.iv_toutiao:
                break;
            case R.id.iv_shequ:
                startActivity(new Intent(ToutiaoActivity.this, ShequActivty.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_xuanche:
                startActivity(new Intent(ToutiaoActivity.this, XuancheActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_fuwu:
                startActivity(new Intent(ToutiaoActivity.this, FuwuActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_wode:
                startActivity(new Intent(ToutiaoActivity.this, WodeActivity.class));
                overridePendingTransition(0, 0);
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
