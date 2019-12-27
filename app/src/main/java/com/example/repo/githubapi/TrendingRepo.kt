package com.example.repo.githubapi

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.repo.db.RepoTypeConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = "repos",
    primaryKeys = ["username"]
)
data class TrendingRepo(
    @NonNull
    @SerializedName("username")
    var username: String = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("url")
    var url: String? = "",
    @SerializedName("avatar")
    var avatar: String? = "",
    @TypeConverters(RepoTypeConverter::class)
    @SerializedName("repo")
    var repo: Repo,
    var timeStamp: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class Repo(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("url")
    var url: String? = ""
) : Parcelable