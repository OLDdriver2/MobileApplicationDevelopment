package com.example.administrator.weather;


import java.util.Calendar;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;



public class MainActivity extends AppCompatActivity {


    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLocationClient = new LocationClient(getBaseContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){

            Double latitude = location.getLatitude();    //获取纬度信息
            Double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            int errorCode = location.getLocType();
            WeatherInfo.GPSlocation=latitude.toString()+","+longitude.toString();
        }
    }

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
    public void btnClick(View view){

        new DownloadUpdate().execute("auto_ip");
        setDate();
        mLocationClient.start();
        new DownloadForecast().execute("auto_ip");
        //Toast.makeText(getBaseContext(),WeatherInfo.GPSlocation,Toast.LENGTH_LONG).show();


    }
    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = "https://free-api.heweather.com/s6/weather/now?location="+strings[0]+"&key=2a52d94fb1744d2a8c1dd37ab638c4fa";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return "10";
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return "11";
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "12";
        }

        @Override
        protected void onPostExecute(String temperature) {
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(JsonUtil.getObject(temperature));
            String nowstate="w"+WeatherInfo.nowState;
            int drawableid=getResources().getIdentifier(nowstate,"drawable",getApplicationContext().getPackageName());
            ((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(drawableid);
            ((ImageView) findViewById(R.id.first_image)).setImageResource(drawableid);
            ((TextView) findViewById(R.id.tv_location)).setText(WeatherInfo.location);
        }

    }
    private class DownloadForecast extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = "https://free-api.heweather.com/s6/weather/forecast?location="+strings[0]+"&key=2a52d94fb1744d2a8c1dd37ab638c4fa";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return "10";
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return "11";
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "12";
        }

        @Override
        protected void onPostExecute(String forecast) {
            //Toast.makeText(getBaseContext(),forecast,Toast.LENGTH_LONG).show();
            JsonUtil.getForecastObject(forecast);
            String second_state="w"+WeatherInfo.secondState;
            int drawableid=getResources().getIdentifier(second_state,"drawable",getApplicationContext().getPackageName());
            ((ImageView) findViewById(R.id.second_image)).setImageResource(drawableid);
            String third_state="w"+WeatherInfo.thirdState;
            int drawableid1=getResources().getIdentifier(third_state,"drawable",getApplicationContext().getPackageName());
            ((ImageView) findViewById(R.id.third_image)).setImageResource(drawableid1);
            String forth_state="w"+WeatherInfo.forthState;
            int drawableid2=getResources().getIdentifier(forth_state,"drawable",getApplicationContext().getPackageName());
            ((ImageView) findViewById(R.id.fouth_image)).setImageResource(drawableid2);
        }

    }
}
