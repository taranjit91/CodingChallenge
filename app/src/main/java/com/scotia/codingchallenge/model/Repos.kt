package com.scotia.codingchallenge.model


import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep

@Keep
data class Repos(
    @SerializedName("description")
    val description: String,
    @SerializedName("forks")
    val forks: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)