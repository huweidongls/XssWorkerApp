package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/5/23.
 */

public class OpenCityListBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","city_area":"黑龙江省-鸡西市"},{"id":"2","city_area":"黑龙江省-哈尔滨市"}]
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
