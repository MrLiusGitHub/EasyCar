package mapp;

import android.app.Application;
import android.graphics.Bitmap;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import cn.sharesdk.framework.ShareSDK;
import utils.OKHttpUtils;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class MyApplication extends Application {

    //test
    @Override
    public void onCreate() {
        super.onCreate();

        ShareSDK.initSDK(this);

        //SDKInitializer.initialize(getApplicationContext());
        //在此做全局的配置
        initOkHttpUtils();
        initPicasso();
    }

    /**
     * 初始化OKHttpUtils
     */
    private void initOkHttpUtils() {
        OKHttpUtils.getOKHttpUtils(this);
    }

    /**
     * 初始化Picasso
     */
    private void initPicasso() {
        //
        Picasso picasso = new Picasso.Builder(this)
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .memoryCache(new LruCache(10 << 20))
                .indicatorsEnabled(true)
                //红色：网络
                //蓝色：磁盘
                //绿色：内存
                .build();
        Picasso.setSingletonInstance(picasso);
    }
}
