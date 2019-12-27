package com.example.repo.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.repo.extensions.load
import com.example.repo.githubapi.TrendingRepo
import kotlinx.android.synthetic.main.rv_repo_item.view.*

class RepoVH ( view: View) : RecyclerView.ViewHolder(view) {

    fun bind(trendingRepo: TrendingRepo, selectedItem: Int, onRepoClicked : () -> Unit) {
        with(itemView) {
            ivAvatar.load(trendingRepo.avatar!!)
            userName.text = trendingRepo.username
            repoName.text = trendingRepo.repo.name

            setOnClickListener {
                onRepoClicked()
            }

        }
    }
}