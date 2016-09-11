package cn.edu.qzu.ynhelper.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qzu.ynhelper.entity.Pesticide;

/**
 * Created by Jaren on 2016/7/31.
 */
public class PesticideDBHelper {

    public static final String DB_NAME="pesticide";
    public static final String TABLE_NAME ="pesticide";
    public final int LIMIT = 15;

    private Context context;
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    public PesticideDBHelper(Context context) {
        this.context = context;
        dbHelper = new SQLiteOpenHelper(context,DB_NAME,null,1);
    }

    public List<Pesticide> queryAll(){
        return query(0,"");
    }

    public List<Pesticide> query(int lastRow,String key){
        List<Pesticide> list = new ArrayList<>();
        Pesticide p;
        Cursor cursor = _query(lastRow,key);
        while(cursor.moveToNext()){
            p = new Pesticide();
            p.setId(cursor.getInt(cursor.getColumnIndex("id")));
            p.setName(cursor.getString(cursor.getColumnIndex("name")));
            p.setCatelogy(cursor.getString(cursor.getColumnIndex("category")));
            p.setCompany(cursor.getString(cursor.getColumnIndex("company")));
            list.add(p);
        }
        cursor.close();
        return list;
    }

    private Cursor _query(int lastRow,String key){
        db = dbHelper.getReadableDatabase();
        //return db.rawQuery("SELECT id,name,company,category,substr(introduction,1,20) as abs FROM "+TABLE_NAME+" WHERE name LIKE ?",new String[]{"%"+key+"%"});
        return db.query(TABLE_NAME,new String[]{"id,name,company,category"},"name like ?",new String[]{"%"+key+"%"},null,null,"id",lastRow+","+LIMIT);
    }

    public Pesticide queryDetail(int id){
        Pesticide p = new Pesticide();
        Cursor cursor = _queryDetail(id);
        if(cursor.moveToNext()){
            p.setId(id);
            p.setName(cursor.getString(cursor.getColumnIndex("name")));
            p.setCatelogy(cursor.getString(cursor.getColumnIndex("category")));
            p.setCompany(cursor.getString(cursor.getColumnIndex("company")));
            p.setSpec(cursor.getString(cursor.getColumnIndex("spec")));
            String str = cursor.getString(cursor.getColumnIndex("introduction"));
            str = str.replaceAll("\\\\n", "\n");
            p.setIntroduction(str);
        }
        cursor.close();
        return p;
    }

    private Cursor _queryDetail(int id){
        db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE id=?",new String[]{String.valueOf(id)});
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
