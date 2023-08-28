package com.a2k.expensemanager

import android.icu.text.DateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.a2k.expensemanager.databinding.ActivityEditBinding
import com.a2k.expensemanager.model.Expense
import com.a2k.expensemanager.viewmodel.ExpenseViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import com.google.type.DateTime

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private val expenseViewModel: ExpenseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.expenseDate.keyListener = null

        val isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            val expenseTitle = intent.getStringExtra("expense_title")
            val expenseAmount = intent.getDoubleExtra("expense_amount", 0.0)
            var expenseDate = intent.getLongExtra("expense_date", 0) * 1000
            val expenseCategory = intent.getStringExtra("expense_category")
            val expenseNote = intent.getStringExtra("expense_note")
            val expenseId = intent.getStringExtra("expense_id")

            binding.expenseTitle.setText(expenseTitle)
            binding.expenseAmount.setText(expenseAmount.toString())
            binding.expenseDate.setText(DateFormat.getDateInstance().format(expenseDate))
            binding.expenseCategory.setText(expenseCategory)
            binding.expenseNote.setText(expenseNote)

            binding.expenseDate.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker().build()
                datePicker.show(supportFragmentManager, "datePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.expenseDate.setText(DateFormat.getDateInstance().format(it))
                    expenseDate = it
                }
            }

            binding.saveExpense.setOnClickListener {
                val expense = Expense(
                    id = expenseId!!,
                    title = binding.expenseTitle.text.toString().ifEmpty { "" },
                    amount = binding.expenseAmount.text.toString().toDouble(),
                    date = Timestamp(expenseDate / 1000, 0),
                    category = binding.expenseCategory.text.toString(),
                    note = binding.expenseNote.text.toString()
                )
                expenseViewModel.updateExpense(expense)
                finish()
            }

        } else {

            var expenseDate = System.currentTimeMillis()
            binding.expenseDate.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker().build()
                datePicker.show(supportFragmentManager, "datePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.expenseDate.setText(DateFormat.getDateInstance().format(it))
                    expenseDate = it
                }
            }

            binding.saveExpense.setOnClickListener {
                val expense = Expense(
                    title = binding.expenseTitle.text.toString().ifEmpty { "" },
                    amount = binding.expenseAmount.text.toString().toDouble(),
                    date = Timestamp(expenseDate / 1000, 0),
                    category = binding.expenseCategory.text.toString(),
                    note = binding.expenseNote.text.toString()
                )
                expenseViewModel.addExpense(expense)
                finish()
            }

        }

    }
}