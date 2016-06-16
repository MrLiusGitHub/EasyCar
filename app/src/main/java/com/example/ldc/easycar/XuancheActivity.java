package com.example.ldc.easycar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import adapter.SectionedGroupableAdapter;
import bean.Car;
import bean.CarListModle;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mapp.ExitApplication;
import utils.FindCar;
import views.GroupListView;

import android.os.Bundle;


public class XuancheActivity extends AppCompatActivity {

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


    private SectionedGroupableAdapter adapter;
    private GroupListView listview;
    private List<Car> mList;
    private SlidingMenu slidingMenu;
    private final static int BTN_ONE_1=0;
    private final static int BTN_ONE_2=1;
    private final static int BTN_ONE_3=2;
    private final static int BTN_ONE_4=3;
    private final static int BTN_TWO=4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuanche);
        ExitApplication.getInstance().addActivity(this);
        ButterKnife.inject(this);
        ivXuanche.setEnabled(false);

        initSlidingMenu();

        initData();

        initViews();

    }

    private void initData() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.acjson);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String mJson = stringBuffer.toString();


        CarListModle modle = new Gson().fromJson(mJson, CarListModle.class);
        mList = modle.data.getTotalCityList();
        if (mList != null) {
            Log.i("sortListView", "mCityList.size:" + mList.size()
                    + "");
        }


    }

    private void initViews() {
        listview = (GroupListView) findViewById(R.id.grouplistview);
        //给带索引的listview添加头部
        LinearLayout heander_layout = (LinearLayout)
                LayoutInflater.from(this).inflate(R.layout.xc_header_layout, null);
        listview.addHeaderView(heander_layout);
        //设置头部控件的点击监听
        ImageView iv_shouchang = (ImageView) heander_layout.findViewById(R.id.iv_xc_shouchang);
        ImageView iv_paihang = (ImageView) heander_layout.findViewById(R.id.iv_xc_paihang);
        ImageView iv_xinche = (ImageView) heander_layout.findViewById(R.id.iv_xc_xinche);
        ImageView iv_kanche = (ImageView) heander_layout.findViewById(R.id.iv_xc_kanche);
        TextView tv_tiaojian = (TextView) heander_layout.findViewById(R.id.tv_xc_gdtj);
        TextView tv_lanjie = (TextView) heander_layout.findViewById(R.id.tv_meishilanjie1);
        TextView tv_wuwan = (TextView) heander_layout.findViewById(R.id.tv_xc_wuwan);
        TextView tv_bawan = (TextView) heander_layout.findViewById(R.id.tv_xc_bawan);
        TextView tv_shier= (TextView) heander_layout.findViewById(R.id.tv_xc_shierwan);
        TextView tv_shba= (TextView) heander_layout.findViewById(R.id.tv_xc_shibawan);
        TextView tv_xiaoxing= (TextView) heander_layout.findViewById(R.id.tv_xc_xiaoxing);
        TextView tv_jincou= (TextView) heander_layout.findViewById(R.id.tv_xc_jincou);
        TextView tv_zhongxing= (TextView) heander_layout.findViewById(R.id.tv_xc_zhongxing);
        TextView tv_suv= (TextView) heander_layout.findViewById(R.id.tv_xc_suv);

        iv_shouchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.showMenu();
            }
        });
        iv_paihang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XuancheActivity.this, PaiHangActivity.class));
            }
        });
        iv_xinche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XuancheActivity.this, XinCheActivity.class));
            }
        });
        iv_kanche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(XuancheActivity.this, "无法获得定位", Toast.LENGTH_SHORT).show();
            }
        });
        tv_tiaojian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XuancheActivity.this, TiaoJianActivity.class));
            }
        });

        tv_lanjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        tv_wuwan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJGActivity(BTN_ONE_1,0);
            }
        });
        tv_bawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJGActivity(BTN_ONE_2,0);
            }
        });
        tv_shier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJGActivity(BTN_ONE_3,0);
            }
        });
        tv_shba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJGActivity(BTN_ONE_4,0);
            }
        });
        tv_xiaoxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJGActivity(BTN_TWO,2);
            }
        });
        tv_jincou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJGActivity(BTN_TWO,3);
            }
        });
        tv_zhongxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJGActivity(BTN_TWO,5);
            }
        });
        tv_suv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJGActivity(BTN_TWO,8);
            }
        });



        //给带索引的listview添加适配器
        adapter = new SectionedGroupableAdapter(this, mList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new GroupListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, int rawPosition, long id) {

                ImageView iv = (ImageView) view.findViewById(R.id.iv_xc_item);
                TextView tv = (TextView) view.findViewById(R.id.tv_xc_item);
                String name = tv.getText().toString();
                ImageView iv_menu = (ImageView) slidingMenu.findViewById(R.id.iv_title_brand);
                String logo_url = FindCar.getLogoUrl(mList, rawPosition);
                Picasso.with(XuancheActivity.this).load(logo_url).into(iv_menu);
                TextView tv_menu = (TextView) slidingMenu.findViewById(R.id.tv_slidingmenu_summary);

                tv_menu.setText(name);
                slidingMenu.showMenu();
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
    }

    private void initSlidingMenu() {
        //1. 得到SlidingMenu对象
        slidingMenu = new SlidingMenu(this);
        //2. 从哪边出来：
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setMenu(R.layout.slide_menu_layout);
        //3. 显示菜单的布局
        //4. 触摸的模式：
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        //5. 设置菜单的宽度
        slidingMenu.setBehindWidth(500); //菜单的宽度


        //6.设置依附于Activity的形式：
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);


    }


    /**
     * 底部导航栏的跳转
     *
     * @param view
     */
    @OnClick({R.id.iv_toutiao, R.id.iv_shequ, R.id.iv_xuanche, R.id.iv_fuwu, R.id.iv_wode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toutiao:
                startActivity(new Intent(XuancheActivity.this, ToutiaoActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_shequ:
                startActivity(new Intent(XuancheActivity.this, ShequActivty.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_xuanche:
                break;
            case R.id.iv_fuwu:
                startActivity(new Intent(XuancheActivity.this, FuwuActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_wode:
                startActivity(new Intent(XuancheActivity.this, WodeActivity.class));
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

    public void startJGActivity(int type,int code){
        Intent intent=new Intent(this,JieGuoActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("code",code);
        startActivity(intent);
    }

}
