package com.example.repo.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.repo.extensions.load
import com.example.repo.githubapi.TrendingRepo
import kotlinx.android.synthetic.main.rv_repo_item.view.*

class RepoVH(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(trendingRepo: TrendingRepo, selectedItem: Int, onRepoClicked: () -> Unit) {
        with(itemView) {
            ivAvatar.load(trendingRepo.avatar!!)
            userName.text = trendingRepo.username
            userUrl.text = trendingRepo.url
            repoName.text = "Name: "+trendingRepo.repo.name
            repoDesc.text = "Description: " + trendingRepo.repo.description
            repoUrl.text = "Url: " + trendingRepo.repo.url

            detailLayout.visibility = if(selectedItem == adapterPosition) View.VISIBLE else View.GONE

            setOnClickListener {
                onRepoClicked()
            }

        }
    }
}