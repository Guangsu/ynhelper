package cn.edu.qzu.ynhelper.http;

import com.squareup.okhttp.Request;

import cn.edu.qzu.ynhelper.entity.DataReturn;

/**
 * Created by Jaren on 2016/8/16.
 */
public abstract class AbstractObjectCallback {

    public void onSuccess(DataReturn data){}
    public void onError(DataReturn data){}
    public void onFailure(Request request,Exception e){}


}
