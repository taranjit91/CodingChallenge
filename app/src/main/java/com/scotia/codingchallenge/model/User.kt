package com.scotia.codingchallenge.model


import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep

@Keep
data class User(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("name")
    val name: String
)