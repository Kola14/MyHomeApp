package com.example.myhomeapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SignalsWrapper(
    @SerializedName("rows") val data: List<Signals>
)

@Parcelize
data class Signals(
    val id: Int,
    val date: String,
    val time: String,
    val isConfirmed: Boolean
): Parcelable