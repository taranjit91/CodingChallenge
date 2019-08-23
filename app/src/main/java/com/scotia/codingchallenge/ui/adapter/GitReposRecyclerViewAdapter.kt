package com.scotia.codingchallenge.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.scotia.codingchallenge.R
import com.scotia.codingchallenge.model.Repos
import com.scotia.codingchallenge.ui.GitReposActivity


class GitReposRecyclerViewAdapter(val context: Context, val list: List<Repos>, gitActivity: GitReposActivity) :
    RecyclerView.Adapter<GitReposRecyclerViewAdapter.ReposViewHolder>() {
    var activity = gitActivity
    lateinit var lastClickedRepo: Repos
    var infoVisible = false
    override fun getItemCount(): Int {
        return list.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.itemview_repositories,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val story = list[position]

        if (story == null) {
            holder.clear()
        } else {
            holder.bind(story)
        }

    }


    inner class ReposViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvTitle: TextView = view.findViewById(R.id.item_cardView_repoTitle)
        var tvDesc: TextView = view.findViewById(R.id.item_cardView_repoDesc)
        var cardViewLayout: RelativeLayout = view.findViewById(R.id.item_cardView)

        fun bind(repo: Repos) {
            tvTitle.text = repo.name.trim()
            tvDesc.text = repo.description
            cardViewLayout.setOnClickListener {
                if (::lastClickedRepo.isInitialized) {
                    if (lastClickedRepo == repo && infoVisible) {
                        infoVisible = false
                        activity.hideRepoInformation()
                    } else {
                        infoVisible = true
                        activity.showRepoInformation(repo)
                    }
                    lastClickedRepo = repo
                } else {
                    lastClickedRepo = repo
                    activity.showRepoInformation(repo)
                }
            }
        }

        fun clear() {
            tvTitle.text = null
            tvDesc.text = null

        }

    }


}