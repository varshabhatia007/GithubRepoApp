package com.example.repo.db

import android.content.Context
import androidx.room.*
import com.example.repo.githubapi.TrendingRepo

@Database(
    entities = [TrendingRepo::class],
    version = 1, exportSchema = false
)
@TypeConverters(RepoTypeConverter::class)
abstract class GithubAppDB : RoomDatabase() {

    abstract fun getRepoDao(): RepoDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: GithubAppDB? = null

        fun getInstance(context: Context): GithubAppDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): GithubAppDB {
            return Room.databaseBuilder(context, GithubAppDB::class.java, "github-db")
                .build()
        }
    }

}