package com.jingna.xssworkerapp.bean;

/**
 * Created by Administrator on 2019/6/10.
 */

public class UserInfoBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"order_count":"0","theorder":"0","order_task":"0","price":"0","headimg":"include/timg.jpg","real":"0"}
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
         * order_count : 0
         * theorder : 0
         * order_task : 0
         * price : 0
         * headimg : include/timg.jpg
         * real : 0
         */

        private String order_count;
        private String theorder;
        private String order_task;
        private String price;
        private String headimg;
        private String real;
        private String name;
        private String tel;
        private String VersionName;
        private String VersionCode;

        public String getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(String VersionCode) {
            this.VersionCode = VersionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String VersionName) {
            this.VersionName = VersionName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrder_count() {
            return order_count;
        }

        public void setOrder_count(String order_count) {
            this.order_count = order_count;
        }

        public String getTheorder() {
            return theorder;
        }

        public void setTheorder(String theorder) {
            this.theorder = theorder;
        }

        public String getOrder_task() {
            return order_task;
        }

        public void setOrder_task(String order_task) {
            this.order_task = order_task;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getReal() {
            return real;
        }

        public void setReal(String real) {
            this.real = real;
        }
    }
}
