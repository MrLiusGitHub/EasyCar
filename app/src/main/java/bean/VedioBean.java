package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Liu on 2016/6/9.
 */
public class VedioBean implements Serializable {

    public String status;
    public DataBean data;

    public class DataBean{

        public List<ListBean> list;

        public class ListBean implements Serializable {

            public int videoid;
            public String title;
            public String sourcename;
            public String modifytime;
            public UserBean user;
            public String coverimg;

            //服务页面所需要的信息
            public String name;
            public String score;
            public String bizHourBegin;
            public String bizHourEnd;
            public String img;
            public String distance;
            public List<ItemBean> serviceItems;
            public String address;
            public String phone;
            public Double longitude;
            public Double latitude;

            public class ItemBean implements Serializable{
                public String name;
                public String ktPrice;
            }

            public class UserBean implements Serializable{
                public int userId;
            }
        }

    }




}
