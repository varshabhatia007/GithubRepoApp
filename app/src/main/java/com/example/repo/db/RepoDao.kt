package com.example.repo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.repo.githubapi.TrendingRepo

@Dao
interface RepoDao {

    @Query("SELECT * FROM repos ")
    fun getTrendingRepo(): LiveData<List<TrendingRepo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trendingRepos: List<TrendingRepo>)

}