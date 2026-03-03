package com.example.laba2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ResultFragment : Fragment(R.layout.fragment_result) {

    private val viewModel: PizzaViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val tvResult = view.findViewById<TextView>(R.id.tvResult)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        viewModel.orderSummary.observe(viewLifecycleOwner) { summary ->
            tvResult.text = summary
        }

        btnCancel.setOnClickListener {

            viewModel.orderSummary.value = ""

            parentFragmentManager.popBackStack()
        }
    }
}