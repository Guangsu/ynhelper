package cn.edu.qzu.ynhelper.entity;

/**
 * Created by Jaren on 2016/8/30.
 */
public class WeatherType {

    private String weatid;
    private String weather;
    private String weather_icon;

    public WeatherType() {
    }

    public String getWeatid() {
        return weatid;
    }

    public void setWeatid(String weatid) {
        this.weatid = weatid;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }
}
