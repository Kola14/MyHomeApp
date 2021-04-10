package com.example.myhomeapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class SignalsWrapper(
    //@SerializedName("rows") val data: List<Signals>
        val data: List<Signals>
)

@Parcelize
data class Signals(
    val id: Int,
    val date: String,
    val time: String,
    val isConfirmed: Boolean
): Parcelable