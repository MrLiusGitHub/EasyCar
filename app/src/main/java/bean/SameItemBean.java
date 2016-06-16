package bean;

/**
 * Created by Mr.Liu on 2016/6/7.
 */
public class SameItemBean {

    public String message;
    public DataBean data;

    public class DataBean{

        public ShareDataBean shareData;

    }

    public class ShareDataBean{
        public String newsLink;
        public String newsTitle;
        public String newsImg;
    }

}
