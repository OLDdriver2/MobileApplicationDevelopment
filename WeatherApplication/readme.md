# Weather Application
## ICON
![image](https://github.com/OLDdriver2/MobileApplicationDevelopment/blob/master/WeatherApplication/app/src/main/res/drawable/icon.png?raw=true)
> I draw this icon by myself
> 
![image](https://github.com/OLDdriver2/MobileApplicationDevelopment/blob/master/images/A1-01.PNG?raw=true)
>
> 
```
<application
        android:allowBackup="true"
        android:icon="@drawable/icon"
        android:label="@string/app_name"
        android:roundIcon="@drawable/icon"
        android:supportsRtl="true"
        android:theme="@style/Theme.AppCompat.NoActionBar">
```
>
## Blue circle and refresh button
>
![image](https://github.com/OLDdriver2/MobileApplicationDevelopment/blob/master/images/A1-02.PNG?raw=true)
>

```
<ImageButton
            android:background="@drawable/refresh"
            android:scaleType="fitXY"
            android:id="@+id/imageButton"
            android:layout_width="30dp"
            android:layout_height="30dp"
            android:layout_centerVertical="true"
            android:layout_toEndOf="@+id/linearLayout"
            android:layout_toRightOf="@+id/linearLayout"
            android:onClick="btnClick"
             />
```
> 

```
<TextView
            android:id="@+id/first"
            android:layout_width="35dp"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:text="mon"
            android:textAllCaps="true"
            android:textColor="#000"
            android:background="@drawable/text_shape"
            />
```
> 

```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle" >

    <solid
        android:color="#2495d1" >
    </solid>

    <corners
        android:radius="180dp"
         >
    </corners>

</shape>
```
> 
> Refresh button is Imagebutton.And the blue circle is just the backgroud of Textview which is bule.
## Refresh butoon pressed
> ![image](https://github.com/OLDdriver2/MobileApplicationDevelopment/blob/master/images/A1-03.PNG?raw=true)
> 
> It is based on the real data from heweather(https://www.heweather.com/).The API address is https://free-api.heweather.com/s6/weather/forecast?params .The project contains baidumap service,but i haven't done that part.For now,this application uses IP to get your location and weather data.
> 
> It will reutrn a json string such as

```
{
    "HeWeather6": [
        {
            "basic": {
                "cid": "CN101010100",
                "location": "北京",
                "parent_city": "北京",
                "admin_area": "北京",
                "cnty": "中国",
                "lat": "39.90498734",
                "lon": "116.40528870",
                "tz": "8.0"
            },
            "daily_forecast": [
                {
                    "cond_code_d": "103",
                    "cond_code_n": "101",
                    "cond_txt_d": "晴间多云",
                    "cond_txt_n": "多云",
                    "date": "2017-10-26",
                    "hum": "57",
                    "pcpn": "0.0",
                    "pop": "0",
                    "pres": "1020",
                    "tmp_max": "16",
                    "tmp_min": "8",
                    "uv_index": "3",
                    "vis": "16",
                    "wind_deg": "0",
                    "wind_dir": "无持续风向",
                    "wind_sc": "微风",
                    "wind_spd": "5"
                },
                {
                    "cond_code_d": "101",
                    "cond_code_n": "501",
                    "cond_txt_d": "多云",
                    "cond_txt_n": "雾",
                    "date": "2017-10-27",
                    "hum": "56",
                    "pcpn": "0.0",
                    "pop": "0",
                    "pres": "1018",
                    "tmp_max": "18",
                    "tmp_min": "9",
                    "uv_index": "3",
                    "vis": "20",
                    "wind_deg": "187",
                    "wind_dir": "南风",
                    "wind_sc": "微风",
                    "wind_spd": "6"
                },
                {
                    "cond_code_d": "101",
                    "cond_code_n": "101",
                    "cond_txt_d": "多云",
                    "cond_txt_n": "多云",
                    "date": "2017-10-28",
                    "hum": "26",
                    "pcpn": "0.0",
                    "pop": "0",
                    "pres": "1029",
                    "tmp_max": "17",
                    "tmp_min": "5",
                    "uv_index": "2",
                    "vis": "20",
                    "wind_deg": "2",
                    "wind_dir": "北风",
                    "wind_sc": "3-4",
                    "wind_spd": "19"
                }
            ],
            "status": "ok",
            "update": {
                "loc": "2017-10-26 23:09",
                "utc": "2017-10-26 15:09"
            }
        }
    ]
}
```
> So i used GSON to analyse it and get what i need."cond_code_d" is the condition of the day.
> Date will also be updated.I used import java.util.Calendar to help get the date.

```
 private void setDate(){
        final String dayNames[] = { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY","SATURDAY" };
        Calendar calendar=Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1;
        ((TextView) findViewById(R.id.dayOfWeek)).setText(dayNames[dayOfWeek]);
        Integer year=calendar.get(Calendar.YEAR);
        Integer month=calendar.get(Calendar.MONTH)+1;
        Integer day=calendar.get(Calendar.DATE);
        String today=day.toString()+"/"+month.toString()+"/"+year.toString();
        ((TextView) findViewById(R.id.tv_date)).setText(today);

        final String dayNamesShort[] = { "SUN", "MON", "TUES", "WED", "THUR", "FRI","SAT" };
        ((TextView) findViewById(R.id.first)).setText(dayNamesShort[(dayOfWeek++)%7]);
        ((TextView) findViewById(R.id.second)).setText(dayNamesShort[(dayOfWeek++)%7]);
        ((TextView) findViewById(R.id.third)).setText(dayNamesShort[(dayOfWeek++)%7]);
        ((TextView) findViewById(R.id.fouth)).setText(dayNamesShort[(dayOfWeek++)%7]);
    }
```
