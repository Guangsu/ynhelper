package cn.edu.qzu.ynhelper.db;

import android.os.AsyncTask;

/**
 * Created by Jaren on 2016/9/10.
 */
public class DBTask extends AsyncTask {

    private IDBTask iDBTask;

    public DBTask(IDBTask iDBTask) {
        this.iDBTask = iDBTask;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        iDBTask.query();
        return null;
    }


    public interface IDBTask{
        public void query();
    }

}
