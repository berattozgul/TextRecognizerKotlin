package com.example.textrecognizerkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHoler>() {
    private var historyList: ArrayList<HistoryModel> = ArrayList()
    private var onClickItem: ((HistoryModel) -> Unit)? = null
    private var onClickDeleteItem: ((HistoryModel) -> Unit)? = null

    fun addItems(items: ArrayList<HistoryModel>) {
        this.historyList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (HistoryModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (HistoryModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHoler(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_history, parent, false)
    )

    override fun onBindViewHolder(holder: HistoryViewHoler, position: Int) {
        val history = historyList[position]
        holder.bindView(history)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(history)
        }
        holder.btnDelete.setOnClickListener {
            onClickDeleteItem?.invoke(history)
        }

    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    class HistoryViewHoler(var view: View) : RecyclerView.ViewHolder(view) {
        var txt = view.findViewById<TextView>(R.id.tvText)
        var btnDelete = view.findViewById<Button>(R.id.historyDeleteButton)
        var time = view.findViewById<TextView>(R.id.timestampTv)

        fun bindView(hstry: HistoryModel) {
            txt.text = hstry.text
            time.text = hstry.time
        }
    }
}