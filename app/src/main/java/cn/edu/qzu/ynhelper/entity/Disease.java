package cn.edu.qzu.ynhelper.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by Jaren on 2016/7/4.
 */
public class Disease {

    private int id;
    private String code;
    private String name;
    private String summary;
    private List<Map<String,String>> card;
    private List<Map<String,String>> content;

    public Disease() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Map<String,String>> getCard() {
        return card;
    }

    public void setCard(List<Map<String,String>> card) {
        this.card = card;
    }

    public List<Map<String,String>> getContent() {
        return content;
    }

    public void setContent(List<Map<String,String>> content) {
        this.content = content;
    }
}
