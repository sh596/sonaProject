package com.example.sona_project.Main;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.sona_project.R;
import com.example.sona_project.Retrofit.WeatherClient;
import com.example.sona_project.Retrofit.WeatherData;

import static android.content.ContentValues.TAG;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private WeatherData weatherData;
    private TextView tempMain;
    private TextView tempMax;
    private TextView tempMin;
    private TextView humidity;
    private ImageView weatherIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempMain = findViewById(R.id.tempMain);
        tempMax = findViewById(R.id.tempMax);
        tempMin = findViewById(R.id.tempMin);
        humidity = findViewById(R.id.humidity);

        weatherIcon = findViewById(R.id.weatherIcon);

        getWheather();
    }

    private void getWheather(){
        Call<WeatherData> call = WeatherClient.getApiService().getWheather("Seoul");
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                weatherData = response.body();

                tempMain.setText(String.format("%.0f",weatherData.getMain().getTemp())+"°");
                tempMax.setText(String.format("%.0f",weatherData.getMain().getTemp_max())+"°");
                tempMin.setText(String.format("%.0f",weatherData.getMain().getTemp_min())+"°");
                humidity.setText("습도 "+weatherData.getMain().getHumidity()+" %");

                setIcon(weatherData.getWeather().get(0).getIcon());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.d(TAG, "onResponse: 실패"+t.getMessage());
            }
        });
    }

    private void setIcon(String icon){
        if (icon.equals("01d") || icon.equals("01n")){
            Toast.makeText(this, "맑음", Toast.LENGTH_SHORT).show();
        }else if(icon.equals("02d") || icon.equals("02n")){
            weatherIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.few_cloud_icon));
        }else if(icon.equals("03d")|| icon.equals("03n") || icon.equals("04d") || icon.equals("04n")){
            weatherIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.cloud_icon));
        }else {
            weatherIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rain_icon));
        }
    }
}
