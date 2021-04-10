package com.example.myhomeapp.ui.fragments.eventHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.myhomeapp.R
import com.example.myhomeapp.core.RecyclerAdapter
import com.example.myhomeapp.models.Signals

class EventHistoryAdapter (
    private val model: Unit
) : RecyclerAdapter<Signals>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<Signals> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)

        return object : BindingHolder<Signals>(view) {
            override fun bind(model: Signals, position: Int) {

                view.findViewById<TextView>(R.id.time).text = model.time;
                view.findViewById<TextView>(R.id.date).text = model.date;

                if (model.isConfirmed) {
                    view.findViewById<TextView>(R.id.status).text = "Подтверждено"
                    view.findViewById<TextView>(R.id.status).backgroundTintList = view.context.getColorStateList(R.color.light_green)
                }
                else {
                    view.findViewById<TextView>(R.id.status).text = "Не подтверждено"
                    view.findViewById<TextView>(R.id.status).backgroundTintList = view.context.getColorStateList(R.color.red)
                }
            }
        }
    }
}