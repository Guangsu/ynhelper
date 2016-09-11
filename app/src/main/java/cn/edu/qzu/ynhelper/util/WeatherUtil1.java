package cn.edu.qzu.ynhelper.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.entity.Weather;
import cn.edu.qzu.ynhelper.entity.WeatherListItem;
import cn.edu.qzu.ynhelper.entity.WeatherResponse;
import cn.edu.qzu.ynhelper.entity.WeatherType;
import cn.edu.qzu.ynhelper.entity.WeatherTypeResponse;

/**
 * Created by Jaren on 2016/4/1.
 */
public class WeatherUtil1 {

    public static AMapLocationClientOption mLocationOption = null;
    public static AMapLocationClient mlocationClient = null;


    public static void requestWeather(Context context, IWeather iWeather) {
        startLoc(context, new LocationCallback(iWeather));
        Log.i("WeatherSHA1",WeatherUtil.sHA1(context));
    }

    public static void startLoc(final Context context, final ILocation iLocation) {
        startLoc(context, new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    mlocationClient.stopLocation();
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        iLocation.onSuccess(amapLocation.getCity().substring(0,2));
                        Log.i("WeatherUtil","City:"+ amapLocation.getCity()+",District:"+amapLocation.getDistrict()+",AdCode:"+amapLocation.getAdCode());
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        iLocation.onError(amapLocation.getErrorInfo());
                    }
                }
            }
        });
    }

    public static void startLoc(Context context, AMapLocationListener listener) {
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        //设置定位监听
        mlocationClient.setLocationListener(listener);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        // 启动定位
        mlocationClient.startLocation();
    }

    interface ILocation {
        void onSuccess(String city);
        void onError(String msg);
    }

    public interface  IWeather{
        void onSuccess(List<Weather> list);
        void onError(String msg);
    }

    static class LocationCallback implements ILocation {

        private IWeather iWeather;

        public LocationCallback(IWeather iWeather) {
            this.iWeather = iWeather;
        }

        @Override
        public void onSuccess(String city) {
            OkHttpUtils.get().url("http://api.k780.com:88")
                    .addParams("app","weather.future")
                    .addParams("appkey","20639")
                    .addParams("sign","0515afed42ddf042c8349e2c80127fe1")
                    .addParams("format","json")
                    .addParams("weaid",city).build().execute(new WeatherCallback(iWeather));
        }

        @Override
        public void onError(String msg) {
            iWeather.onError(msg);
        }
    }

    static class WeatherCallback extends StringCallback {

        private IWeather iWeather;

        public WeatherCallback(IWeather iWeather) {
            this.iWeather = iWeather;
        }

        @Override
        public void onError(Request request, Exception e) {
            iWeather.onError(e.getMessage());
        }

        @Override
        public void onResponse(String response) {
            WeatherResponse wResponse = new Gson().fromJson(response, WeatherResponse.class);
            if("1".equals(wResponse.getSuccess())){
                List<Weather> list = wResponse.getWeather();
                iWeather.onSuccess(list);
            }else {
                iWeather.onError(wResponse.getMsg());
            }
        }
    }

    public static int getWeatherDrawableRes(String iconId){
        int resId = 0;
        switch (iconId){
            case "0":
                resId = R.drawable.weather_00;
                break;
            case "1":
                resId =  R.drawable.weather_01;
                break;
            case "2":
                resId = R.drawable.weather_02;
                break;
            case "3":
                resId = R.drawable.weather_03;
                break;
            case "4":
                resId = R.drawable.weather_04;
                break;
            case "5":
                resId = R.drawable.weather_05;
                break;
            case "6":
                resId = R.drawable.weather_06;
                break;
            case "7":
                resId = R.drawable.weather_07;
                break;
            case "8":
                resId = R.drawable.weather_08;
                break;
            case "9":
                resId = R.drawable.weather_09;
                break;
            case "10":
                resId = R.drawable.weather_10;
                break;
            case "11":
                resId = R.drawable.weather_11;
                break;
            case "12":
                resId = R.drawable.weather_12;
                break;
            case "13":
                resId = R.drawable.weather_13;
                break;
            case "14":
                resId = R.drawable.weather_14;
                break;
            case "15":
                resId = R.drawable.weather_15;
                break;
            case "16":
                resId = R.drawable.weather_16;
                break;
            case "17":
                resId = R.drawable.weather_17;
                break;
            case "18":
                resId = R.drawable.weather_18;
                break;
            case "19":
                resId = R.drawable.weather_19;
                break;
            case "20":
                resId = R.drawable.weather_20;
                break;
            case "21":
                resId = R.drawable.weather_21;
                break;
            case "22":
                resId = R.drawable.weather_22;
                break;
            case "23":
                resId = R.drawable.weather_23;
                break;
            case "24":
                resId = R.drawable.weather_24;
                break;
            case "25":
                resId = R.drawable.weather_25;
                break;
            case "26":
                resId = R.drawable.weather_26;
                break;
            case "27":
                resId = R.drawable.weather_27;
                break;
            case "28":
                resId = R.drawable.weather_28;
                break;
            case "29":
                resId = R.drawable.weather_29;
                break;
            case "30":
                resId = R.drawable.weather_30;
                break;
            case "31":
                resId = R.drawable.weather_31;
                break;
            case "53":
                resId = R.drawable.weather_53;
                break;
        }
        return resId;
    }

    public static Drawable getWeatherDrawable(Context context,String iconId){
        Drawable drawable = context.getResources().getDrawable(getWeatherDrawableRes(iconId));
        return drawable;
    }

    public static String getWeatherIconId(String weahterId){
        WeatherTypeResponse response  = new Gson().fromJson(weatherType,WeatherTypeResponse.class);
        List<WeatherType> types = response.getResult();
        for(WeatherType type:types){
            if(weahterId.equals(type.getWeatid())){
                return type.getWeather_icon();
            }
        }
        return null;
    }

    static String weatherType = "{\"success\":\"1\",\"result\":[{\"weatid\":\"1\",\"weather\":\"晴\",\"weather_icon\":\"0\"},{\"weatid\":\"2\",\"weather\":\"多云\",\"weather_icon\":\"1\"},{\"weatid\":\"3\",\"weather\":\"阴\",\"weather_icon\":\"2\"},{\"weatid\":\"4\",\"weather\":\"阵雨\",\"weather_icon\":\"3\"},{\"weatid\":\"5\",\"weather\":\"雷阵雨\",\"weather_icon\":\"4\"},{\"weatid\":\"6\",\"weather\":\"雷阵雨有冰雹\",\"weather_icon\":\"5\"},{\"weatid\":\"7\",\"weather\":\"雨夹雪\",\"weather_icon\":\"6\"},{\"weatid\":\"8\",\"weather\":\"小雨\",\"weather_icon\":\"7\"},{\"weatid\":\"9\",\"weather\":\"中雨\",\"weather_icon\":\"8\"},{\"weatid\":\"10\",\"weather\":\"大雨\",\"weather_icon\":\"9\"},{\"weatid\":\"11\",\"weather\":\"暴雨\",\"weather_icon\":\"10\"},{\"weatid\":\"12\",\"weather\":\"大暴雨\",\"weather_icon\":\"11\"},{\"weatid\":\"13\",\"weather\":\"特大暴雨\",\"weather_icon\":\"12\"},{\"weatid\":\"14\",\"weather\":\"阵雪\",\"weather_icon\":\"13\"},{\"weatid\":\"15\",\"weather\":\"小雪\",\"weather_icon\":\"14\"},{\"weatid\":\"16\",\"weather\":\"中雪\",\"weather_icon\":\"15\"},{\"weatid\":\"17\",\"weather\":\"大雪\",\"weather_icon\":\"16\"},{\"weatid\":\"18\",\"weather\":\"暴雪\",\"weather_icon\":\"17\"},{\"weatid\":\"19\",\"weather\":\"雾\",\"weather_icon\":\"18\"},{\"weatid\":\"20\",\"weather\":\"冻雨\",\"weather_icon\":\"19\"},{\"weatid\":\"21\",\"weather\":\"沙尘暴\",\"weather_icon\":\"20\"},{\"weatid\":\"22\",\"weather\":\"小雨-中雨\",\"weather_icon\":\"21\"},{\"weatid\":\"23\",\"weather\":\"中雨-大雨\",\"weather_icon\":\"22\"},{\"weatid\":\"24\",\"weather\":\"大雨-暴雨\",\"weather_icon\":\"23\"},{\"weatid\":\"25\",\"weather\":\"暴雨-大暴雨\",\"weather_icon\":\"24\"},{\"weatid\":\"26\",\"weather\":\"大暴雨-特大暴雨\",\"weather_icon\":\"25\"},{\"weatid\":\"27\",\"weather\":\"小雪-中雪\",\"weather_icon\":\"26\"},{\"weatid\":\"28\",\"weather\":\"中雪-大雪\",\"weather_icon\":\"27\"},{\"weatid\":\"29\",\"weather\":\"大雪-暴雪\",\"weather_icon\":\"28\"},{\"weatid\":\"30\",\"weather\":\"浮尘\",\"weather_icon\":\"29\"},{\"weatid\":\"31\",\"weather\":\"扬沙\",\"weather_icon\":\"30\"},{\"weatid\":\"32\",\"weather\":\"强沙尘暴\",\"weather_icon\":\"31\"},{\"weatid\":\"33\",\"weather\":\"霾\",\"weather_icon\":\"53\"}]}";


    public static List<WeatherListItem> Weather2WeatherListIte(List<Weather> weather){
        List<WeatherListItem> list = new ArrayList<>();
        WeatherListItem item;
        for(Weather w : weather){
            item = new WeatherListItem(w.getDays(),getWeatherDrawableRes(getWeatherIconId(w.getWeatid())),w.getTemp_high(),w.getTemp_low());
            list.add(item);
        }
        return list;
    }
}
