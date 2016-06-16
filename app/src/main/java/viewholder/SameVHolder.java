package viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class SameVHolder extends RecyclerView.ViewHolder {

    public ImageView iv_same;
    public TextView tv_title;
    public TextView tv_time;

    public SameVHolder(View itemView) {
        super(itemView);

        iv_same = (ImageView) itemView.findViewById(R.id.iv_img_same);
        tv_title= (TextView) itemView.findViewById(R.id.tv_title_same);
        tv_time= (TextView) itemView.findViewById(R.id.tv_time_same);
    }
}
