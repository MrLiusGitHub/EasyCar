package activity_item;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.example.ldc.easycar.R;
import com.squareup.picasso.Picasso;

import bean.VedioBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import views.SelectPicPopupWindow;

/**
 * Created by Mr.Liu on 2016/6/15.
 */
public class StoreActivtiy extends Activity {

    @InjectView(R.id.imd_store)
    ImageView imdStore;
    @InjectView(R.id.iv_lv_item)
    ImageView ivLvItem;
    @InjectView(R.id.tvName_lv_item)
    TextView tvNameLvItem;
    @InjectView(R.id.tvTime_lv_item)
    TextView tvTimeLvItem;
    @InjectView(R.id.tvDes_lv_item)
    TextView tvDesLvItem;
    @InjectView(R.id.tv_addr_store)
    TextView tvAddrStore;
    @InjectView(R.id.card_add_store)
    CardView cardAddStore;
    @InjectView(R.id.tv_phone_store)
    TextView tvPhoneStore;
    @InjectView(R.id.card_phone_store)
    CardView cardPhoneStore;
    @InjectView(R.id.main)
    RelativeLayout main;
    private SelectPicPopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_store);

        ButterKnife.inject(this);

        initView();
    }



    private void initView() {
        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("a");

        final VedioBean.DataBean.ListBean bean = (VedioBean.DataBean.ListBean) intent.getSerializableExtra("store");
        tvAddrStore.setText(bean.address);
        tvPhoneStore.setText(bean.phone);

        tvDesLvItem.setText(bean.distance);
        tvTimeLvItem.setText("营业时间:" + bean.bizHourBegin + "--" + bean.bizHourEnd);
        tvNameLvItem.setText(bean.name);


        Picasso.with(this).load(bean.img).into(ivLvItem);

        mPopupWindow = new SelectPicPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_phoneNum:

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + bean.phone));
                        if (ActivityCompat.checkSelfPermission(StoreActivtiy.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);

                        break;
                    case R.id.btn_cancel:
                        break;
                    default:
                        break;
                }
            }
        });
        mPopupWindow.setNum(bean.phone);

        cardPhoneStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }
        });

        cardAddStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StoreActivtiy.this,MapActivtiy.class);
                intent.putExtra("latitude",bean.latitude);
                intent.putExtra("longitude",bean.longitude);
                startActivity(intent);
            }
        });


    }

}
