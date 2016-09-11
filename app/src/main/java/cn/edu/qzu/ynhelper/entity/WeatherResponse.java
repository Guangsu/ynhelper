package cn.edu.qzu.ynhelper.entity;

import java.util.List;

/**
 * Created by Jaren on 2016/8/16.
 */
public class WeatherResponse {

    private String success;
    private String msg;
    private List<Weather> result;

    public WeatherResponse() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Weather> getWeather() {
        return result;
    }

    public void setWeather(List<Weather> result) {
        this.result = result;
    }
}
