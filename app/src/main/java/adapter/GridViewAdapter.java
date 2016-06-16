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

/**
 * Created by ldc on 2016/6/13.
 */
public class GridViewAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> list;
    private List<Integer> mData;
    private final LayoutInflater inflater;

    public GridViewAdapter(Context context,List<String> list,List<Integer> data){
        this.mContext=context;
        inflater = LayoutInflater.from(context);
        this.list=list;
        this.mData=data;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler holder=null;
        if(convertView ==null){
            holder=new ViewHodler();
            convertView=inflater.inflate(R.layout.gridview_item_layout,null);
            holder.name= (TextView) convertView.findViewById(R.id.tv_gridview);
            holder.img= (ImageView) convertView.findViewById(R.id.iv_gridview);

            convertView.setTag(holder);
        }else{
            holder= (ViewHodler) convertView.getTag();
        }

        holder.name.setText(list.get(position));
        Picasso.with(mContext).load(R.drawable.weixingche_selector).into(holder.img);
        return convertView;

    }
    class ViewHodler{
        TextView name;
        ImageView img;
    }
}
