package com.example.simpleweatherapp

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.util.Locale

class DisplayWeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_weather)

        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)
        linearLayout.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

        val tvCityName: TextView = findViewById(R.id.tvCityName)
        val ivFlag: ImageView = findViewById(R.id.ivFlag)
        val ivWeatherIcon: ImageView = findViewById(R.id.ivWeatherIcon)
        val tvWeatherDescription: TextView = findViewById(R.id.tvWeatherDescription)
        val tvTemperature: TextView = findViewById(R.id.tvTemperature)
        val tvHumidity: TextView = findViewById(R.id.tvHumidity)
        val tvWindSpeed: TextView = findViewById(R.id.tvWindSpeed)

        val cityName = intent.getStringExtra("CITY_NAME") ?: ""
        getWeather(cityName, tvCityName, ivFlag, ivWeatherIcon, tvWeatherDescription, tvTemperature, tvHumidity, tvWindSpeed)
    }

    private fun getWeather(cityName: String, tvCityName: TextView, ivFlag: ImageView, ivWeatherIcon: ImageView, tvWeatherDescription: TextView, tvTemperature: TextView, tvHumidity: TextView, tvWindSpeed: TextView) {
        val apiKey = "69e59d9f1a67660644f26bca3c2e8da2"
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$apiKey&units=metric"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val main: JSONObject = response.getJSONObject("main")
                val weatherArray = response.getJSONArray("weather")
                val weatherObject = weatherArray.getJSONObject(0)
                val windObject = response.getJSONObject("wind")
                val sysObject = response.getJSONObject("sys")
                val country = sysObject.getString("country")

                val weather = main.getString("temp")
                val humidity = main.getString("humidity")
                val weatherDescription = weatherObject.getString("description")
                val weatherIconCode = weatherObject.getString("icon")
                val windSpeed = windObject.getString("speed")

                tvCityName.text = cityName
                tvWeatherDescription.text = weatherDescription.capitalize(Locale.ROOT)
                tvTemperature.text = "Temperature: $weather°C"
                tvHumidity.text = "Humidity: $humidity%"
                tvWindSpeed.text = "Wind Speed: $windSpeed m/s"

                val flagUrl = "https://flagcdn.com/w80/${country.lowercase(Locale.ROOT)}.png"
                val weatherIconUrl = "https://openweathermap.org/img/wn/$weatherIconCode@2x.png"

                Log.d("DisplayWeatherActivity", "Flag URL: $flagUrl")
                Log.d("DisplayWeatherActivity", "Weather Icon URL: $weatherIconUrl")

                Glide.with(this).load(flagUrl).into(ivFlag)
                Glide.with(this).load(weatherIconUrl).into(ivWeatherIcon)
            },
            { error ->
                tvCityName.text = "Error: $error"
                Log.e("WeatherApp", "Error: $error")
            }
        )

        val queue = Volley.newRequestQueue(this)
        queue.add(jsonObjectRequest)
    }
}

