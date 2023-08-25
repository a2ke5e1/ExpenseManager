package com.a2k.expensemanager.repo

import com.a2k.expensemanager.model.Expense
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User

class ExpenseRepo {

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid  ?: "test_user"
    private val expenseCollection = db.collection("users").document(uid).collection("expenses")

    fun addExpense(expense: Expense) {
        expenseCollection.add(expense)
    }

    fun updateExpense(expense: Expense) {
        expenseCollection.document(expense.id.toString()).set(expense)
    }

    fun deleteExpense(expense: Expense) {
        expenseCollection.document(expense.id.toString()).delete()
    }


    fun getExpenses(): CollectionReference {
        return expenseCollection
    }

    companion object {
        private var _instance: ExpenseRepo? = null
        private fun _getInstance(): ExpenseRepo {
            if (_instance == null) {
                _instance = ExpenseRepo()
            }
            return _instance!!
        }
        val instance get() = _getInstance()
    }

}