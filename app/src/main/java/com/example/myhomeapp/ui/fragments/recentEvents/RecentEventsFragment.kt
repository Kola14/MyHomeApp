package com.example.myhomeapp.ui.fragments.recentEvents

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.myhomeapp.MyApplication
import com.example.myhomeapp.R
import com.example.myhomeapp.models.Devices
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class RecentEventsFragment : Fragment() {
    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: RecentEventsViewModel
    lateinit var recentEventsAdapter: RecentEventsAdapter
    lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(RecentEventsViewModel::class.java)
        this.context?.let { notificationManager = NotificationManagerCompat.from(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recent_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        observeLiveData()
        viewModel.getSignals()
        initTimer()
    }

    private fun initTimer(){

        var timer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if ((activity?.applicationContext as MyApplication).getUpdatableStatus()){
                    viewModel.getSignals()
                }
            }

            override fun onFinish() {
                if ((activity?.applicationContext as MyApplication).getUpdatableStatus()){
                    this.start()
                }
            }
        }

        timer.start()

    }

    private fun initAdapters() {
        recentEventsAdapter = RecentEventsAdapter(viewModel.api, viewModel.getSignals())
        view?.findViewById<RecyclerView>(R.id.recentEventRecycler)?.apply {
            adapter = recentEventsAdapter
        }


        val deviceList: ArrayList<String> = ArrayList()
        deviceList.add("All")
        var devicesData = ArrayList<Devices>()
        lifecycleScope.launch {

            val response = viewModel.api.getDevices(1101)
            val data = response.data?.data
            data?.let { devices ->
                devicesData = devices as ArrayList<Devices>
            }

            for (i in devicesData){
                deviceList.add(i.device_name);
            }

            view?.findViewById<Spinner>(R.id.deviceSelectSpinner)?.apply {
                val spinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    deviceList
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                this.adapter =  spinnerAdapter
            }
        }

        view?.findViewById<Spinner>(R.id.deviceSelectSpinner)?.apply {
            this.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    itemview: View,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        viewModel.getSignals()
                    }
                    else{
                        viewModel.getSpecificSignals(getItemAtPosition(position).toString())
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

    }

    private fun observeLiveData() {
        var notificationId = 0
        viewModel.signalsLiveData.observe(viewLifecycleOwner) { signalsList ->
            if (signalsList != null) {
                recentEventsAdapter.submit(signalsList)
            }

            if (!signalsList.isNullOrEmpty() && signalsList.size != 0){
                if ((activity?.applicationContext as MyApplication).getPushNotificationsStatus()) {
                    if (signalsList[0].time[4].toInt() - DateFormat.getTimeInstance(DateFormat.SHORT)
                            .format(
                                Calendar.getInstance().time
                            ).toString()[4].toInt() <= 5
                    ){

                        var notification = this.view?.let { NotificationCompat.Builder(it.context, "MyHomeAppChannel")
                            .setSmallIcon(R.drawable.ic_baseline_error_outline_24)
                            .setContentTitle(this.getString(R.string.signal_recieved))
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .build()
                        }

                        if (notification != null) {
                            notificationManager.notify(notificationId, notification)
                            notificationId++
                        }

                    }
                }
            }
        }
    }
}