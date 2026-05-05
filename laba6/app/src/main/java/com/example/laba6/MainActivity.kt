package com.example.laba6

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var tvQuote: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var btnFetchQuote: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvQuote = findViewById(R.id.tvQuote)
        tvAuthor = findViewById(R.id.tvAuthor)
        btnFetchQuote = findViewById(R.id.btnFetchQuote)

        btnFetchQuote.setOnClickListener {
            fetchQuote()
        }
    }

    private fun fetchQuote() {
        tvQuote.text = "Завантаження з мережі..."
        tvAuthor.text = ""
        btnFetchQuote.isEnabled = false

        thread {
            try {
                val url = URL("https://dummyjson.com/quotes/random")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }

                val jsonObject = JSONObject(response)
                val quoteText = jsonObject.getString("quote")
                val quoteAuthor = jsonObject.getString("author")

                runOnUiThread {
                    tvQuote.text = "\"$quoteText\""
                    tvAuthor.text = "- $quoteAuthor"
                    btnFetchQuote.isEnabled = true
                }
            } catch (e: Exception) {
                e.printStackTrace()

                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Помилка підключення", Toast.LENGTH_SHORT).show()
                    tvQuote.text = "Не вдалося завантажити дані. Перевірте підключення."
                    btnFetchQuote.isEnabled = true
                }
            }
        }
    }
}