package cn.edu.qzu.ynhelper.util;

import android.content.Context;

import cn.edu.qzu.ynhelper.entity.User;

/**
 * Created by Jaren on 2016/8/16.
 */
public class UserUtil {

    public static User getLocalUser(Context context){
        Object o = SharedPreferencesHelper.getParam(context,SharedPreferencesHelper.USER,"id",-1);
        if(o == null){
            return null;
        }
        int uid = (Integer)o;

        String username = (String) SharedPreferencesHelper.getParam(context,SharedPreferencesHelper.USER,"username","");
        String password = (String) SharedPreferencesHelper.getParam(context,SharedPreferencesHelper.USER,"password","");
        String img = (String) SharedPreferencesHelper.getParam(context,SharedPreferencesHelper.USER,"img","");
        String token = (String) SharedPreferencesHelper.getParam(context,SharedPreferencesHelper.USER,"token","");
        User user = new User(uid,username,password,img,token);
        return user;
    }
}
