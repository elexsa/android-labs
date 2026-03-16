package com.example.laba3

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class InputFragment : Fragment(R.layout.fragment_input) {

    private val viewModel: PizzaViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etDetails = view.findViewById<EditText>(R.id.etDetails)
        val btnOk = view.findViewById<Button>(R.id.btnOk)

        val cbMargarita = view.findViewById<CheckBox>(R.id.cbMargarita)
        val cbPepperoni = view.findViewById<CheckBox>(R.id.cbPepperoni)
        val cbCheese = view.findViewById<CheckBox>(R.id.cbCheese)

        val cbSmall = view.findViewById<CheckBox>(R.id.cbSmall)
        val cbMedium = view.findViewById<CheckBox>(R.id.cbMedium)
        val cbLarge = view.findViewById<CheckBox>(R.id.cbLarge)

        btnOk.setOnClickListener {
            val details = etDetails.text.toString()

            val isPizzaSelected = cbMargarita.isChecked || cbPepperoni.isChecked || cbCheese.isChecked
            val isSizeSelected = cbSmall.isChecked || cbMedium.isChecked || cbLarge.isChecked

            if (details.isEmpty() || !isPizzaSelected || !isSizeSelected) {
                Toast.makeText(context, "Будь ласка, завершіть введення всіх даних!", Toast.LENGTH_SHORT).show()
            } else {

                var orderSummary = "Замовник: $details\nОбрано піци: "
                if (cbMargarita.isChecked) orderSummary += "Маргарита "
                if (cbPepperoni.isChecked) orderSummary += "Пепероні "
                if (cbCheese.isChecked) orderSummary += "4 Сири "

                orderSummary += "\nРозміри: "
                if (cbSmall.isChecked) orderSummary += "25см "
                if (cbMedium.isChecked) orderSummary += "30см "
                if (cbLarge.isChecked) orderSummary += "40см "

                val dbHelper = DatabaseHelper(requireContext())
                val isInserted = dbHelper.insertOrder(orderSummary)

                if (isInserted) {
                    Toast.makeText(context, "Замовлення успішно збережено в БД!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Помилка збереження", Toast.LENGTH_SHORT).show()
                }

                viewModel.orderSummary.value = orderSummary

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ResultFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        val btnOpen = view.findViewById<Button>(R.id.btnOpen)
        btnOpen.setOnClickListener {
            val intent = android.content.Intent(requireActivity(), StorageActivity::class.java)
            startActivity(intent)
        }
    }
}