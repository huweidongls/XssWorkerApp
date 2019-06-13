package com.jingna.xssworkerapp.bean;

/**
 * Created by Administrator on 2019/5/22.
 */

public class LocationBean {

    /**
     * code : 200
     * message : 定位成功!
     * obj : {"id":"1","city_area":"黑龙江省-鸡西市"}
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
         * id : 1
         * city_area : 黑龙江省-鸡西市
         */

        private String id;
        private String city_area;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity_area() {
            return city_area;
        }

        public void setCity_area(String city_area) {
            this.city_area = city_area;
        }
    }
}
