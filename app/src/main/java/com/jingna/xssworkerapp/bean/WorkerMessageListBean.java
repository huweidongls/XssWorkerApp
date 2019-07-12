package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/7/12.
 */

public class WorkerMessageListBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","title":"订单消息","addtime":"2019/7/12","text":"订单:54454545645645业主支付100元已经收在钱包中","radio":"0","type":"1","oid":"5","uid":"5"}]
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
         * title : 订单消息
         * addtime : 2019/7/12
         * text : 订单:54454545645645业主支付100元已经收在钱包中
         * radio : 0
         * type : 1
         * oid : 5
         * uid : 5
         */

        private String id;
        private String title;
        private String addtime;
        private String text;
        private String radio;
        private String type;
        private String oid;
        private String uid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOid() {
            return oid;
        }

        public void setOid(String oid) {
            this.oid = oid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
