package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/6/13.
 */

public class WorkerTypeListBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","typename":"家政清洗","two":[{"id":"21","typename":"预留"},{"id":"35","typename":"家政清洗"}]},{"id":"2","typename":"家电清洗","two":[{"id":"36","typename":"家电清洗"}]},{"id":"3","typename":"上门洗车","two":[{"id":"15","typename":"普通洗车"},{"id":"31","typename":"蒸汽洗车"}]},{"id":"4","typename":"机电设备清洗","two":[{"id":"32","typename":"重油污设备清洗"},{"id":"33","typename":"电气设备清洗"},{"id":"34","typename":"机械设备"}]},{"id":"5","typename":"酒店/厨房","two":[{"id":"37","typename":"酒店/厨房"}]},{"id":"6","typename":"学校/KTV","two":[{"id":"38","typename":"学校/KTV"}]},{"id":"7","typename":"新楼装修除甲醛","two":[{"id":"39","typename":"新楼装修除甲醛"}]},{"id":"8","typename":"管路清洗","two":[{"id":"40","typename":"管路清洗"}]},{"id":"9","typename":"室内漏点查找","two":[{"id":"41","typename":"室内漏点查找"}]},{"id":"10","typename":"劳动力服务","two":[{"id":"42","typename":"劳动力服务"}]},{"id":"12","typename":"车辆服务","two":[{"id":"16","typename":"小型货车"},{"id":"19","typename":"预留"}]}]
     */

    private int code;
    private String message;
    private List<ObjBean> obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * id : 1
         * typename : 家政清洗
         * two : [{"id":"21","typename":"预留"},{"id":"35","typename":"家政清洗"}]
         */

        private String id;
        private String typename;
        private List<TwoBean> two;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public List<TwoBean> getTwo() {
            return two;
        }

        public void setTwo(List<TwoBean> two) {
            this.two = two;
        }

        public static class TwoBean {
            /**
             * id : 21
             * typename : 预留
             */

            private String id;
            private String typename;
            private int isSelect = 0;

            public int getIsSelect() {
                return isSelect;
            }

            public void setIsSelect(int isSelect) {
                this.isSelect = isSelect;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }
        }
    }
}
