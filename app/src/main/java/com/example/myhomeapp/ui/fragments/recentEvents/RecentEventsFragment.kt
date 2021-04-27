package com.example.myhomeapp.ui.fragments.recentEvents

import android.os.Bundle
import android.os.Handler
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
        //viewModel.getSignals()

        if (!(activity?.applicationContext as MyApplication).getTimerStatus()){
            val timer = Timer()
            val handler = Handler()
            val doAsynchronousTask: TimerTask = object : TimerTask() {
                override fun run() {
                    handler.post {
                        try {
                            if ((activity?.applicationContext as MyApplication).getUpdatableStatus()) {
                                viewModel.getSignals()
                            }

                            if ((activity?.applicationContext as MyApplication).getPushNotificationsStatus()) {
                                if (!viewModel.signalsLiveData.value.isNullOrEmpty() && viewModel.signalsLiveData.value!!.size != 0){
                                    if (differenceInMinutes(viewModel.signalsLiveData.value!![0].time, getCurrentTimeInString()) in 0..5) {
                                        val notificationId = (activity?.applicationContext as MyApplication).getNotificationId()
                                        var notification = view.let {
                                            NotificationCompat.Builder(
                                                    it.context,
                                                    "MyHomeAppChannel"
                                            )
                                                    .setSmallIcon(R.drawable.ic_baseline_error_outline_24)
                                                    .setContentTitle(resources.getString(R.string.signal_recieved))
                                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                    .setAutoCancel(true)
                                                    .build()
                                        }

                                        if (notification != null) {
                                            notificationManager.notify(notificationId, notification)
                                        }
                                    }
                                }
                            }

                        } catch (e: Exception) {

                        }
                    }
                }
            }
            timer.schedule(doAsynchronousTask, 0, 60000)
        }
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

    private fun differenceInMinutes(time1: String, time2: String) : Int{
        if (time1.length >= 5 && time2.length >= 5){
            if (time1[0] == time2[0] && time1[1] == time2[1]) {
                return (time1[3].toInt() * 10 + time1[4].toInt() - time2[3].toInt() * 10 - time2[4].toInt())
            }
        }
        return 60
    }

    private fun getCurrentTimeInString(): String{
        return DateFormat.getTimeInstance(DateFormat.SHORT)
                .format(
                        Calendar.getInstance().time
                ).toString()
    }

    private fun observeLiveData() {
        viewModel.signalsLiveData.observe(viewLifecycleOwner) { signalsList ->
            if (signalsList != null) {
                recentEventsAdapter.submit(signalsList)
            }


            if (!signalsList.isNullOrEmpty() && signalsList.size != 0){
                if ((activity?.applicationContext as MyApplication).getPushNotificationsStatus()) {
                    if (differenceInMinutes(signalsList[0].time, getCurrentTimeInString()) in 0..5) {



/*

                        val notificationIntent = Intent((activity?.applicationContext as MyApplication).applicationContext, NotificationsReceiver::class.java)
                        notificationIntent.action = "App_notification"
                        val sender = PendingIntent.getBroadcast((activity?.applicationContext as MyApplication).applicationContext, 1, notificationIntent, 0)
                        sender.send()

 */
                                /*
                        Intent().also { intent ->
                            intent.setAction("com.example.broadcast.MY_NOTIFICATION")
                            intent.putExtra("data", "Nothing to see here, move along.")
                            sendBroadcast(intent)
                        }

                                 */
/*
                        val notificationId = (activity?.applicationContext as MyApplication).getNotificationId()
                        var notification = this.view?.let {
                            NotificationCompat.Builder(
                                    it.context,
                                    "MyHomeAppChannel"
                            )
                                    .setSmallIcon(R.drawable.ic_baseline_error_outline_24)
                                    .setContentTitle(this.getString(R.string.signal_recieved))
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setAutoCancel(true)
                                    .build()
                        }

                        if (notification != null) {
                            notificationManager.notify(notificationId, notification)
                        }



 */

                    }
                }
            }
        }
    }
}