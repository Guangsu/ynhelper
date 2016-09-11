package cn.edu.qzu.ynhelper.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Jaren on 2016/7/31.
 */
public class FileUtil {

    public static String TAG = "FileUtil";

    /*从raw目录下拷贝数据库文件filename到/data/data/packagename/databases目录下*/
    public static void copyDBFiles(Context context,int resId,String filename){
        final Context c=context;
        final int res = resId;
        final String f = filename;
        new Thread(new Runnable() {
            @Override
            public void run() {
               _copyDBFiles(c,res,f);
            }
        }).start();
    }

    public static void _copyDBFiles(Context context,int resId,String filename){
        String dirName = "/data/data/"+context.getPackageName()+"/databases";
        File dir = new File(dirName);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File dest = new File(dir, filename);

        try {
            if(dest.exists()){
                dest.delete();
            }
            dest.createNewFile();
            InputStream in = context.getResources().openRawResource(resId);
            int size = in.available();
            byte buf[] = new byte[size];
            in.read(buf);
            in.close();
            FileOutputStream out = new FileOutputStream(dest);
            out.write(buf);
            out.close();
            Log.i(TAG,"Copy database file "+filename+" successfully！!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
