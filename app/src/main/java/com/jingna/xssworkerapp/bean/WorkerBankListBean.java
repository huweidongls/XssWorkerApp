package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/7/8.
 */

public class WorkerBankListBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","name":"于福","tel":"15244753606","code":"6214834510810915","bank_name":"招商银行","radio":"0","wid":"5"}]
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
         * name : 于福
         * tel : 15244753606
         * code : 6214834510810915
         * bank_name : 招商银行
         * radio : 0
         * wid : 5
         */

        private String id;
        private String name;
        private String tel;
        private String code;
        private String bank_name;
        private String radio;
        private String wid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }
    }
}
