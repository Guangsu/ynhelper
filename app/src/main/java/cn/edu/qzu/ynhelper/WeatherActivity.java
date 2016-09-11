package cn.edu.qzu.ynhelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.qzu.ynhelper.adapter.WeatherAdapter;
import cn.edu.qzu.ynhelper.entity.Weather;
import cn.edu.qzu.ynhelper.entity.WeatherListItem;
import cn.edu.qzu.ynhelper.util.WeatherUtil1;


public class WeatherActivity extends Activity {

    private TextView tvCity;
    private TextView tvWeatherDesc;
    private TextView tvWeatherTemp;
    private RecyclerView rvFutureWeather;

    private List<WeatherListItem> data;
    private WeatherAdapter mAdapter;
    private Weather todayWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initView();
        initData();
        requestWeatherData();
    }

    private void initView(){
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvWeatherDesc = (TextView) findViewById(R.id.tv_weather_desc);
        tvWeatherTemp = (TextView) findViewById(R.id.tv_weather_temp);
        rvFutureWeather = (RecyclerView) findViewById(R.id.recylerView_future);
        rvFutureWeather.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvFutureWeather.setLayoutManager(layoutManager);
    }

    private void initData(){
        data = new ArrayList<>();
        mAdapter = new WeatherAdapter(data);
        rvFutureWeather.setAdapter(mAdapter);
    }

    private void requestWeatherData(){
        WeatherUtil1.requestWeather(this,new WeatherUtil1.IWeather(){

            @Override
            public void onSuccess(List<Weather> weather) {
                todayWeather = weather.get(0);
                setTodayWeather(todayWeather);
                data.clear();
                data.addAll(WeatherUtil1.Weather2WeatherListIte(weather));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(WeatherActivity.this, "msg", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTodayWeather(Weather weather){
        tvCity.setText(weather.getCitynm());
        tvWeatherDesc.setText(weather.getWeather());
        tvWeatherTemp.setText(weather.getTemperature().replaceAll("℃",""));
    }

}
