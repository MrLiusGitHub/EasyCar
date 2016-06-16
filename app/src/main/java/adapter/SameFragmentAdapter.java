package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ldc.easycar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import activity_item.SameActivity;
import activity_item.TupianActivity;
import bean.SameBean;
import viewholder.HeaderVHolder;
import viewholder.SameVHolder;
import viewholder.TupianVHolder;
import viewholder.TupianVHolderOne;
import viewholder.VideoVHolder;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class SameFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SameBean.ListBean> data;
    private LayoutInflater inflater;
    // private OnItemClickListener onItemClickListener;

    private List<ImageView> imgs = new ArrayList<>();

    private List<String> titles = new ArrayList<>();

    private boolean isFirst = true;

    private boolean isFirstPager;
    private boolean isCommond;

    public void setCommond(boolean commond) {
        isCommond = commond;
    }

    public void setFirstPager(boolean firstPager) {
        isFirstPager = firstPager;
    }

    private int i = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            headView.pager.setCurrentItem(i % 3);
            headView.title.setText(titles.get(i % 3));
            i++;
        }
    };

    /********************
     * 添加头部的ViewPager
     * *******************
     */
    private HeaderVHolder headView;

    public HeaderVHolder getHeadView() {
        return headView;
    }

    public void setHeadView(HeaderVHolder headView) {
        this.headView = headView;
    }

//    public interface OnItemClickListener {
//        void onItemClickListener(String url, int position);
//    }, OnItemClickListener onItemClickListener   this.onItemClickListener = onItemClickListener;

    public SameFragmentAdapter(Context context, List<SameBean.ListBean> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;


    }

    /**
     * Item的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        int position1 = position + 2;
        if (headView != null) {
            if (position == 0) {
                return 0;
            } else {
                return doType(position1);
            }
        } else {
            return 1;
        }
    }

    public int doType(int position) {

        if (data.get(position).type == 3) {

            String[] imgsUrl = data.get(position).picCover.split(";");
            if (imgsUrl.length == 3) {
                return 3;
            } else {
                return 5;
            }

        } else if (data.get(position).type == 4) {
            return 4;
        }
        if (data.get(position).type == 21) {
            return 6;
        } else if (data.get(position).type == 23) {
            return 3;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            return headView;
        } else if (viewType == 3) {
            return new TupianVHolder(inflater.inflate(R.layout.tupian_item, null));
        } else if (viewType == 5) {
            return new TupianVHolderOne(inflater.inflate(R.layout.tupian_item2, null));
        } else if (viewType == 4) {
            return new VideoVHolder(inflater.inflate(R.layout.fragment_video_item, null));
        } else {
            return new SameVHolder(inflater.inflate(R.layout.same_item, null));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int type = holder.getItemViewType();
        int position1 = position + 2;

        if (type == 0) {

            headView = (HeaderVHolder) holder;

            initImgs();
            if (isFirst) {
                initPager();
                isFirst = false;
            }

            headView.pager.setAdapter(new MyAdapter());

            headView.indicator.setViewPager(headView.pager);

        } else if (type == 3) {

            Log.e("123", data.get(position1).type + "");
            TupianVHolder holder1 = (TupianVHolder) holder;

            String[] imgsUrl = data.get(position1).picCover.split(";");

            holder1.tv_title.setText(data.get(position1).title);
            holder1.tv_count.setText(data.get(position1).commentCount);
            holder1.tv_src.setText(data.get(position1).publishTime);

            Picasso.with(context).load(imgsUrl[0])
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder1.iv_tupian1);

            Picasso.with(context).load(imgsUrl[1])
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder1.iv_tupian2);

            Picasso.with(context).load(imgsUrl[2])
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder1.iv_tupian3);

            final int finalPosition3 = position1;
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TupianActivity.class);
                    intent.putExtra("url", "http://api.ycapp.yiche.com/appnews/"
                            + "GetNewsAlbumv71?newsid=" + data.get(finalPosition3).newsId + "&lastmodify=" + data.get(finalPosition3).lastModify);
                    intent.putExtra("pinglun",data.get(finalPosition3).commentCount);
                    context.startActivity(intent);
                    Log.e("234", "http://api.ycapp.yiche.com/appnews/"
                            + "GetNewsAlbumv71?newsid=" + data.get(finalPosition3).newsId + "&lastmodify=" + data.get(finalPosition3).lastModify);
                    Log.e("TAG", data.get(finalPosition3).commentCount);
                }
            });
        } else if (type == 5) {

            TupianVHolderOne holder1 = (TupianVHolderOne) holder;

            String[] imgsUrl = data.get(position1).picCover.split(";");

            holder1.tv_title.setText(data.get(position1).title);
            holder1.tv_count.setText(data.get(position1).commentCount);
            holder1.tv_src.setText(data.get(position1).publishTime);

            Picasso.with(context).load(imgsUrl[0])
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder1.iv_tupian1);

            final int finalPosition2 = position1;
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TupianActivity.class);
                    intent.putExtra("url", "http://api.ycapp.yiche.com/appnews/"
                            + "GetNewsAlbumv71?newsid=" + data.get(finalPosition2).newsId + "&lastmodify=" + data.get(finalPosition2).lastModify);
                    intent.putExtra("pinglun",data.get(finalPosition2).commentCount);
                    context.startActivity(intent);
                }
            });

        } else if (type == 4) {

            VideoVHolder holder1 = (VideoVHolder) holder;

            holder1.tv_title.setText(data.get(position1).title);
            holder1.tv_src.setText(data.get(position1).nickName);

            Picasso.with(context).load(data.get(position1).picCover).into(holder1.img);

            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } else if (type == 6) {
            final int finalPosition1 = position1;
            SameVHolder holder1 = (SameVHolder) holder;

            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SameActivity.class);
                    intent.putExtra("same", "http://api.ycapp.yiche.com/media/" +
                            "GetStructMedia?version=7.1&" +
                            "newsId=" + data.get(finalPosition1).newsId + "&isrevise=1&plat=1&ts=" +
                            data.get(finalPosition1).lastModify + "&type=1");
                    intent.putExtra("pinglun",data.get(finalPosition1).commentCount);
                    context.startActivity(intent);
                }
            });

            holder1.tv_title.setText(data.get(finalPosition1).title);
            holder1.tv_time.setText(data.get(finalPosition1).publishTime);

            Picasso.with(context).load(data.get(finalPosition1).picCover.replace("wapimg-{0}-{1}/", "")).into(holder1.iv_same);

        } else {

            if (headView != null) {
                position += 2;
            }
            final int finalPosition1 = position;


            SameVHolder holder1 = (SameVHolder) holder;

            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SameActivity.class);
                    if (headView != null || isCommond) {
                        intent.putExtra("same", "http://api.ycapp.yiche.com/appnews/" +
                                "GetStructNews?version=7.1&" +
                                "newsId=" + data.get(finalPosition1).newsId + "&plat=1&ts=" +
                                data.get(finalPosition1).lastModify);
                        intent.putExtra("pinglun",data.get(finalPosition1).commentCount);
                    } else {
                        intent.putExtra("same", "http://api.ycapp.yiche.com/news/" +
                                "GetStructYCNews?version=7.1&" +
                                "newsId=" + data.get(finalPosition1).newsId + "&plat=1&ts=" +
                                data.get(finalPosition1).lastModify);
                        intent.putExtra("pinglun",data.get(finalPosition1).commentCount);
                    }
                    context.startActivity(intent);
                }
            });

            holder1.tv_title.setText(data.get(finalPosition1).title);
            holder1.tv_time.setText(data.get(finalPosition1).publishTime);

            Picasso.with(context).load(data.get(finalPosition1).picCover.replace("wapimg-{0}-{1}/", "")).into(holder1.iv_same);

        }
    }

    @Override
    public int getItemCount() {
        return headView == null ? data.size() : data.size() - 2;
    }

    public void setData(List<SameBean.ListBean> bean) {
        data = bean;
        notifyDataSetChanged();
    }

    public void initImgs() {

        for (int i = 0; i < 3; i++) {
            ImageView img = new ImageView(context);
            img.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            img.setScaleType(ImageView.ScaleType.FIT_XY);

            Picasso.with(context).load(data.get(i).picCover).into(img);

            titles.add((data.get(i).title));

            final int finalI = i;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isFirstPager) {
                        Intent intent = new Intent(context, SameActivity.class);
                        intent.putExtra("same", "http://api.ycapp.yiche.com/appnews/" +
                                "GetStructNews?version=7.1&" +
                                "newsId=" + data.get(finalI).newsId + "&plat=1&ts=" +
                                data.get(finalI).lastModify);
                        intent.putExtra("pinglun",data.get(finalI).commentCount);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, SameActivity.class);
                        intent.putExtra("same", "http://api.ycapp.yiche.com/media/" +
                                "GetStructMedia?version=7.1&" +
                                "newsId=" + data.get(finalI).newsId + "&isrevise=1&plat=1&ts=" +
                                data.get(finalI).lastModify + "&type=1");
                        intent.putExtra("pinglun",data.get(finalI).commentCount);
                        context.startActivity(intent);
                    }


                    //onItemClickListener.onItemClickListener(, finalI);
                }
            });

            imgs.add(img);
        }

    }

    public void initPager() {

        new Thread() {

            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }

        }.start();

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
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
