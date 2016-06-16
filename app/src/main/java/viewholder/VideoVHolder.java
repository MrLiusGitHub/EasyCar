package viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;

/**
 * Created by Mr.Liu on 2016/6/9.
 */
public class VideoVHolder extends RecyclerView.ViewHolder{


        public ImageView img;
        public TextView tv_title;
        public TextView tv_src;

        public VideoVHolder(View convertView) {
            super(convertView);

            img = (ImageView) convertView.findViewById(R.id.img_video);
            tv_title = (TextView) convertView.findViewById(R.id.tv_title_video);
            tv_src = (TextView) convertView.findViewById(R.id.tv_src_video);
        }
}
