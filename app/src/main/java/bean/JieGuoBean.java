package bean;

import java.util.List;

/**
 * Created by ldc on 2016/6/9.
 */
public class JieGuoBean {
    public int status;
    public String message;
    public DataBean data;
    public class DataBean{
        public int SerialCount;
        public int CarCount;
        public List<ListBean> List;
    }
    public class ListBean{
        public String Name;
        public String Picture;
        public String DealerPrice;
    }

}
