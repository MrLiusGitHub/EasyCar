package activity_item;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.TupianActivityPagerBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import utils.OKHttpUtils;
import utils.ShareUtils;
import views.ShareDialog;

/**
 * Created by Mr.Liu on 2016/6/6.
 */
public class TupianActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.viewPager_tupian)
    ViewPager viewPagerTupian;
    @InjectView(R.id.tv_title_tupian)
    TextView tvTitleTupian;
    @InjectView(R.id.tv_desc_tupian)
    TextView tvDescTupian;
    @InjectView(R.id.tv_indictor)
    TextView tvIndictor;
    private Handler handler = new Handler();

    private String url;
    private String pinglun;
    private String imgTitle;
    private String imgWeb;
    private String imgsss;

    private List<ImageView> imgs = new ArrayList<>();
    private List<String> descs = new ArrayList<>();
    private MyAdapter adapter;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tupian);
        ButterKnife.inject(this);

        url = getIntent().getStringExtra("url");
        pinglun=getIntent().getStringExtra("pinglun");
        Log.e("123", url);

        initData();

        initView();
        
        initShere();

    }

    private void initShere() {
        TextView tv= (TextView) findViewById(R.id.tv_tupain_pinglun_num);
        tv.setText(pinglun);
        ImageView share= (ImageView) findViewById(R.id.iv_tupain_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog = new ShareDialog(TupianActivity.this);
                shareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();
                    }
                });
                ShareUtils utils=new ShareUtils(TupianActivity.this,shareDialog,imgTitle,imgsss,imgTitle,imgWeb);
            }
        });
    }

    private void initView() {

        adapter = new MyAdapter();

        viewPagerTupian.setAdapter(adapter);

        viewPagerTupian.setOnPageChangeListener(this);

    }

    private void initData() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = OKHttpUtils.buildGetString(url, "tupian", TupianActivity.this);
                    Log.d("TAG", url);
                    final TupianActivityPagerBean bean = new Gson().fromJson(result, TupianActivityPagerBean.class);
                    imgWeb=bean.data.list.shareToCheYou.newslink;
                    imgTitle=bean.data.list.shareToCheYou.newstitle;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            List<TupianActivityPagerBean.AlbumBean> albums = new ArrayList<TupianActivityPagerBean.AlbumBean>();
                            albums.addAll(bean.data.list.albums);

                            tvTitleTupian.setText(bean.data.list.title);
                            tvIndictor.setText(1+"/"+albums.size());
                            tvDescTupian.setText(albums.get(0).content);

                            for (int i = 0; i < albums.size(); i++) {
                                if(i==0){
                                    imgsss=albums.get(i).content;
                                }

                                descs.add(albums.get(i).content);
                                ImageView img = new ImageView(TupianActivity.this);

                                img.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                                if (albums.get(i).imageUrl != null) {
                                    Picasso.with(TupianActivity.this).load(albums.get(i).imageUrl).into(img);
                                }

                                imgs.add(img);

                                adapter.notifyDataSetChanged();
                            }


                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvIndictor.setText(position + 1 + "/" + imgs.size());
        tvDescTupian.setText(descs.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imgs.get(position));
            return imgs.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imgs.get(position));
        }
    }
}
