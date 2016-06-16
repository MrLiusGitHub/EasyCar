package bean;

import java.util.List;

/**
 * Created by Mr.Liu on 2016/6/6.
 */
public class TupianActivityPagerBean {
    public String status;
    public DataBean data;

    public class DataBean{
        public ListBean list;

    }

    public class ListBean{
        public String title;
        public List<AlbumBean> albums;
        public ShareTo shareToCheYou;
    }

    public class AlbumBean{
        public String imageUrl;
        public String content;
    }

    public class ShareTo{
        public String newslink;
        public String newstitle;
    }
}
