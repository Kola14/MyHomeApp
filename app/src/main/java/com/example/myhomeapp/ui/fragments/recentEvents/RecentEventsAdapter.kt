package com.example.myhomeapp.ui.fragments.recentEvents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.myhomeapp.R
import com.example.myhomeapp.core.RecyclerAdapter
import com.example.myhomeapp.models.Signals
import com.example.myhomeapp.remote.RetrofitApi
import javax.inject.Inject

class RecentEventsAdapter (
    private val model: Unit
) : RecyclerAdapter<Signals>() {
    @Inject
    lateinit var api: RetrofitApi

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<Signals> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)

        return object : BindingHolder<Signals>(view) {
            override fun bind(model: Signals, position: Int) {

                view.findViewById<TextView>(R.id.time).text = model.time;
                view.findViewById<TextView>(R.id.date).text = model.date;
                view.findViewById<TextView>(R.id.title).text = "Получен сигнал";

                if (!model.isConfirmed) {
                    view.findViewById<AppCompatButton>(R.id.btnConfirm).setOnClickListener(){

                        model.isConfirmed = true
                        //api.confirm(userId)
                        view.findViewById<AppCompatButton>(R.id.btnConfirm).isEnabled = false
                        view.findViewById<AppCompatButton>(R.id.btnConfirm).visibility = View.GONE
                    }
                }
                else {
                    view.findViewById<AppCompatButton>(R.id.btnConfirm).isEnabled = false
                    view.findViewById<AppCompatButton>(R.id.btnConfirm).visibility = View.GONE
                }
            }
        }
    }
}