package com.example.ldc.easycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mapp.ExitApplication;
import views.PullZoomListView;

public class WodeActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode);
        ExitApplication.getInstance().addActivity(this);
        ButterKnife.inject(this);
        ivWode.setEnabled(false);

    }

    @OnClick({R.id.iv_toutiao, R.id.iv_shequ, R.id.iv_xuanche, R.id.iv_fuwu, R.id.iv_wode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toutiao:
                startActivity(new Intent(WodeActivity.this, ToutiaoActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_shequ:
                startActivity(new Intent(WodeActivity.this, ShequActivty.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_xuanche:
                startActivity(new Intent(WodeActivity.this, XuancheActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_fuwu:
                startActivity(new Intent(WodeActivity.this, FuwuActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.iv_wode:
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
