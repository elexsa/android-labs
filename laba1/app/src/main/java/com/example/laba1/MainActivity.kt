package com.example.laba1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etDetails = findViewById<EditText>(R.id.etDetails)
        val btnOrder = findViewById<Button>(R.id.btnOrder)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        // Чекбокси типів
        val cbMargarita = findViewById<CheckBox>(R.id.cbMargarita)
        val cbPepperoni = findViewById<CheckBox>(R.id.cbPepperoni)
        val cbCheese = findViewById<CheckBox>(R.id.cbCheese)

        // Чекбокси розмірів
        val cbSmall = findViewById<CheckBox>(R.id.cbSmall)
        val cbMedium = findViewById<CheckBox>(R.id.cbMedium)
        val cbLarge = findViewById<CheckBox>(R.id.cbLarge)

        btnOrder.setOnClickListener {
            val details = etDetails.text.toString()

            // Перевірка на вибір хоча б одного елемента
            val isPizzaSelected = cbMargarita.isChecked || cbPepperoni.isChecked || cbCheese.isChecked
            val isSizeSelected = cbSmall.isChecked || cbMedium.isChecked || cbLarge.isChecked

            if (details.isEmpty() || !isPizzaSelected || !isSizeSelected) {
                Toast.makeText(this, "Будь ласка, завершіть введення всіх даних!", Toast.LENGTH_SHORT).show()
            } else {
                var orderSummary = "Замовник: $details\nОбрано піци: "
                if (cbMargarita.isChecked) orderSummary += "Маргарита "
                if (cbPepperoni.isChecked) orderSummary += "Пепероні "
                if (cbCheese.isChecked) orderSummary += "4 Сири "

                orderSummary += "\nРозміри: "
                if (cbSmall.isChecked) orderSummary += "25см "
                if (cbMedium.isChecked) orderSummary += "30см "
                if (cbLarge.isChecked) orderSummary += "40см "

                tvResult.text = orderSummary
            }
        }
    }
}