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
        return nowBean.getTmp();
    }
}
