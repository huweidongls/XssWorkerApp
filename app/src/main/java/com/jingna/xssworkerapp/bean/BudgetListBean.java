package com.jingna.xssworkerapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/7/11.
 */

public class BudgetListBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"list":[{"id":"3","type":"2","price":"250","addtime":"2019-07-08 16:36:50","text":"提现-招商银行","uid":"5"},{"id":"2","type":"2","price":"500","addtime":"2019-07-08 16:09:08","text":"提现-招商银行","uid":"5"},{"id":"1","type":"1","price":"1000","addtime":"2019-07-08 16:08:52","text":"清洗卫生","uid":"5"}],"count_money":{"s":1750,"z":1750}}
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
         * list : [{"id":"3","type":"2","price":"250","addtime":"2019-07-08 16:36:50","text":"提现-招商银行","uid":"5"},{"id":"2","type":"2","price":"500","addtime":"2019-07-08 16:09:08","text":"提现-招商银行","uid":"5"},{"id":"1","type":"1","price":"1000","addtime":"2019-07-08 16:08:52","text":"清洗卫生","uid":"5"}]
         * count_money : {"s":1750,"z":1750}
         */

        private CountMoneyBean count_money;
        private List<ListBean> list;

        public CountMoneyBean getCount_money() {
            return count_money;
        }

        public void setCount_money(CountMoneyBean count_money) {
            this.count_money = count_money;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class CountMoneyBean {
            /**
             * s : 1750
             * z : 1750
             */

            private double s;
            private double z;

            public double getS() {
                return s;
            }

            public void setS(double s) {
                this.s = s;
            }

            public double getZ() {
                return z;
            }

            public void setZ(double z) {
                this.z = z;
            }
        }

        public static class ListBean {
            /**
             * id : 3
             * type : 2
             * price : 250
             * addtime : 2019-07-08 16:36:50
             * text : 提现-招商银行
             * uid : 5
             */

            private String id;
            private String type;
            private String price;
            private String addtime;
            private String text;
            private String uid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }
    }
}
