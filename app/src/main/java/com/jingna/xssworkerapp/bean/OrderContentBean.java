package com.jingna.xssworkerapp.bean;

/**
 * Created by Administrator on 2019/7/4.
 */

public class OrderContentBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"id":"178","service_type":"家政清洗","order_code":"27216534325890","pretime":"2019-07-06 08:00","address":"黑龙江省-哈尔滨市-道里区-机场路313号","remarks":"不要消毒水，请准时一些谢谢","radio":"2","address_id":"27","lng":"126.522805","lat":"45.691676"}
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
         * id : 178
         * service_type : 家政清洗
         * order_code : 27216534325890
         * pretime : 2019-07-06 08:00
         * address : 黑龙江省-哈尔滨市-道里区-机场路313号
         * remarks : 不要消毒水，请准时一些谢谢
         * radio : 2
         * address_id : 27
         * lng : 126.522805
         * lat : 45.691676
         */

        private String id;
        private String service_type;
        private String order_code;
        private String pretime;
        private String address;
        private String remarks;
        private String radio;
        private String address_id;
        private String lng;
        private String lat;

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

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
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

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
