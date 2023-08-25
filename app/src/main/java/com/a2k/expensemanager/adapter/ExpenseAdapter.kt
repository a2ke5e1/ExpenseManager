package com.a2k.expensemanager.com.a2k.expensemanager.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a2k.expensemanager.databinding.ExpenseItemBinding
import com.a2k.expensemanager.model.Expense

class ExpenseAdapter(
    private val onExpenseClick: (Expense) -> Unit,
    private val onExpenseLongPress: (Expense) -> Unit
): RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(val binding: ExpenseItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    private var expenseList = emptyList<Expense>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExpenseItemBinding.inflate(inflater, parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun getItemCount(): Int = expenseList.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        Log.d("ExpenseAdapter", "onBindViewHolder: ${expenseList[position]}")
        val currentExpense = expenseList[position]
        val binding = holder.binding
        binding.expenseTitle.text = currentExpense.title.toString()
        binding.expenseAmount.text = currentExpense.amount.toString()
        binding.expenseDate.text = currentExpense.date.toString()
        binding.expenseCategory.text = currentExpense.category.toString()
        binding.expenseNote.text = currentExpense.note.toString()

        binding.root.setOnClickListener {
            onExpenseClick(currentExpense)
        }

        binding.root.setOnLongClickListener {
            onExpenseLongPress(currentExpense)
            true
        }

    }

    fun setExpense(expense: List<Expense>) {
        this.expenseList = expense
        notifyDataSetChanged()
    }

}