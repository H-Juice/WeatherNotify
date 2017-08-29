package com.weather.hyeongjukim.weathernotify;

/**
 * Created by hyeongjukim on 2017. 8. 29..
 */

public class Weather {
    private String temperature;
    private String cloud;
    private String wind_direction;
    private String wind_speed;
    private String icon;

    public static Weather getInstance() {
        Weather weather = new Weather();
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

