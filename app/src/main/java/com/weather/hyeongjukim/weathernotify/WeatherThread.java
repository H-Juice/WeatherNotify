package com.weather.hyeongjukim.weathernotify;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hyeongjukim on 2017. 8. 29..
 */
public class WeatherThread extends Thread {
    final static String TAG = "WeatherThread";
    Context mContext;
    WeatherRepo weatherRepo;
    Handler handler;

    int version = 1;
    String lat;
    String lon;

    public WeatherThread(Handler handler, Context mContext, double lat, double lon) {
        this.mContext = mContext;
        this.lat = String. valueOf(lat);
        this.lon = String .valueOf(lon);
        this.handler = handler;
    }

    @Override
    public void run() {
        super.run();
        Retrofit client = new Retrofit.Builder().baseUrl("http://apis.skplanetx.com/").addConverterFactory(GsonConverterFactory.create()).build();
        WeatherRepo.WeatherApiInterface service = client.create(WeatherRepo.WeatherApiInterface.class);
        Call<WeatherRepo> call = service.get_Weather_retrofit(version, lat, lon);
        call.enqueue(new Callback<WeatherRepo>() {
            @Override
            public void onResponse(Call<WeatherRepo> call, Response<WeatherRepo> response) {
                if(response.isSuccessful()){
                    weatherRepo = response.body();
                    Log.d(TAG,"response.raw :"+response.raw());
                    if(weatherRepo.getResult().getCode().equals("9200")){ // 9200 = 성공
                        Weather.getInstance().setTemperature(weatherRepo.getWeather().getHourly().get(0).getTemperature().getTc());
                        Weather.getInstance().setCloud(weatherRepo.getWeather().getHourly().get(0).getSky().getName());
                        Weather.getInstance().setWind_direction(weatherRepo.getWeather().getHourly().get(0).getWind().getWdir());
                        Weather.getInstance().setWind_speed(weatherRepo.getWeather().getHourly().get(0).getWind().getWspd());
                        Weather.getInstance().setIcon(weatherRepo.getWeather().getHourly().get(0).getSky().getCode());

                        Message msg = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putString("weather","weather");
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }else{
                        Log.e(TAG,"요청 실패 :"+weatherRepo.getResult().getCode());
                        Log.e(TAG,"메시지 :"+weatherRepo.getResult().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherRepo> call, Throwable t) {
                Log.e(TAG,"날씨정보 불러오기 실패 :" + t.getMessage() );
                Log.e(TAG,"요청 메시지 :"+call.request());
            }
        });
    }
}
