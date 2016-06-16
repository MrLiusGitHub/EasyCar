package viewholder;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ldc.easycar.R;

import indicator.CirclePageIndicator;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class HeaderVHolder extends RecyclerView.ViewHolder {

    public ViewPager pager;
    public TextView title;
    public CirclePageIndicator indicator;


    public HeaderVHolder(View itemView) {
        super(itemView);

        pager = (ViewPager) itemView.findViewById(R.id.pager_head);
        title = (TextView) itemView.findViewById(R.id.title_head);
        indicator = (CirclePageIndicator) itemView.findViewById(R.id.indi_head);
    }


}
