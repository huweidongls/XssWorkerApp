package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/6/11.
 */

public class WorkExperienceListBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","companyname":"哈尔滨静娜科技","start_time":"2016-5-1","end_time":"2019-5-1","position":"php","salary":"100000","jobdescription":"哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技","gid":"3"}]
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
         * companyname : 哈尔滨静娜科技
         * start_time : 2016-5-1
         * end_time : 2019-5-1
         * position : php
         * salary : 100000
         * jobdescription : 哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技哈尔滨静娜科技
         * gid : 3
         */

        private String id;
        private String companyname;
        private String start_time;
        private String end_time;
        private String position;
        private String salary;
        private String jobdescription;
        private String gid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCompanyname() {
            return companyname;
        }

        public void setCompanyname(String companyname) {
            this.companyname = companyname;
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

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getJobdescription() {
            return jobdescription;
        }

        public void setJobdescription(String jobdescription) {
            this.jobdescription = jobdescription;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }
    }
}
