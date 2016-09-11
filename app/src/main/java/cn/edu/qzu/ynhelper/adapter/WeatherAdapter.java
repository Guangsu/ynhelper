package cn.edu.qzu.ynhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.entity.WeatherListItem;

/**
 * Created by Jaren on 2016/9/2.
 */
public class WeatherAdapter extends RecyclerView.Adapter {

    private List<WeatherListItem> data;

    public WeatherAdapter(List<WeatherListItem> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather,null);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WeatherViewHolder viewHolder = (WeatherViewHolder) holder;
        WeatherListItem weather = data.get(position);
        viewHolder.tvDay.setText(weather.getDay());
        viewHolder.ivWeather.setImageResource(weather.getImg());
        viewHolder.tvTempLow.setText(weather.getTempLow());
        viewHolder.tvTempHigh.setText(weather.getTempHigh());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{

        private TextView tvDay;
        private ImageView ivWeather;
        private TextView tvTempHigh;
        private TextView tvTempLow;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            tvDay = (TextView) itemView.findViewById(R.id.tv_day);
            ivWeather = (ImageView) itemView.findViewById(R.id.iv_weather);
            tvTempHigh = (TextView) itemView.findViewById(R.id.tv_temp_high);
            tvTempLow = (TextView) itemView.findViewById(R.id.tv_temp_low);
        }
    }
}
