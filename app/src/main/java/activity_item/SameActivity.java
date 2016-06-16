package activity_item;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;
import com.google.gson.Gson;

import java.io.IOException;

import bean.SameItemBean;
import utils.OKHttpUtils;
import utils.ShareUtils;
import views.ShareDialog;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class SameActivity extends Activity {

    private WebView webview;
    private String webUrl = "";
    private String webImg = "";
    private String webTitle="";

    private Handler handler = new Handler();
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_same);

        initWebView();

        Intent intent = getIntent();
        final boolean isVideo = intent.getBooleanExtra("isVideo",false);
        final String url = intent.getStringExtra("same");
        String pinglun = intent.getStringExtra("pinglun");
        TextView tv= (TextView) findViewById(R.id.tv_same_pinglun_num);
        tv.setText(pinglun);
        initShare();
        Log.e("123", url);

        if (isVideo){
            webview.loadUrl(url);
        }else {

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        String result = OKHttpUtils.buildGetString(url, "1", SameActivity.this);

                        SameItemBean bean = new Gson().fromJson(result, SameItemBean.class);
                        Log.e("123", bean.data.shareData.newsLink);
                        webUrl = bean.data.shareData.newsLink;
                        webTitle=bean.data.shareData.newsTitle;
                        webImg=bean.data.shareData.newsImg;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //加载需要显示的网页
                                if (webUrl != null) {
                                    Log.e("123",webUrl);
                                    webview.loadUrl(webUrl);
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }

    private void initWebView() {
        webview = (WebView) findViewById(R.id.web_same);
        WebSettings webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);


        //设置Web视图
        webview.setWebViewClient(new webViewClient());
    }

    private void initShare() {

        ImageView share= (ImageView) findViewById(R.id.iv_same_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog = new ShareDialog(SameActivity.this);
                shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                    }
                });
                ShareUtils utils=new ShareUtils(SameActivity.this,shareDialog,webTitle,webImg,webTitle,webUrl);
            }
        });
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        finish();//结束退出程序
        return false;
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
