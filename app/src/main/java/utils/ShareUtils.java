package utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import views.ShareDialog;

/**
 * Created by ldc on 2016/6/15.
 */
public class ShareUtils implements PlatformActionListener{
    private Context mContext;
    private ShareDialog shareDialog;
    private String mText;
    private String mImgUrl;
    private String mTitle;
    private String mWebUrl;
    public ShareUtils(Context context, ShareDialog shareDialog,String text,String imgUrl,String title,String webUrl){
        this.mContext=context;
        this.shareDialog=shareDialog;
        this.mText=text;
        this.mTitle=title;
        this.mImgUrl=imgUrl;
        this.mWebUrl=webUrl;

        shareDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                int arg2, long arg3) {
                    HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                    if (item.get("ItemText").equals("微博")) {

                        //2、设置分享内容
                        Platform.ShareParams sp = new Platform.ShareParams();
                        sp.setText(mText); //分享文本
                        sp.setImageUrl(mImgUrl);//网络图片rul

                        //3、非常重要：获取平台对象
                        Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                        sinaWeibo.setPlatformActionListener(ShareUtils.this); // 设置分享事件回调
                        // 执行分享
                        sinaWeibo.share(sp);

                    } else if (item.get("ItemText").equals("微信好友")) {
                        Toast.makeText(mContext, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();

                        //2、设置分享内容
                        Platform.ShareParams sp = new Platform.ShareParams();
                        sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
                        sp.setTitle(mTitle);  //分享标题
                        sp.setText(mText);   //分享文本
                        sp.setImageUrl(mImgUrl);//网络图片rul
                        sp.setUrl(mWebUrl);   //网友点进链接后，可以看到分享的详情

                        //3、非常重要：获取平台对象
                        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                        wechat.setPlatformActionListener(ShareUtils.this); // 设置分享事件回调
                        // 执行分享
                        wechat.share(sp);


                    } else if (item.get("ItemText").equals("朋友圈")) {
                        //2、设置分享内容
                        Platform.ShareParams sp = new Platform.ShareParams();
                        sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
                        sp.setTitle(mTitle);  //分享标题
                        sp.setText(mText);   //分享文本
                        sp.setImageUrl(mImgUrl);//网络图片rul
                        sp.setUrl(mWebUrl);   //网友点进链接后，可以看到分享的详情

                        //3、非常重要：获取平台对象
                        Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
                        wechatMoments.setPlatformActionListener(ShareUtils.this); // 设置分享事件回调
                        // 执行分享
                        wechatMoments.share(sp);

                    } else if (item.get("ItemText").equals("QQ")) {
                        //2、设置分享内容
                        Platform.ShareParams sp = new Platform.ShareParams();
                        sp.setTitle(mTitle);
                        sp.setText(mText);
                        sp.setImageUrl(mImgUrl);//网络图片rul
                        sp.setTitleUrl(mWebUrl);  //网友点进链接后，可以看到分享的详情
                        //3、非常重要：获取平台对象
                        Platform qq = ShareSDK.getPlatform(QQ.NAME);
                        qq.setPlatformActionListener(ShareUtils.this); // 设置分享事件回调
                        // 执行分享
                        qq.share(sp);

                    }
                }

        });
    }



    @Override
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
                    Toast.makeText(mContext, "微博分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    Toast.makeText(mContext, "微信分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(mContext, "朋友圈分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(mContext, "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 5:
                    Toast.makeText(mContext, "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(mContext, "分享失败啊" + msg.obj, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }

    };
}
