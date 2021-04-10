package com.example.myhomeapp.ui.fragments.recentEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myhomeapp.MyApplication
import com.example.myhomeapp.R
import javax.inject.Inject


class RecentEventsFragment : Fragment() {
    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: RecentEventsViewModel
    lateinit var recentEventsAdapter: RecentEventsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(RecentEventsViewModel::class.java)
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
        recentEventsAdapter = RecentEventsAdapter(viewModel.getSignals(0))
        view?.findViewById<RecyclerView>(R.id.recentEventRecycler)?.apply {
            adapter = recentEventsAdapter
        }

    }

    private fun observeLiveData() {
        viewModel.signalsLiveData.observe(viewLifecycleOwner) { newsList ->
            if (!newsList.isNullOrEmpty()) {
                recentEventsAdapter.submit(newsList)
            }
        }
    }
}