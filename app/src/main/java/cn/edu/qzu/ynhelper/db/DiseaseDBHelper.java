package cn.edu.qzu.ynhelper.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.qzu.ynhelper.entity.Disease;
import cn.edu.qzu.ynhelper.util.EntityUtil;

/**
 * Created by Jaren on 2016/7/31.
 */
public class DiseaseDBHelper {

    public static final String DB_NAME="disease";
    public static final String TABLE_NAME="disease";
    public static final int LIMIT = 15;

    private Context context;
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    public DiseaseDBHelper(Context context) {
        this.context = context;
        dbHelper = new SQLiteOpenHelper(context,DB_NAME,null,1);
    }

    public List<Disease> queryAll(int lastRow){
        return query(lastRow,"");
    }

    public List<Disease> query(int lastRow,String key){
        List<Disease> list = new ArrayList<>();
        Cursor cursor = _query(lastRow,key);
        Disease d;
        while(cursor.moveToNext()){
            d = new Disease();
            d.setCode(cursor.getString(cursor.getColumnIndex("code")));
            d.setName(cursor.getString(cursor.getColumnIndex("name")));
            d.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
            d.setCard(null);
            d.setContent(null);
            list.add(d);
        }
        cursor.close();
        return list;
    }

    private Cursor _query(int lastRow,String key){
        db = dbHelper.getReadableDatabase();
       // return db.rawQuery("SELECT code,name,summary FROM "+TABLE_NAME+" WHERE name LIKE ?",new String[]{"%"+key+"%"});
        return db.query(TABLE_NAME,new String[]{"code","name","summary"},"name like ?",new String[]{"%"+key+"%"},null,null,"id",lastRow+","+LIMIT);
    }

    public Disease queryDetail(String code){
        Disease disease = new Disease();
        disease.setCode(code);
        Cursor cursor = _queryDetail(code);
        if(cursor.moveToNext()){
            disease.setName(cursor.getString(cursor.getColumnIndex("name")));
            disease.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
            Map<String,String> cardMap =  EntityUtil.string2Map( cursor.getString(cursor.getColumnIndex("card")));
            Map<String,String> contentMap = EntityUtil.string2Map(cursor.getString(cursor.getColumnIndex("content")));
            List<Map<String,String>> basicInfoList = new ArrayList<Map<String,String>>(),contentList = new ArrayList<Map<String,String>>();
            Set<String> keySet = cardMap.keySet();
            Map<String,String> map;
            for(String key:keySet){
                map = new HashMap<>();
                map.put("key", key);
                map.put("value", cardMap.get(key));
                basicInfoList.add(map);
            }

            keySet = contentMap.keySet();
            for(String key:keySet){
                map = new HashMap<>();
                map.put("key", key);
                map.put("value", contentMap.get(key));
                contentList.add(map);
            }

            disease.setCard(basicInfoList);
            disease.setContent(contentList);
        }
        cursor.close();
        return disease;
    }

    private Cursor _queryDetail(String code){
        db = dbHelper.getReadableDatabase();
        //return db.rawQuery("SELECT name,summary,card,content FROM "+TABLE_NAME+" WHERE code=?",new String[]{code});
        return db.query(TABLE_NAME,new String[]{"name","summary","card","content"}," code=?",new String[]{code},null,null,null);
    }

    class SQLiteOpenHelper  extends android.database.sqlite.SQLiteOpenHelper{

        public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
