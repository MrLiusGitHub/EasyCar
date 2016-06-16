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

import bean.XinCheBean;

/**
 * Created by ldc on 2016/6/8.
 */
public class XcXcAdapter extends BaseAdapter{
    private Context mContext;
    private List<XinCheBean.ItemBean> mData;
    public XcXcAdapter(Context context, List<XinCheBean.ItemBean> mData){
        this.mContext=context;
        this.mData=mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler=null;
        if(convertView ==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.xc__xc_item_layout,null);
            viewHodler=new ViewHodler();
            viewHodler.logo= (ImageView) convertView.findViewById(R.id.iv_xc_item_logo);
            viewHodler.name= (TextView) convertView.findViewById(R.id.tv_xc_item_name);
            viewHodler.money= (TextView) convertView.findViewById(R.id.tv_xc_item_money);

            convertView.setTag(viewHodler);
        }else{
            viewHodler= (ViewHodler) convertView.getTag();


        }
        viewHodler.name.setText(mData.get(position).ShowName);
        viewHodler.money.setText(mData.get(position).Price);
        Picasso.with(mContext).load(mData.get(position).Img).into(viewHodler.logo);
        return convertView;
    }
    class ViewHodler{
        public TextView name;
        public TextView money;
        public ImageView logo;
    }

}
