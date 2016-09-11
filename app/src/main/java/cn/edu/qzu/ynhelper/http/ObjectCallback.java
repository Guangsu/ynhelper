package cn.edu.qzu.ynhelper.http;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;

import cn.edu.qzu.ynhelper.entity.DataReturn;

/**
 * Created by Jaren on 2016/8/16.
 */
public class ObjectCallback extends com.zhy.http.okhttp.callback.StringCallback {

    private AbstractObjectCallback absCallback;

    public ObjectCallback(AbstractObjectCallback absCallback) {
        this.absCallback = absCallback;
    }

    @Override
    public void onError(Request request, Exception e) {
        absCallback.onFailure(request,e);
    }

    @Override
    public void onResponse(String response) {
        DataReturn data = new Gson().fromJson(response,DataReturn.class);
        if(data.getErrCode() == 200){
            absCallback.onSuccess(data);
        }else {
            absCallback.onError(data);
        }
    }
}
