package cn.edu.qzu.ynhelper;

import android.app.Application;
import android.content.Context;

import cn.edu.qzu.ynhelper.util.FileUtil;

/**
 * Created by Jaren on 2016/8/1.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileUtil.copyDBFiles(getApplicationContext(),R.raw.disease,"disease");
        FileUtil.copyDBFiles(getApplicationContext(),R.raw.pesticide,"pesticide");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
