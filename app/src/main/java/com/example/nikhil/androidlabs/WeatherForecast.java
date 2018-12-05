package com.example.nikhil.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    private static final String ACTIVITY_NAME = "WeatherForecast";

    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private ImageView imageCurrent;
    private ProgressBar weatherBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

//        weatherBar.setVisibility(View.VISIBLE);

        currentTemp = findViewById(R.id.text_current);
        minTemp = findViewById(R.id.text_min);
        maxTemp = findViewById(R.id.text_max);
        imageCurrent = findViewById(R.id.image_current);
        weatherBar = findViewById(R.id.weatherBar);

        ForecastQuery fq = new ForecastQuery();
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
        fq.execute(urlString);
    }


    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }


    protected static Bitmap getImage(URL url) {
        Log.i(ACTIVITY_NAME, "In getImage");
        HttpURLConnection iconConn = null;
        try {
            iconConn = (HttpURLConnection) url.openConnection();
            iconConn.connect();
            int response = iconConn.getResponseCode();
            if (response == 200) {
                return BitmapFactory.decodeStream(iconConn.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (iconConn != null) {
                iconConn.disconnect();
            }
        }
    }


    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        public String min;
        public String max;
        public String value;
        public String iconName;
        public Bitmap tempImg;


        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                //==================================================================================

                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                //  parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(stream, null);
                // parser.nextTag();

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    if (parser.getName().equals("temperature")) {
                        value = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        min = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        max = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    if (parser.getName().equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName + ".png";
                        if (fileExistance(iconFile)) {
                            FileInputStream inputStream = null;
                            try {
                                inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            tempImg = BitmapFactory.decodeStream(inputStream);
                        } else {
                            URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                            tempImg = getImage(iconUrl);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            tempImg.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }

                        publishProgress(100);
                    }
                }

            } catch (Exception ex){}

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            weatherBar.setVisibility(View.VISIBLE);
            weatherBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String degree = Character.toString((char) 0x00B0);
            currentTemp.setText("Current Temprature "+ value + degree + "C");
            minTemp.setText("Minimim Temprature "+ min + degree + "C");
            maxTemp.setText("Maximum Temprature "+ max + degree + "C");
            imageCurrent.setImageBitmap(tempImg);
            weatherBar.setVisibility(View.INVISIBLE);
        }
    }
}
