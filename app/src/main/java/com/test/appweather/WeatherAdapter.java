package com.test.appweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {


    private final List<ItemData> list;
    public final Context context;
    //private MainView view;


    public WeatherAdapter(Context context_) {
        this.context = context_;
        list = new ArrayList<>();
    }

    public void swap(List<ItemData> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new WeatherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_weather_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherViewHolder weatherViewHolder, int position) {



        String week = list.get(position).getWeek();
        String mintemp = list.get(position).getMintemp();
        String maxtemp = list.get(position).getMaxtemp();
        String description = list.get(position).getDescription();


        weatherViewHolder.tvWeek.setText(week);
        weatherViewHolder.tvMinTemp.setText("Min: " + mintemp + "°");
        weatherViewHolder.tvMaxTemp.setText("Max: " +  maxtemp + "°");
        weatherViewHolder.tvDescription.setText(description);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tvWeek;
        TextView tvMinTemp;
        TextView tvMaxTemp;
        TextView tvDescription;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            tvWeek = (TextView)itemView.findViewById(R.id.tv_week);
            tvMinTemp = (TextView)itemView.findViewById(R.id.tv_min_temp);
            tvMaxTemp = (TextView)itemView.findViewById(R.id.tv_max_temp);
            tvDescription = (TextView)itemView.findViewById(R.id.tv_description);
        }
    }
}
