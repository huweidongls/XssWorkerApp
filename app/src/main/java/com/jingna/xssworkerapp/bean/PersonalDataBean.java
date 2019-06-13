package com.jingna.xssworkerapp.bean;

/**
 * Created by Administrator on 2019/6/11.
 */

public class PersonalDataBean {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"id":"5","headimg":"include/timg.jpg","user":"18686817319","real":"0","workexperience":"0","workertrain":"0","username":"工人_7319"}
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
         * headimg : include/timg.jpg
         * user : 18686817319
         * real : 0
         * workexperience : 0
         * workertrain : 0
         * username : 工人_7319
         */

        private String id;
        private String headimg;
        private String user;
        private String real;
        private String workexperience;
        private String workertrain;
        private String username;
        private String habitualresidence;

        public String getHabitualresidence() {
            return habitualresidence;
        }

        public void setHabitualresidence(String habitualresidence) {
            this.habitualresidence = habitualresidence;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getReal() {
            return real;
        }

        public void setReal(String real) {
            this.real = real;
        }

        public String getWorkexperience() {
            return workexperience;
        }

        public void setWorkexperience(String workexperience) {
            this.workexperience = workexperience;
        }

        public String getWorkertrain() {
            return workertrain;
        }

        public void setWorkertrain(String workertrain) {
            this.workertrain = workertrain;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
