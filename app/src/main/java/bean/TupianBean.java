package bean;

import java.util.List;

/**
 * Created by Mr.Liu on 2016/6/6.
 */
public class TupianBean {
    public String message;
    public List<DataItemBean> data;

    public class DataItemBean{
        public int newsId;
        public String picCover;
        public String title;
        public String src;
        public String commentCount;
        public String lastModify;
    }

}


