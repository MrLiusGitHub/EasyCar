package com.example.ldc.easycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import views.FlowRadioGroup;
import views.MaterialRangeSlider;

public class TiaoJianActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @InjectView(R.id.tv_min_money)
    TextView tvMinMoney;
    @InjectView(R.id.tv_max_money)
    TextView tvMaxMoney;
    @InjectView(R.id.price_slider)
    views.MaterialRangeSlider slider;
    List<String> list=new ArrayList<String>();
    List<Integer> img=new ArrayList<Integer>();
    String oldUrl="http://extapi.ycapp.yiche.com/car/pickcar/?dtshijian=1465303009312" +
            "&p=0-9999&l=5&g=0&b=0&dt=0&lv=0&t=0&d=&f=0&bd=0&sn=0&m=00000000000000000000" +
            "00000000&page=1&s=3&pagesize=20";
    String url="";
    int min;
    int max;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiaojian);
        ButterKnife.inject(this);

        initTitle();

        initGroup();
        slider.setMin(0);
        slider.setMax(101);
        slider.setStartingMinMax(0,101);
        slider.setRangeSliderListener(new MaterialRangeSlider.RangeSliderListener() {
            @Override
            public void onMaxChanged(int newValue) {
                if(newValue == 101){
                    tvMaxMoney.setText("∞");
                    max=newValue;
                }else {
                    tvMaxMoney.setText(newValue+"");
                    max=newValue;
                }
            }

            @Override
            public void onMinChanged(int newValue) {
                tvMinMoney.setText(newValue+"");
                min=newValue;
            }
        });

        Button  btn= (Button) findViewById(R.id.btn_chaxun);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(max == 101){
                    max=9999;
                }
                String url1=oldUrl.replace("0-9999",min+"-"+max);
                url=url1.replace("l=5","l="+type);
                Intent intent=new Intent(TiaoJianActivity.this,JieGuo1Activity.class);
                intent.putExtra("url",url);
                startActivity(intent);

            }
        });
    }

    private void initGroup() {
        FlowRadioGroup group= (FlowRadioGroup) findViewById(R.id.flowradiogroup);
        group.setOnCheckedChangeListener(this);
    }


    private void initTitle() {
        ImageView img= (ImageView) findViewById(R.id.iv_back_xc_ym);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TiaoJianActivity.this,XuancheActivity.class));
                TiaoJianActivity.this.finish();
            }
        });

        TextView chongzhi= (TextView) findViewById(R.id.chongzhi);
        chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slider.reset();
                slider.invalidate();
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb= (RadioButton) findViewById(checkedId);
        String content=rb.getText().toString();
        switch (content){
            case "微型车":
                type=1;
                break;
            case "小型车":
                type=2;
                break;
            case "紧凑型车":
                type=3;
                break;
            case "中型车":
                type=5;
                break;
            case "中大型车":
                type=4;
                break;
            case "豪华车":
                type=6;
                break;
            case "MPV":
                type=7;
                break;
            case "SUV":
                type=8;
                break;
            case "跑车":
                type=9;
                break;
            case "面包车":
                type=11;
                break;
            case "皮卡":
                type=12;
                break;
        }
    }
}
