package com.example.myhomeapp.models

import com.google.gson.annotations.SerializedName

data class SignalsCount (
    @SerializedName("rows") val rows: List<SignalsCountRow>
)

data class SignalsCountRow(
    @SerializedName("count") val count: Int
)