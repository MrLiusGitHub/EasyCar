package bean;

import java.util.List;

/**
 * Created by ldc on 2016/6/8.
 */
public class PaiHangBean {
    public String status;
    public String message;
    public DataCount data;

    public class DataCount{
        public String count;
        public List<ListDataBean> list;

        public class ListDataBean{
            public String SerialName;
            public String WhiteCoverImg;
            public String Index;
        }
    }
}
