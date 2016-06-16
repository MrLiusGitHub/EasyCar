package bean;

import java.util.List;

/**
 * Created by ldc on 2016/6/8.
 */
public class XinCheBean {
    public String success;
    public List<ItemBean> data;

    public class ItemBean{
        public String Img;
        public String Price;
        public String ShowName;
    }
}
