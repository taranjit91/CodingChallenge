package com.scotia.codingchallenge.ui

import com.scotia.codingchallenge.model.Repos
import com.scotia.codingchallenge.model.User
import com.scotia.codingchallenge.utils.MessageType

class GitReposPresenter(private val view: GitReposContract.View, val interactor: GitReposInteractor) :
    GitReposContract.Presenter, GitReposContract.OnGettingReposInfoFromServer,
    GitReposContract.OnGettingUserInfoFromServer {
    override fun errorGettingInfo(messageType: MessageType) {
        view.showMessage(messageType)
    }

    override fun noPublicReposForUser(messageType: MessageType) {
        view.showMessage(messageType)
    }

    override fun userExist(user: User) {
        view.updateUserInfo(user)
    }

    override fun userNotFound(messageType: MessageType) {
        view.updateViewForUserNotFound()
    }

    override fun successfullyGettingRepos(repos: List<Repos>) {
        view.updateRecyclerViewForRepositories(repos)
    }

    override fun onErrorGettingRepos(messageType: MessageType) {
        view.showMessage(messageType)
    }

    override fun fetchRepositoriesForUserId(userId: String) {
        interactor.fetchRepositoriesByUserIdFromServer(userId, this)
    }

    override fun fetchUserInfo(userId: String) {
        interactor.fetchUserInfoFromServer(userId, this)
    }

}