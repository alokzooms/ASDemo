package com.maxlabs.asdemo.model


import com.google.gson.annotations.SerializedName

data class AuthenticationModel(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)