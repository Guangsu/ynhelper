package cn.edu.qzu.ynhelper.entity;


import org.json.JSONArray;

/**
 * Created by Jaren on 2016/7/1.
 */
public class News {

    public final static int FOCUS_NEWS = 1;
    public final static int TRENDS = 2;
    public  final static int FOOD_SAFETY = 3;
    public final static int POLICY = 4;

    private int id;
    private String title;
    private String time;
    private String img;

    public News() {
    }

    public News(int id, String title,   String time,  String img) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static News fromJSONArray(JSONArray array){
        News news = new News();
        try{
            news.setId(array.getInt(0));
            news.setTitle(array.getString(1));
            news.setImg(array.getString(2));
            news.setTime(array.getString(3));
        } catch(Exception e){

        }
        return news;
    }


}
