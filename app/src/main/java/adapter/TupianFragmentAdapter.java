package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldc.easycar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.TupianBean;
import viewholder.TupianVHolder;
import viewholder.TupianVHolderOne;

/**
 * Created by Mr.Liu on 2016/6/6.
 */
public class TupianFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<TupianBean.DataItemBean> data;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener{
        void onItemClickListener(String url,String pinlun);
    };

    public TupianFragmentAdapter(Context context, List<TupianBean.DataItemBean> data, OnItemClickListener onItemClickListener) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        TupianBean.DataItemBean bean = data.get(position);
        String[] imgsUrl = bean.picCover.split(";");

        return imgsUrl.length;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 3){
            return new TupianVHolder(inflater.inflate(R.layout.tupian_item, null));
        }else {
            return new TupianVHolderOne(inflater.inflate(R.layout.tupian_item2, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final TupianBean.DataItemBean bean = data.get(position);
        String[] imgsUrl = bean.picCover.split(";");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener( "http://api.ycapp.yiche.com/appnews/"
                +"GetNewsAlbumv71?newsid="+ bean.newsId+"&lastmodify="+ bean.lastModify,bean.commentCount);
            }
        });

        int type = getItemViewType(position);

        switch (type){
            case 3:

                TupianVHolder holder1 = (TupianVHolder) holder;

                holder1.tv_title.setText(bean.title);
                holder1.tv_count.setText(bean.commentCount);
                holder1.tv_src.setText(bean.src);

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

                break;

            case 1:
                TupianVHolderOne holder2 = (TupianVHolderOne) holder;

                holder2.tv_title.setText(bean.title);
                holder2.tv_count.setText(bean.commentCount);
                holder2.tv_src.setText(bean.src);

                Picasso.with(context).load(imgsUrl[0])
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(holder2.iv_tupian1);
                break;

        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<TupianBean.DataItemBean> bean) {
        data = bean;
        notifyDataSetChanged();
    }
}
