package com.example.weatherapiapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    private static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    private static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    String cityID = "";

    Context context;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(String cityID);
    }

    public void getCityID(String cityName, VolleyResponseListener volleyResponseListener) {
        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(context, cityID, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something wrong");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

        //return cityID;

    }

    public interface ForeCastByIDResponse {
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getCityForecastByID(String cityID, ForeCastByIDResponse foreCastByIDResponse) {

        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        // get the json object
        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray cwl = response.getJSONArray("consolidated_weather");
                    WeatherReportModel one_day_weather = new WeatherReportModel();

                    for (int i=0; i < cwl.length(); i++) {

                        JSONObject first_day_from_api = (JSONObject) cwl.get(i);
                        one_day_weather.setId(first_day_from_api.getInt("id"));
                        one_day_weather.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                        weatherReportModels.add(one_day_weather);
                    }

                    foreCastByIDResponse.onResponse(weatherReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
                // get the propery called c w

                // get each item and assign
    }
//
//    public List<WeatherReportModel> getCityForecastByName(String cityName) {
//
//
//    }

}
