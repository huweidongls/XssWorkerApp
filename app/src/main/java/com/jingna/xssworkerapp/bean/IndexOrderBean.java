package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/6/14.
 */

public class IndexOrderBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"user_radio":"1","list":[{"id":"62","service_type":"家政清洗","pretime":"2019-06-14 10:42","address":"北京市-北京市-东城区-hncf","addtime":"3小时前","coupon":"暂无优惠券"}]}
     */

    private int code;
    private String message;
    private ObjBean obj;

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

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * user_radio : 1
         * list : [{"id":"62","service_type":"家政清洗","pretime":"2019-06-14 10:42","address":"北京市-北京市-东城区-hncf","addtime":"3小时前","coupon":"暂无优惠券"}]
         */

        private String user_radio;
        private List<ListBean> list;

        public String getUser_radio() {
            return user_radio;
        }

        public void setUser_radio(String user_radio) {
            this.user_radio = user_radio;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 62
             * service_type : 家政清洗
             * pretime : 2019-06-14 10:42
             * address : 北京市-北京市-东城区-hncf
             * addtime : 3小时前
             * coupon : 暂无优惠券
             */

            private String id;
            private String service_type;
            private String pretime;
            private String address;
            private String addtime;
            private String coupon;
            private String status;

            public String getRadio() {
                return status;
            }

            public void setRadio(String status) {
                this.status = status;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getService_type() {
                return service_type;
            }

            public void setService_type(String service_type) {
                this.service_type = service_type;
            }

            public String getPretime() {
                return pretime;
            }

            public void setPretime(String pretime) {
                this.pretime = pretime;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getCoupon() {
                return coupon;
            }

            public void setCoupon(String coupon) {
                this.coupon = coupon;
            }
        }
    }
}
