package com.example.administrator.weather;
import com.google.gson.Gson;
import java.util.List;
/**
 * Created by Administrator on 2018/3/14/014.
 */

public class JsonUtil {
    public static String getObject(String jsonString){
        Gson gson=new Gson();
        HeWeather6 weather6=gson.fromJson(jsonString,HeWeather6.class);;
        List<HeWeather6Bean> list=weather6.getHeWeather6();
        NowBean nowBean=list.get(0).getNow();
        WeatherInfo.nowState=nowBean.getCond_code();
        BasicBean basicBean=list.get(0).getBasic();
        WeatherInfo.location=basicBean.getLocation();
        return nowBean.getTmp();
    }
    public static String getForecastObject(String jsonString){
        Gson gson=new Gson();
        HeWeather6Forecast weather6Forecast=gson.fromJson(jsonString,HeWeather6Forecast.class);
        List<HeWeather6Forecast.HeWeather6Bean> list=weather6Forecast.getHeWeather6();
        List<HeWeather6Forecast.HeWeather6Bean.DailyForecastBean> dailyForecastBeans=list.get(0).getDaily_forecast();
        WeatherInfo.secondState=dailyForecastBeans.get(1).getCond_code_d();
        WeatherInfo.thirdState=dailyForecastBeans.get(2).getCond_code_d();
        WeatherInfo.forthState=dailyForecastBeans.get(2).getCond_code_d();
        return null;
    }
}
