package com.example.myhomeapp.ui.fragments.eventHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myhomeapp.MyApplication
import com.example.myhomeapp.R
import com.example.myhomeapp.ui.fragments.recentEvents.RecentEventsAdapter
import com.example.myhomeapp.ui.fragments.recentEvents.RecentEventsViewModel
import javax.inject.Inject

class EventHistoryFragment : Fragment() {
    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: EventHistoryViewModel
    lateinit var eventHistoryAdapter: EventHistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(EventHistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recent_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeLiveData()

        viewModel.getSignals(0)
    }

    private fun initAdapter() {
        eventHistoryAdapter = EventHistoryAdapter(viewModel.getSignals(0))
        view?.findViewById<RecyclerView>(R.id.recentEventRecycler)?.apply {
            adapter = eventHistoryAdapter
        }

    }

    private fun observeLiveData() {
        viewModel.signalsLiveData.observe(viewLifecycleOwner) { newsList ->
            if (!newsList.isNullOrEmpty()) {
                eventHistoryAdapter.submit(newsList)
            }
        }
    }
}