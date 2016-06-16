package bean;

import java.util.List;

import viewholder.SameVHolder;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class SameBean {

    public String status;
    public DataBean data;

    public class DataBean {
        public int count;
        public List<ListBean> list;

    }

    public class ListBean{
        public int newsId;
        public String title;
        public String picCover;
        public String lastModify;
        public String publishTime;
        public int type;
        public String commentCount;
        public String nickName;
    }
}
