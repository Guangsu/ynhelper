package cn.edu.qzu.ynhelper.event;

/**
 * Created by Jaren on 2016/7/4.
 */
public class PesticideSearchEvent {

    private String keyword;

    public PesticideSearchEvent(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
