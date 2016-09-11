package cn.edu.qzu.ynhelper.entity;

/**
 * Created by Jaren on 2016/9/2.
 */
public class WeatherListItem {

    private String day;
    private int img;
    private String tempHigh;
    private String tempLow;

    public WeatherListItem() {
    }

    public WeatherListItem(String day, int img, String tempHigh, String tempLow) {
        this.day = day;
        this.img = img;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTempHigh() {
        return tempHigh;
    }

    public void setTempHigh(String tempHigh) {
        this.tempHigh = tempHigh;
    }

    public String getTempLow() {
        return tempLow;
    }

    public void setTempLow(String tempLow) {
        this.tempLow = tempLow;
    }
}
