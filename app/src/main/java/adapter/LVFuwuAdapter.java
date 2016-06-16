package adapter;

import android.content.Context;
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

/**
 * Created by Mr.Liu on 2016/6/15.
 */
public class LVFuwuAdapter extends BaseAdapter {

    private Context context;
    private List<VedioBean.DataBean.ListBean> data;
    private LayoutInflater inflater;

    public LVFuwuAdapter(Context context, List<VedioBean.DataBean.ListBean> data){
        inflater = LayoutInflater.from(context);
        this.context = context;
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

        ViewHolder holder = null;

        if (convertView == null){

            convertView = inflater.inflate(R.layout.lvfuwu_item, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);

        }else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tvName.setText(data.get(position).name);
        holder.tv_des.setText(data.get(position).distance+"米");
        holder.tv_begin.setText("营业时间:"+data.get(position).bizHourBegin+"--"+data.get(position).bizHourEnd);
        Picasso.with(context).load(data.get(position).img).into(holder.img);

        return convertView;
    }

    class ViewHolder{
        private ImageView img;
        private TextView tvName;
        private TextView tv_begin;
        private TextView tv_des;

        public ViewHolder(View convertView){

            img = (ImageView) convertView.findViewById(R.id.iv_lv_item);
            tv_des = (TextView) convertView.findViewById(R.id.tvDes_lv_item);
            tvName = (TextView) convertView.findViewById(R.id.tvName_lv_item);
            tv_begin = (TextView) convertView.findViewById(R.id.tvTime_lv_item);

        }
    }
}
