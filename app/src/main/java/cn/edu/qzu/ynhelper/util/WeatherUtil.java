package cn.edu.qzu.ynhelper.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.zhy.http.okhttp.callback.StringCallback;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * Created by Jaren on 2016/4/1.
 */
public class WeatherUtil {

    private static TencentLocationRequest locationRequest;
    private static TencentLocationManager locationManager;

    public  static void requestWeather(Context context){
        startLoc(context,new Weather());

    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean startLoc(Context context,TencentLocationListener listener){
        locationRequest = getDefaultLocationRequest();
        locationManager = TencentLocationManager.getInstance(context);
        int error = locationManager.requestLocationUpdates(locationRequest,listener);

        if(error == 0) return true;
        Log.e("TencentLocation","位置监听器注册失败，错误码："+error);
        return false;
    }

    /**
     * 请求定位的主要方法，默认回调方法为在定位完成之后用EventBus发送一个CityEvent实例
     * 定位完成后自动移除位置监听器
     * @param context
     * @return 监听器是否注册成功
     */
    public static boolean startLoc(final Context context,final IWeather iWeather){
        return startLoc(context, new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
                if(TencentLocation.ERROR_OK == error){
                    // 定位成功
                    iWeather.onSuccess(tencentLocation.getCityCode());
                    Toast.makeText(context,tencentLocation.getCity(),Toast.LENGTH_LONG).show();
                } else {
                    // 定位失败
                    iWeather.onError(reason);
                }
                // 移除位置监听器
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {

            }
        });
    }

    public static TencentLocationRequest getDefaultLocationRequest(){
        if(locationRequest == null){
            // 请求的位置信息包括经纬度，位置所处的中国大陆行政区划（REQUEST_LEVEL_ADMIN_AREA）
            return TencentLocationRequest.create().setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
        }
        return locationRequest;
    }

    interface IWeather{
        // 纬度，经度
        void onSuccess(String cityCode );
        void onError(String msg);
    }

    static class Weather implements IWeather{

        @Override
        public void onSuccess(String cityCode) {
            //OkHttpUtils.get().url("").addParams("","").build().execute(new WeatherCallback());
        }

        @Override
        public void onError(String msg) {

        }
    }

    class WeatherCallback extends StringCallback{

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(String response) {

        }
    }

}
