package com.weather.hyeongjukim.weathernotify;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tem)
    TextView tem;
    @Bind(R.id.getWeatherBtn)
    Button getWeatherBtn;


    @OnClick(R.id.getWeatherBtn)
    public void setGetWeatherBtn(View view){

        Retrofit client = new Retrofit.Builder().baseUrl("http://apis.skplanetx.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        final WeatherRepo.WeatherApiInterface weatherApi =
                client.create(WeatherRepo.WeatherApiInterface.class);
        String lat = String.valueOf(37.4870600000);
        String lon = String.valueOf(127.0460400000);
        Call<WeatherRepo> call = weatherApi.get_Weather_retrofit(1, lat, lon);
        call.enqueue(new Callback<WeatherRepo>() {
            @Override
            public void onResponse(Call<WeatherRepo> call, Response<WeatherRepo> response) {
                WeatherRepo weatherRepo = response.body();
                if (weatherRepo.getResult().getCode().equals("9200")) {
                    tem.setText(weatherRepo.getWeather().getHourly().get(0).getTemperature().getTc());
                } else {
                    Log.d("Error", "server return error code :" + weatherRepo.getResult().getCode());
                }
            }

            @Override
            public void onFailure(Call<WeatherRepo> call, Throwable t) {
                Log.d("Error", "onFailure" + t);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

}
