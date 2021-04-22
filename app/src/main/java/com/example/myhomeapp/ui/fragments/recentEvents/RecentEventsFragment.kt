package com.example.myhomeapp.ui.fragments.recentEvents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myhomeapp.MyApplication
import com.example.myhomeapp.R
import com.example.myhomeapp.models.Signals
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
        viewModel.getSignals()

        val deviceList: ArrayList<String> = ArrayList()
        deviceList.add("All")
        //deviceList.add(viewModel.getDevices().toString())
        view.findViewById<Spinner>(R.id.deviceSelectSpinner)?.apply {
            val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, deviceList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.adapter =  spinnerAdapter
        }
    }

    private fun initAdapter() {
        recentEventsAdapter = RecentEventsAdapter(viewModel.api, viewModel.getSignals())
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

        viewModel.signalsLiveData
        fun onChange(){

            for (i: Signals in viewModel.signalsLiveData.value!!){
                if (i.isconfirmed){
                    viewModel.confirmSignal(i.id);
                }
            }

            initAdapter()
            viewModel.getSignals()
        }



        viewModel.signalsLiveData.value?.also {
            initAdapter()
            viewModel.getSignals()
        }
    }
}