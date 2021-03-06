package com.scotia.codingchallenge.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.scotia.codingchallenge.R
import com.scotia.codingchallenge.model.Repos
import com.scotia.codingchallenge.model.User
import com.scotia.codingchallenge.ui.adapter.GitReposRecyclerViewAdapter
import com.scotia.codingchallenge.utils.CheckInternetConnectivity
import com.scotia.codingchallenge.utils.DateUtils
import com.scotia.codingchallenge.utils.GlideApp
import com.scotia.codingchallenge.utils.MessageType
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.*

/*
DESCRIPTION : The activity is responsible for displaying data, error messages on View.
The communication part is handled by presenter.
 */

class GitReposActivity : AppCompatActivity(), GitReposContract.View {
    lateinit var presenter: GitReposPresenter //
    var userId = ""

    override fun updateUserInfo(user: User) {
        //user exist , send request for fetching repos
        if (userId.isNotBlank()) {
            if (CheckInternetConnectivity.isOnline(this))//send request only if internet is available
            presenter.fetchRepositoriesForUserId(userId)
            else
                showMessage(MessageType.InternetNotAvailable)
        }
        //update view
        GlideApp.with(userImage.context)
            .load(user.avatarUrl)
            .into(userImage)

        userNameText.text = user.name
    }

    override fun updateViewForUserNotFound() {
        resetSearchButton()
        showMessage(MessageType.NoUserFound)
    }

    override fun showMessage(messageType: MessageType) {
        hideKeyboardFrom(this, enterIdEditText)
        var message = ""
        when (messageType) {
            MessageType.InternetNotAvailable -> {
                message = resources.getString(R.string.error_internet)
            }
            MessageType.NoUserFound -> {
                message = resources.getString(R.string.user_not_found)
                resetView()
            }
            MessageType.UserIdFieldEmpty -> {
                message = resources.getString(R.string.error_userid_empty)
            }
            MessageType.ErrorGettingData -> {
                message = resources.getString(R.string.error_getting_data)
                resetSearchButton()
            }
            MessageType.NoReposFound -> {
                message = resources.getString(R.string.error_noreposfound)
                resetSearchButton()
            }
            MessageType.ApiLimitExceed -> {
                message = resources.getString(R.string.error_apilimit)
                resetSearchButton()
            }


        }
        if (message.isNotBlank())
            FancyToast.makeText(this, message, Toast.LENGTH_LONG, FancyToast.INFO, true).show()
    }

    override fun updateRecyclerViewForRepositories(repos: List<Repos>) {
        //setting layout and adapter for recycler view
        val adapter = GitReposRecyclerViewAdapter(this, repos, this)
        reposRecyclerView.layoutManager = LinearLayoutManager(this)
        val resId = R.anim.layout_animationfrombottom
        val animation = AnimationUtils.loadLayoutAnimation(this, resId)
        reposRecyclerView.layoutAnimation = (animation)
        reposRecyclerView.adapter = adapter

        resetSearchButton()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = GitReposPresenter(this, GitReposInteractor(this@GitReposActivity))

        search_btn.setOnClickListener {
            userId =
                enterIdEditText.text.trim().toString()

            //hide last clicked repo info, in case visible
            hideRepoInformation()

            if (userId.isNotBlank()) {
                hideKeyboardFrom(this, enterIdEditText)
                if (CheckInternetConnectivity.isOnline(this)) {
                    updateViewForOngoingRequest()
                    presenter.fetchUserInfo(userId)
                } else {

                    showMessage(MessageType.InternetNotAvailable)
                }

            } else {

                showMessage(MessageType.UserIdFieldEmpty)
            }
        }
    }

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showRepoInformation(repo: Repos?) {
        if (repoInformationLayout.visibility == View.VISIBLE)
            repoInformationLayout.visibility = View.GONE
        if (repo != null) {
            lastUpdatedText.text = DateUtils.toSimpleDateString(repo.updatedAt)
            forksText.text = repo.forks.toString()
            starsText.text = repo.stargazersCount.toString()
        }

        // slide-up animation
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        if (repoInformationLayout.visibility == View.GONE) {
            repoInformationLayout.visibility = (View.VISIBLE)
            repoInformationLayout.startAnimation(slideUp)
        }

    }

    fun hideRepoInformation() {
        if (repoInformationLayout.visibility == View.VISIBLE)
            repoInformationLayout.visibility = View.GONE
    }

    private fun updateViewForOngoingRequest() {
        search_btn.text = resources.getString(R.string.searching)
        search_btn.isEnabled = false
        search_btn.isClickable = false
    }

    private fun resetSearchButton() {
        search_btn.isEnabled = true
        search_btn.isClickable = true
        search_btn.text = resources.getString(R.string.btn_search)

    }

    private fun resetView() {
        resetSearchButton()
        reposRecyclerView.adapter = null
        userImage.setImageDrawable(null)
        userNameText.text = ""

    }
}
