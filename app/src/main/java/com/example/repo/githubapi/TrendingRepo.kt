package com.example.repo.githubapi

import com.google.gson.annotations.SerializedName

data class TrendingRepo(
    @SerializedName("username")
    var username: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("url")
    var url: String? = "",
    @SerializedName("avatar")
    var avatar: String? = "",
    @SerializedName("repo")
    var repo: Repo
)

data class Repo(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("url")
    var url: String? = ""
)