package com.example.weatherapiapp;

import androidx.annotation.NonNull;

public class WeatherReportModel {

    private int id;
    private String weather_state_name;

    public WeatherReportModel(int id, String weather_state_name) {
        this.id = id;
        this.weather_state_name = weather_state_name;
    }

    public WeatherReportModel() {

    }

    @Override
    public String toString() {
        return "WeatherReportModel{" +
                "id=" + id +
                ", weather_state_name='" + weather_state_name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }
}
