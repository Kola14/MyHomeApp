package com.example.myhomeapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DevicesWrapper(
        @SerializedName("rows") val data: List<Devices>
)

@Parcelize
data class Devices(
        val id: Int,
        val device_id: Int,
        val device_name: String
): Parcelable