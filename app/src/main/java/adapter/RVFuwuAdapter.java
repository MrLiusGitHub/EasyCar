package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import activity_item.SameActivity;
import bean.RVFuwuBean;
import constants.Constants;

/**
 * Created by Mr.Liu on 2016/6/15.
 */
public class RVFuwuAdapter extends RecyclerView.Adapter<RVFuwuAdapter.VHolder> {

    private Context context;
    private List<RVFuwuBean.DataBean> data;
    private final LayoutInflater inflater;

    private String[] urls = new String[]{Constants.URL_FUWU_1,Constants.URL_FUWU_2, Constants.URL_FUWU_3,
    Constants.URL_FUWU_4, Constants.URL_FUWU_5, Constants.URL_FUWU_6, Constants.URL_FUWU_7, Constants.URL_FUWU_8};

    public RVFuwuAdapter(Context context, List<RVFuwuBean.DataBean> data){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }


    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VHolder(inflater.inflate(R.layout.rvfuwu_item, null));
    }

    @Override
    public void onBindViewHolder(VHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(urls[position]);
            }
        });

        holder.title.setText(data.get(position).title);
        Picasso.with(context).load(data.get(position).image).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void start(String url){
        Intent intent = new Intent(context, SameActivity.class);
        intent.putExtra("same", url);
        intent.putExtra("isVideo",true);
        context.startActivity(intent);
    }

    class VHolder extends RecyclerView.ViewHolder{

        public ImageView img;
        public TextView title;

        public VHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.iv_rvfuwu_item);
            title = (TextView) itemView.findViewById(R.id.tv_rvfuwu_item);
        }
    }
}

