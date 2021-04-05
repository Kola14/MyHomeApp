package com.example.myhomeapp.ui.fragments.eventHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myhomeapp.R

class EventHistoryFragment : Fragment() {

    private lateinit var galleryViewModel: EventHistoryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProvider(this).get(EventHistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_event_history, container, false)
        return root
    }
}