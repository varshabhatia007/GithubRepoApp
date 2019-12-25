package com.example.repo.ui

import androidx.lifecycle.ViewModel
import com.example.repo.repo.GitHubRepoDataSource
import javax.inject.Inject

class MainVM  @Inject constructor(val gitHubRepoDataSource: GitHubRepoDataSource) : ViewModel() {
    suspend fun getTrendingRepos() = gitHubRepoDataSource.getTrendingRepo()
}