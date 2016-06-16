package adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.VedioBean;
import viewholder.VideoVHolder;

/**
 * Created by Mr.Liu on 2016/6/9.
 */
public class VideoAdapter extends BaseAdapter {

    private Context context;
    private List<VedioBean.DataBean.ListBean> data;
    private final LayoutInflater inflater;

    public VideoAdapter(Context context, List<VedioBean.DataBean.ListBean> data) {

        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        VideoVHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_video_item, null);

            holder = new VideoVHolder(convertView);

            convertView.setTag(holder);

        } else {

            holder = (VideoVHolder) convertView.getTag();

        }
        holder.tv_title.setText(data.get(position).title);
        holder.tv_src.setText(data.get(position).sourcename);

        Picasso.with(context).load(data.get(position).coverimg.replace("wapimg-{0}-{1}/", "")).into(holder.img);

        return convertView;
    }

    public void setData(List<VedioBean.DataBean.ListBean> data) {
        this.data = data;
    }

}
