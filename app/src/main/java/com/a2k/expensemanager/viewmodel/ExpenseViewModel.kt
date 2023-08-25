package com.a2k.expensemanager.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a2k.expensemanager.model.Expense
import com.a2k.expensemanager.repo.ExpenseRepo

class ExpenseViewModel : ViewModel() {

    sealed class ExpenseEvent {
            class Success(val result: List<Expense>) : ExpenseEvent()
        class Failure(val error: String) : ExpenseEvent()
        object Loading : ExpenseEvent()
        object Empty : ExpenseEvent()
    }

    private val expenseRepo = ExpenseRepo.instance
    private val _expenses = MutableLiveData<ExpenseEvent>()
    val expenses get() = _expenses

    fun addExpense(expense: Expense) {
        expenseRepo.addExpense(expense)
    }

    fun updateExpense(expense: Expense) {
        expenseRepo.updateExpense(expense)
    }

    fun deleteExpense(expense: Expense) {
        expenseRepo.deleteExpense(expense)
    }

    fun getExpenses() {
        expenseRepo.getExpenses().addSnapshotListener {
            value, error ->
            error?.let {
                _expenses.value = ExpenseEvent.Failure(it.message!!)
                return@addSnapshotListener
            }

            value?.let {
                val expenses = it.toObjects(Expense::class.java)
                if (expenses.isEmpty()) {
                    _expenses.value = ExpenseEvent.Empty
                } else {
                    _expenses.value = ExpenseEvent.Success(expenses)
                }
            }
        }
    }


}