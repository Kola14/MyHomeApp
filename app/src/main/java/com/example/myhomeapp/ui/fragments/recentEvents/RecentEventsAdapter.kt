package com.example.myhomeapp.ui.fragments.recentEvents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.example.myhomeapp.R
import com.example.myhomeapp.core.RecyclerAdapter
import com.example.myhomeapp.models.Signals
import com.example.myhomeapp.remote.RetrofitApi

class RecentEventsAdapter (
    private  val api: RetrofitApi,
    private val model: Unit
) : RecyclerAdapter<Signals>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<Signals> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)
        return object : BindingHolder<Signals>(view) {
            override fun bind(model: Signals, position: Int) {

                view.findViewById<TextView>(R.id.time).text = model.time
                view.findViewById<TextView>(R.id.date).text = model.date
                view.findViewById<TextView>(R.id.title).text = view.context.getString(R.string.signal_recieved)

                Glide
                    .with(view.context)
                    .load("http://thumbs.dreamstime.com/z/robber-holding-flashlight-piece-pipe-24011060.jpg")
                    .centerCrop()
                    .into(view.findViewById<ImageView>(R.id.image_view_photo))

                if (!model.isconfirmed) {
                    view.findViewById<AppCompatButton>(R.id.btnConfirm).isEnabled = true
                    view.findViewById<AppCompatButton>(R.id.btnConfirm).visibility = View.VISIBLE

                    view.findViewById<AppCompatButton>(R.id.btnConfirm).setOnClickListener(){

                        model.isconfirmed = true

                        api.confirmSignal(model.id)

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