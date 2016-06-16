package viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ldc.easycar.R;

/**
 * Created by Mr.Liu on 2016/6/6.
 */
public class TupianVHolder extends RecyclerView.ViewHolder {

    public TextView tv_title;
    public TextView tv_src;
    public TextView tv_count;

    public ImageView iv_tupian1;
    public ImageView iv_tupian2;
    public ImageView iv_tupian3;

    public TupianVHolder(View itemView) {
        super(itemView);

        tv_title = ((TextView) itemView.findViewById(R.id.tv_title));
        tv_src = (TextView) itemView.findViewById(R.id.tv_src);
        tv_count = (TextView) itemView.findViewById(R.id.tv_count);

        iv_tupian1 = (ImageView) itemView.findViewById(R.id.iv_tupian1);
        iv_tupian2 = (ImageView) itemView.findViewById(R.id.iv_tupian2);
        iv_tupian3 = (ImageView) itemView.findViewById(R.id.iv_tupian3);
    }
}
