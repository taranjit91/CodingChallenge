package com.scotia.codingchallenge.ui

import android.content.Context
import com.scotia.codingchallenge.model.GithubService
import com.scotia.codingchallenge.model.Repos
import com.scotia.codingchallenge.model.User
import com.scotia.codingchallenge.model.retrofit.RetrofitInstance
import com.scotia.codingchallenge.utils.MessageType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitReposInteractor(val context: Context) : GitReposContract.Interactor {
    override fun fetchUserInfoFromServer(userId: String, listener: GitReposContract.OnGettingUserInfoFromServer) {
        val retrofit = RetrofitInstance.getInstance()
        val storiesAPI = retrofit.create(GithubService::class.java)
        val call = storiesAPI.getUserInfo(userId)
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                listener.errorGettingInfo(MessageType.ErrorGettingData)
            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                if (response == null) {
                    listener.errorGettingInfo(MessageType.ErrorGettingData)
                    return
                } else {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user == null)
                            listener.userNotFound(MessageType.NoUserFound)
                        else {
                            listener.userExist(user)
                        }
                    } else {
                        when (response.code()) {
                            404 -> {
                                listener.userNotFound(MessageType.NoUserFound)
                            }
                            403 -> {
                                listener.errorGettingInfo(MessageType.ApiLimitExceed)
                            }
                            else ->
                                listener.userNotFound(MessageType.ErrorGettingData)
                        }
                    }
                }

            }

        })
    }

    override fun fetchRepositoriesByUserIdFromServer(
        userId: String,
        listener: GitReposContract.OnGettingReposInfoFromServer
    ) {
        val retrofit = RetrofitInstance.getInstance()
        val storiesAPI = retrofit.create(GithubService::class.java)
        val call = storiesAPI.getUserRepos(userId)
        call.enqueue(object : Callback<List<Repos>> {
            override fun onFailure(call: Call<List<Repos>>?, t: Throwable?) {
                listener.onErrorGettingRepos(MessageType.ErrorGettingData)
            }

            override fun onResponse(call: Call<List<Repos>>?, response: Response<List<Repos>>?) {
                if (response == null) {
                    listener.onErrorGettingRepos(MessageType.ErrorGettingData)
                    return
                } else {
                    if (response.isSuccessful) {
                        val repos = response.body()
                        if (repos == null || repos.isEmpty())
                            listener.noPublicReposForUser(MessageType.NoReposFound)
                        else {
                            listener.successfullyGettingRepos(repos)
                        }
                    }
                }
            }
        })
    }
}