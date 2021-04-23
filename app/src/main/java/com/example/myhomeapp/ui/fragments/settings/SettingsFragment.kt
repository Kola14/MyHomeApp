package com.example.myhomeapp.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myhomeapp.MyApplication
import com.example.myhomeapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject


class SettingsFragment : Fragment() {
    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        settingsViewModel = ViewModelProvider(this, providerFactory).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        root.findViewById<TextView>(R.id.settingOperation).text = "Работа устройства";
        root.findViewById<TextView>(R.id.settingNotification).text = "Уведомления";

        root.findViewById<Switch>(R.id.operationSwitch).setOnClickListener{
            (activity?.applicationContext as MyApplication).setUpdatableStatus(!(activity?.applicationContext as MyApplication).getUpdatableStatus())
        }

        root.findViewById<Switch>(R.id.notificationSwitch).setOnClickListener{
            (activity?.applicationContext as MyApplication).setPushNotificationsStatus(!(activity?.applicationContext as MyApplication).getPushNotificationsStatus())
        }

        root.findViewById<AppCompatButton>(R.id.btnClearHistory).setOnClickListener{
            MaterialAlertDialogBuilder(root.context)
                .setTitle(resources.getString(R.string.clear_history))
                .setMessage(resources.getString(R.string.confirm_clear_history_operation))
                .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                   dialog.dismiss()
                }
                .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                    settingsViewModel.clearHistory()
                }
                .show()
        }

        return root
    }
}