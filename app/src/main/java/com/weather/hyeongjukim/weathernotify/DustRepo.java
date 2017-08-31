package com.weather.hyeongjukim.weathernotify;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class DustRepo {

    @SerializedName("result")
    Result result;
    @SerializedName("weather")
    weather weather;

    public class Result {
        @SerializedName("message") String message;
        @SerializedName("code") String code;

        public String getMessage() {return message;}
        public String getCode() {return code;}
    }

    public class weather {

        public List<Dust> dust = new ArrayList<>();
        public List<Dust> getDust() {return dust;}

        public class Dust {
            @SerializedName("pm10") Pm10 pm10;


            public class Pm10{
                @SerializedName("grade") String grade;
                @SerializedName("value") String value;

                public String getGrade() {return grade;}
                public String getValue() {return value;}
            }

            public Pm10 getPm10() {return pm10;}

        }
    }
    public Result getResult() {return result;}
    public weather getWeather() {return weather;}

    public interface DustApiInterface {
        @Headers({"Accept: application/json", "appKey:bbb8aa59-2ba0-3fdd-bfb1-07b7f3118ec5"})
        @GET("weather/dust/")
        Call<DustRepo> get_Dust_retrofit(@Query("version") int version, @Query("lat") String lat, @Query("lon") String lon);
    }
}
