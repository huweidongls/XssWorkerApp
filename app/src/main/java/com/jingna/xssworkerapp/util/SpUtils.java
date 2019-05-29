package com.jingna.xssworkerapp.util;

import android.content.Context;

import com.vise.xsnow.cache.SpCache;

/**
 * Created by Administrator on 2018/10/26.
 */

public class SpUtils {

    private static SpCache spCache;
    public static String UID = "uid";
    public static String PHONENUM = "phonenum";
    public static String TOKEN = "token";
    public static String CITY_ID = "city_id";
    public static String CITY_NAME = "city_name";

    public static void setCityName(Context context, String cityName){
        spCache = new SpCache(context, "user_info");
        spCache.put(CITY_NAME, cityName);
    }

    public static String getCityName(Context context){
        spCache = new SpCache(context, "user_info");
        return spCache.get(CITY_NAME, "");
    }

    public static void setCityId(Context context, String cityId){
        spCache = new SpCache(context, "user_info");
        spCache.put(CITY_ID, cityId);
    }

    public static String getCityId(Context context){
        spCache = new SpCache(context, "user_info");
        return spCache.get(CITY_ID, "");
    }

    public static void setToken(Context context, String token){
        spCache = new SpCache(context, "user_info");
        spCache.put(TOKEN, token);
    }

    public static String getToken(Context context){
        spCache = new SpCache(context, "user_info");
        return spCache.get(TOKEN, "0");
    }

    public static void setPhoneNum(Context context, String phonenum){
        spCache = new SpCache(context, "user_info");
        spCache.put(PHONENUM, phonenum);
    }

    public static String getPhoneNum(Context context){
        spCache = new SpCache(context, "user_info");
        return spCache.get(PHONENUM, "0");
    }

    public static void setUid(Context context, String userid){
        spCache = new SpCache(context, "user_info");
        spCache.put(UID, userid);
    }

    public static String getUid(Context context){
        spCache = new SpCache(context, "user_info");
        return spCache.get(UID, "0");
    }

    public static void clear(Context context){
        spCache = new SpCache(context, "user_info");
        spCache.clear();
    }

}
