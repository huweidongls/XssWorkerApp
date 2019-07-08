package com.jingna.xssworkerapp.bean;

/**
 * Created by Administrator on 2019/7/8.
 */

public class MyWalletBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"id":"5","balance":"0","withdrawable":"0","settlement":"0"}
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
         * balance : 0
         * withdrawable : 0
         * settlement : 0
         */

        private String id;
        private String balance;
        private String withdrawable;
        private String settlement;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getWithdrawable() {
            return withdrawable;
        }

        public void setWithdrawable(String withdrawable) {
            this.withdrawable = withdrawable;
        }

        public String getSettlement() {
            return settlement;
        }

        public void setSettlement(String settlement) {
            this.settlement = settlement;
        }
    }
}
