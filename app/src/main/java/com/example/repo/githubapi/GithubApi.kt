package com.example.repo.githubapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val BASE_URL = "https://github-trending-api.now.sh/"
    }


    @GET("developers")
    suspend fun getTrendingRepos(@Query("since") since:String =""): Response<List<TrendingRepo>>

}