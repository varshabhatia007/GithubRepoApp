package com.example.repo.db

import androidx.room.TypeConverter
import com.example.repo.githubapi.Repo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RepoTypeConverter {
    @TypeConverter
    fun fromRepo(repo: Repo?): String? {
        if (repo == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Repo>() {

        }.getType()
        return gson.toJson(repo, type)
    }

    @TypeConverter
    fun toRepo(repoByString: String?): Repo? {
        if (repoByString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Repo>() {

        }.getType()
        return gson.fromJson(repoByString, type)
    }
}