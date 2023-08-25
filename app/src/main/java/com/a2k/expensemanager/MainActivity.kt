package com.a2k.expensemanager

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.a2k.expensemanager.com.a2k.expensemanager.adapter.ExpenseAdapter
import com.a2k.expensemanager.databinding.ActivityMainBinding
import com.a2k.expensemanager.model.Expense
import com.a2k.expensemanager.viewmodel.ExpenseViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random


class MainActivity : AppCompatActivity() {


    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ExpenseAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (auth.currentUser == null) {
            auth.signInAnonymously()
        }

        binding.addExpense.setOnClickListener {
            expenseViewModel.addExpense(
                Expense(
                    "Test",
                    Random.nextDouble(10.0, 1000.0),
                    Timestamp(
                        System.currentTimeMillis() / 1000,
                        0
                    ),
                    "Test",
                    "Test"
                )
            )
        }

        adapter = ExpenseAdapter(
            onExpenseClick = {
                Log.d(TAG, "onCreate: $it")
            },
            onExpenseLongPress = {
                expenseViewModel.deleteExpense(it)
                adapter.notifyDataSetChanged()
            }
        )
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.expenseRecyclerView.layoutManager = layoutManager
        binding.expenseRecyclerView.adapter = adapter

        expenseViewModel.getExpenses()
        expenseViewModel.expenses.observe(this) {
            when (it) {
                is ExpenseViewModel.ExpenseEvent.Success -> {
                    adapter.setExpense(it.result)

                    binding.totalExpense.text = "Total Expense: ${calculateTotalExpense(it.result)}"

                    Log.d(TAG, "Success: ${it.result}")
                }

                is ExpenseViewModel.ExpenseEvent.Failure -> {
                    Log.d(TAG, "Failure: ${it.error}")
                }

                is ExpenseViewModel.ExpenseEvent.Loading -> {
                    Log.d(TAG, "Loading")
                }

                is ExpenseViewModel.ExpenseEvent.Empty -> {
                    Log.d(TAG, "Empty")
                }
            }
        }
    }

    private fun calculateTotalExpense(expenses: List<Expense>): Double {
        var totalExpense = 0.0
        expenses.forEach {
            totalExpense += it.amount!!
        }
        return totalExpense
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}