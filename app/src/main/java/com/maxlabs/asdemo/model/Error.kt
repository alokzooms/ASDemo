package com.maxlabs.asdemo.model


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("error")
    val error: String
)