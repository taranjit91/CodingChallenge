package com.scotia.codingchallenge.ui

import com.scotia.codingchallenge.model.Repos
import com.scotia.codingchallenge.model.User
import com.scotia.codingchallenge.utils.MessageType

/*
DESCRIPTION : The contract lists responsibilities to be handled by View and Presenter
 */
interface GitReposContract {

    interface View {
        fun updateUserInfo(user: User)
        fun updateViewForUserNotFound()
        fun showMessage(messageType: MessageType)
        fun updateRecyclerViewForRepositories(repos: List<Repos>)

    }

    interface OnGettingUserInfoFromServer {
        fun userExist(user: User)
        fun userNotFound(messageType: MessageType)
        fun errorGettingInfo(messageType: MessageType)
    }

    interface OnGettingReposInfoFromServer {
        fun successfullyGettingRepos(repos: List<Repos>)
        fun noPublicReposForUser(messageType: MessageType)
        fun onErrorGettingRepos(messageType: MessageType)
    }

    interface Presenter {
        fun fetchRepositoriesForUserId(userId: String)
        fun fetchUserInfo(userId: String)

    }

    interface Interactor {
        fun fetchUserInfoFromServer(userId: String, listener: OnGettingUserInfoFromServer)
        fun fetchRepositoriesByUserIdFromServer(userId: String, listener: OnGettingReposInfoFromServer)

    }
}