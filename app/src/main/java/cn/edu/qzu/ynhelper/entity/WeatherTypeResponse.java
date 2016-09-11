package cn.edu.qzu.ynhelper.entity;

import java.util.List;

/**
 * Created by Jaren on 2016/8/30.
 */
public class WeatherTypeResponse {

    private String success;
    private List<WeatherType> result;

    public WeatherTypeResponse() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<WeatherType> getResult() {
        return result;
    }

    public void setResult(List<WeatherType> result) {
        this.result = result;
    }
}
