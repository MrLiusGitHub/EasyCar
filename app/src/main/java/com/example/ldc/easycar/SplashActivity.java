package com.example.ldc.easycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import bean.SplashBean;
import utils.OKHttpUtils;

public class SplashActivity extends AppCompatActivity {

    public String url = "http://api.ycapp.yiche.com/yicheapp/getappads/?&group=110&platform=1";
    private SplashBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView img = (ImageView) findViewById(R.id.img_splash);

        new Thread(){
            @Override
            public void run() {
                super.run();

            }
        }.start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String result = OKHttpUtils.buildGetString(url,"splash",SplashActivity.this);

                    bean = new Gson().fromJson(result, SplashBean.class);
                    if(bean.data.size() == 0){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.with(SplashActivity.this).load(R.drawable.splash3).into(img);
                            }
                        });

                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.with(SplashActivity.this).load(bean.data.get(0).pic2_url).into(img);
                            }
                        });
                    }



                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(SplashActivity.this,ToutiaoActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }).start();
    }
}
