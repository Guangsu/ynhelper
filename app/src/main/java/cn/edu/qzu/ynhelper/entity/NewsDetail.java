package cn.edu.qzu.ynhelper.entity;

/**
 * Created by Jaren on 2016/8/16.
 */
public class NewsDetail implements IData{

    private int id;
    private String title;
    private String author;
    private String src;
    private String time;
    private String detail;

    public NewsDetail() {
    }

    public NewsDetail(int id, String title, String author, String src, String time, String detail) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.src = src;
        this.time = time;
        this.detail = detail;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
