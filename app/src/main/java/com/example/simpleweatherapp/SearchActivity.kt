package com.example.simpleweatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val cityInput: EditText = findViewById(R.id.cityInput)
        val searchButton: Button = findViewById(R.id.searchButton)

        searchButton.setOnClickListener {
            val cityName = cityInput.text.toString()
            val intent = Intent(this, DisplayWeatherActivity::class.java)
            intent.putExtra("CITY_NAME", cityName)
            startActivity(intent)
        }
    }
}

