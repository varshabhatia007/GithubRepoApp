package com.example.repo.repo

import com.example.repo.githubapi.DataSource
import com.example.repo.githubapi.GithubApi
import javax.inject.Inject

class GitHubRepoDataSource @Inject constructor(val githubApi: GithubApi) : DataSource() {
    suspend fun getTrendingRepo(sortByData: String)
            = getResult { githubApi.getTrendingRepos(sortByData) }
}