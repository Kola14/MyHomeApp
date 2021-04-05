package com.example.myhomeapp.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myhomeapp.R

class SettingsFragment : Fragment() {

    private lateinit var slideshowViewModel: SettingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        /*
        val textView: TextView = root.findViewById(R.id.settingsTitle)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

         */
        root.findViewById<TextView>(R.id.settingOperation).text = "Работа устройства";
        root.findViewById<TextView>(R.id.settingNotification).text = "Уведомления";
        return root
    }
}