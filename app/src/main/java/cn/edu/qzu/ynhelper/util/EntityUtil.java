package cn.edu.qzu.ynhelper.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.qzu.ynhelper.entity.Disease;
import cn.edu.qzu.ynhelper.entity.News;
import cn.edu.qzu.ynhelper.entity.Pesticide;

/**
 * Created by Jaren on 2016/7/3.
 */
public class EntityUtil {

    public static List<News> JSONArray2NewsList(JSONArray array){
        List<News> list = new ArrayList<News>();
        News news;
        try {
            for(int i = 0;i < array.length();i++){
                news = JSONObject2News(array.getJSONObject(i));
                list.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static News JSONObject2News(JSONObject json){
        News news = null;
        try {
            int id = json.getInt("id");
            String title = json.getString("title");
            String time = json.getString("time");
            String img = json.getString("image_url");
            news = new News( id,title,time, img);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }

    public static List<Disease> JSONArray2DiseaseList(JSONArray array){
        List<Disease> list = new ArrayList<Disease>();
        Disease disease;
        try {
            for(int i = 0;i < array.length();i++){
                disease = JSONObject2Disease(array.getJSONObject(i));
                list.add(disease);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Disease JSONObject2Disease(JSONObject json){
        /*Disease disease = null;
        try {
            int id = json.getInt("id");
            int type = json.getInt("type");
            String name = json.getString("name");
            String abs = json.getString("abstract");
            String content = json.getString("content");
            disease = new Disease( );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return disease;*/
        return new Disease();
    }

    public static List<Pesticide> JSONArray2PesticideList(JSONArray array){
        List<Pesticide> list = new ArrayList<Pesticide>();
        Pesticide pesticide;
        try {
            for(int i = 0;i < array.length();i++){
                pesticide = JSONObject2Pesticide(array.getJSONObject(i));
                list.add(pesticide);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Pesticide JSONObject2Pesticide(JSONObject json){
        Pesticide pesticide = null;
        try {
            int id = json.getInt("id");
            String name = json.getString("name");
            String abs = json.getString("abstract");
            String content = json.getString("content");
            //pesticide = new Pesticide( id, name,abs,content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pesticide;
    }

    /*public static Map<String,String> listMap2Map(List<Map<String,String>> list){
        Map<String,String> map = new HashMap<>();
        for(Map<String,String> map1:list){
            map.put(map1.get("key"),);
        }
        return map;
    }*/

    public static Map<String,String> string2Map(String str){
        Map<String,String> map = new HashMap<String, String>();
        str = str.replaceAll("\\\\\":\\\\", "\\\\\"@@@###\\\\");
        str = str.replaceAll("\\\\\",\\\\", "\\\\\"@#@#@#\\\\");
        str = str.replaceAll("\\{", "");
        str = str.replaceAll("\\}", "");
        str = str.replaceAll("\"", "");
        str = str.replaceAll("\\\\n", "\n");
        str = str.replaceAll("\\\\", "");
        String []array = str.split("@#@#@#");
        String []array1;
        for(String item:array){
            array1 = item.split("@@@###");
            if(array1.length<2) continue;
            map.put(array1[0], array1[1]);
        }
        return map;
    }
}
