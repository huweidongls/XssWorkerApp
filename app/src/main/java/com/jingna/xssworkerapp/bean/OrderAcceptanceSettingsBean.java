package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/6/13.
 */

public class OrderAcceptanceSettingsBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"id":"5","service_time_radio":"0","goodmorning":"08:00","night":"17:00","is_default":"0","type":[{"id":"1","type_name":"瓦工","uid":"5"}]}
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
         * id : 5
         * service_time_radio : 0
         * goodmorning : 08:00
         * night : 17:00
         * is_default : 0
         * type : [{"id":"1","type_name":"瓦工","uid":"5"}]
         */

        private String id;
        private String service_time_radio;
        private String goodmorning;
        private String night;
        private String is_default;
        private List<TypeBean> type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getService_time_radio() {
            return service_time_radio;
        }

        public void setService_time_radio(String service_time_radio) {
            this.service_time_radio = service_time_radio;
        }

        public String getGoodmorning() {
            return goodmorning;
        }

        public void setGoodmorning(String goodmorning) {
            this.goodmorning = goodmorning;
        }

        public String getNight() {
            return night;
        }

        public void setNight(String night) {
            this.night = night;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public List<TypeBean> getType() {
            return type;
        }

        public void setType(List<TypeBean> type) {
            this.type = type;
        }

        public static class TypeBean {
            /**
             * id : 1
             * type_name : 瓦工
             * uid : 5
             */

            private String id;
            private String typename;
            private String uid;

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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }
    }
}
