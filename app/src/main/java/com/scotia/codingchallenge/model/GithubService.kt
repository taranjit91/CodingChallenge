package com.scotia.codingchallenge.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{userId}")
    fun getUserInfo(@Path("userId") userId: String): Call<User>

    @GET("users/{userId}/repos")
    fun getUserRepos(@Path("userId") userId: String): Call<List<Repos>>

}