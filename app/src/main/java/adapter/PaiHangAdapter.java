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

import bean.PaiHangBean;


/**
 * Created by ldc on 2016/6/8.
 */
public class PaiHangAdapter extends RecyclerView.Adapter<PaiHangAdapter.PaiHangHolder>{

    private Context mContext;
    private final LayoutInflater inflater;
    private List<PaiHangBean.DataCount.ListDataBean> mData;


    public PaiHangAdapter(Context context,List<PaiHangBean.DataCount.ListDataBean> mData){
        this.mContext=context;
        inflater = LayoutInflater.from(context);
        this.mData=mData;
    }

    public void setData(List<PaiHangBean.DataCount.ListDataBean> bean) {
        mData = bean;
        notifyDataSetChanged();
    }

    @Override
    public PaiHangHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PaiHangHolder(inflater.inflate(R.layout.xc__xc_item_layout,null));
    }

    @Override
    public void onBindViewHolder(PaiHangHolder holder, int position) {
        holder.name.setText(mData.get(position).SerialName);
        holder.xiaoliang.setText("全国销量："+mData.get(position).Index);
        Picasso.with(mContext).load(mData.get(position).WhiteCoverImg.replace("{0}","3")).into(holder.logo);

    }

    @Override
    public int getItemCount() {
        return mData ==null?0:mData.size();
    }

    class PaiHangHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView xiaoliang;
        public ImageView logo;
        public PaiHangHolder(View itemView) {
            super(itemView);
            logo= (ImageView) itemView.findViewById(R.id.iv_xc_item_logo);
            name= (TextView) itemView.findViewById(R.id.tv_xc_item_name);
            xiaoliang= (TextView) itemView.findViewById(R.id.tv_xc_item_money);

        }
    }


}
