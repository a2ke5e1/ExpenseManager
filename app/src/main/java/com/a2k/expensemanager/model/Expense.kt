package com.a2k.expensemanager.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Expense(
    val title: String? = null,
    val amount: Double? = null,
    val date: Timestamp? = null,
    val category: String? = null,
    val note: String? = null,

    @DocumentId
    val id: String? = null,
)
