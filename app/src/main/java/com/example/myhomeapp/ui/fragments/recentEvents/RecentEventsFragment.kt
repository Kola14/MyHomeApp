package com.example.myhomeapp.ui.fragments.recentEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myhomeapp.R

class RecentEventsFragment : Fragment() {

    private lateinit var homeViewModel: RecentEventsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(RecentEventsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_recent_events, container, false)

        return root
    }
}