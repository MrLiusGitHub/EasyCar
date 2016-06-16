package viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class TupianVHolderOne extends RecyclerView.ViewHolder {
    public TextView tv_title;
    public TextView tv_src;
    public TextView tv_count;

    public ImageView iv_tupian1;

    public TupianVHolderOne(View itemView) {
        super(itemView);

        tv_title = ((TextView) itemView.findViewById(R.id.tv_title_1));
        tv_src = (TextView) itemView.findViewById(R.id.tv_src_1);
        tv_count = (TextView) itemView.findViewById(R.id.tv_count_1);

        iv_tupian1 = (ImageView) itemView.findViewById(R.id.iv_tupian_1);
    }
}
