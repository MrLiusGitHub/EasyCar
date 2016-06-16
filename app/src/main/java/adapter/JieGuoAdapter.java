package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.JieGuoBean;

/**
 * Created by ldc on 2016/6/9.
 */
public class JieGuoAdapter extends RecyclerView.Adapter<JieGuoAdapter.MyHolder>{
    private Context mContext;
    private List<JieGuoBean.ListBean> mData;
    private final LayoutInflater inflater;

    public void setData(List<JieGuoBean.ListBean> bean) {
        mData = bean;
        notifyDataSetChanged();
    }

    public JieGuoAdapter(Context context, List<JieGuoBean.ListBean> data){
        this.mContext=context;
        inflater = LayoutInflater.from(context);
        this.mData=data;
    }
    @Override
    public JieGuoAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.jieguo_item_layout,null));
    }

    @Override
    public void onBindViewHolder(JieGuoAdapter.MyHolder holder, int position) {
        holder.name.setText(mData.get(position).Name);
        holder.money.setText(mData.get(position).DealerPrice);
        Picasso.with(mContext).load(mData.get(position).Picture.replace("_2","_3")).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        public ImageView logo;
        public TextView name;
        public TextView money;

        public MyHolder(View itemView) {
            super(itemView);
            logo= (ImageView) itemView.findViewById(R.id.iv_jg_item_logo);
            name= (TextView) itemView.findViewById(R.id.tv_jg_item_name);
            money= (TextView) itemView.findViewById(R.id.tv_jg_item_money);
        }
    }
}
