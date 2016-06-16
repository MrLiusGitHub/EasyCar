package com.example.ldc.easycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mapp.ExitApplication;
import views.ShareDialog;

public class ShequActivty extends AppCompatActivity {

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
    @InjectView(R.id.btn_shequ)
    Button btnShequ;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shequ_activty);
        ExitApplication.getInstance().addActivity(this);
        ButterKnife.inject(this);
        ivShequ.setEnabled(false);
        btnShequ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog = new ShareDialog(ShequActivty.this);
                shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();

                    }
                });
               // ShareUtils utils=new ShareUtils(ShequActivty.this,shareDialog);

               /* shareDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                        if (item.get("ItemText").equals("微博")) {

                            //2、设置分享内容
                            ShareParams sp = new ShareParams();
                            sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/"); //分享文本
                            sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

                            //3、非常重要：获取平台对象
                            Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                            sinaWeibo.setPlatformActionListener(ShequActivty.this); // 设置分享事件回调
                            // 执行分享
                            sinaWeibo.share(sp);

                        } else if (item.get("ItemText").equals("微信好友")) {
                            Toast.makeText(ShequActivty.this, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();

                            //2、设置分享内容
                            ShareParams sp = new ShareParams();
                            sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
                            sp.setTitle("我是分享标题");  //分享标题
                            sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");   //分享文本
                            sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
                            sp.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

                            //3、非常重要：获取平台对象
                            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                            wechat.setPlatformActionListener(ShequActivty.this); // 设置分享事件回调
                            // 执行分享
                            wechat.share(sp);


                        } else if (item.get("ItemText").equals("朋友圈")) {
                            //2、设置分享内容
                            ShareParams sp = new ShareParams();
                            sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
                            sp.setTitle("我是分享标题");  //分享标题
                            sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");   //分享文本
                            sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
                            sp.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

                            //3、非常重要：获取平台对象
                            Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
                            wechatMoments.setPlatformActionListener(ShequActivty.this); // 设置分享事件回调
                            // 执行分享
                            wechatMoments.share(sp);

                        } else if (item.get("ItemText").equals("QQ")) {
                            //2、设置分享内容
                            ShareParams sp = new ShareParams();
                            sp.setTitle("我是分享标题");
                            sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");
                            sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
                            sp.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情
                            //3、非常重要：获取平台对象
                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(ShequActivty.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);

                        }
                        shareDialog.dismiss();
                    }
                });*/


            }
        });

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
                startActivity(new Intent(ShequActivty.this, ToutiaoActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_shequ:
                break;
            case R.id.iv_xuanche:
                startActivity(new Intent(ShequActivty.this, XuancheActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_fuwu:
                startActivity(new Intent(ShequActivty.this, FuwuActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_wode:
                startActivity(new Intent(ShequActivty.this, WodeActivity.class));
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

  /*  @Override
    public void onCancel(Platform arg0, int arg1) {//回调的地方是子线程，进行UI操作要用handle处理
        handler.sendEmptyMessage(5);

    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {//回调的地方是子线程，进行UI操作要用handle处理
        if (arg0.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是新浪微博
            handler.sendEmptyMessage(1);
        } else if (arg0.getName().equals(Wechat.NAME)) {
            handler.sendEmptyMessage(1);
        } else if (arg0.getName().equals(WechatMoments.NAME)) {
            handler.sendEmptyMessage(3);
        } else if (arg0.getName().equals(QQ.NAME)) {
            handler.sendEmptyMessage(4);
        }

    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {//回调的地方是子线程，进行UI操作要用handle处理
        arg2.printStackTrace();
        Message msg = new Message();
        msg.what = 6;
        msg.obj = arg2.getMessage();
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "微博分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    Toast.makeText(getApplicationContext(), "微信分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "朋友圈分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 5:
                    Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(getApplicationContext(), "分享失败啊" + msg.obj, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }

    };*/
}
