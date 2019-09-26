package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/26.
 */

public class PeixunBean {
    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"11","organizationname":"哈尔滨科技","start_time":"2016-09-26","end_time":"2019-09-26","description":"哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技","gid":"21"}]
     */

    private int code;
    private String message;
    private String tel;
    private List<ObjBean> obj;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

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
         * id : 11
         * organizationname : 哈尔滨科技
         * start_time : 2016-09-26
         * end_time : 2019-09-26
         * description : 哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技哈尔滨科技
         * gid : 21
         */

        private String id;
        private String organizationname;
        private String start_time;
        private String end_time;
        private String description;
        private String gid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrganizationname() {
            return organizationname;
        }

        public void setOrganizationname(String organizationname) {
            this.organizationname = organizationname;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }
    }
}
