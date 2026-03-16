package com.example.laba3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        val tvStorageData = findViewById<TextView>(R.id.tvStorageData)
        val dbHelper = DatabaseHelper(this)

        val data = dbHelper.getAllOrders()

        // Перевіряємо, чи пуста база
        if (data.isEmpty()) {
            tvStorageData.text = "Сховище пусте. Зробіть перше замовлення!"
        } else {
            tvStorageData.text = "Збережені замовлення:\n\n$data"
        }
    }
}