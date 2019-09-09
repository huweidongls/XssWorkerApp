package com.jingna.xssworkerapp.bean;

/**
 * Created by Administrator on 2019/9/9.
 */

public class RealNameHuiXianBean {
    /**
     * code : 200
     * message : 上传成功!
     * obj : {"name":"齐连超","idcard":"232325199809252616","department":"兰西县公安局","end":"20261219","imgz":"1.jpg","imgf":"2.jpg"}
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
         * name : 齐连超
         * idcard : 232325199809252616
         * department : 兰西县公安局
         * end : 20261219
         * imgz : 1.jpg
         * imgf : 2.jpg
         */

        private String name;
        private String idcard;
        private String department;
        private String end;
        private String imgz;
        private String imgf;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getImgz() {
            return imgz;
        }

        public void setImgz(String imgz) {
            this.imgz = imgz;
        }

        public String getImgf() {
            return imgf;
        }

        public void setImgf(String imgf) {
            this.imgf = imgf;
        }
    }
}
