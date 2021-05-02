package com.example.myhomeapp.ui.fragments.eventHistory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
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

                if (model.image != null)
                {
                    Glide
                            .with(view.context)
                            //.load("http://thumbs.dreamstime.com/z/robber-holding-flashlight-piece-pipe-24011060.jpg")
                            .load("http://185.197.74.219:8000/api/getimage/" + model.image)
                            .centerCrop()
                            .into(view.findViewById<ImageView>(R.id.image_view_photo))
                }

                if (model.isconfirmed) {
                    view.findViewById<TextView>(R.id.status).text = view.context.getString(R.string.confirmed)
                    view.findViewById<TextView>(R.id.status).backgroundTintList = view.context.getColorStateList(R.color.light_green)
                }
                else {
                    view.findViewById<TextView>(R.id.status).text = view.context.getString(R.string.not_confirmed)
                    view.findViewById<TextView>(R.id.status).backgroundTintList = view.context.getColorStateList(R.color.red)
                }
            }
        }
    }
}