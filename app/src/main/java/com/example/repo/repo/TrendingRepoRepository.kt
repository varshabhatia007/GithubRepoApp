package com.example.repo.repo

import androidx.lifecycle.distinctUntilChanged
import com.example.repo.db.RepoDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingRepoRepository @Inject constructor(private val repoDao: RepoDao,
                                                 private val gitHubRepoDataSource: GitHubRepoDataSource) {


    /** time to leave is set to 2 hours  as per requirement*/
    private val ttl = 2 * 60 * 60 * 1000

    fun getTrendingRepo(forceFetch: Boolean = false, sortByData: String) = resultLiveData(
        databaseQuery = { repoDao.getTrendingRepo(sortByData) },
        networkCall = { gitHubRepoDataSource.getTrendingRepo(sortByData) },
        saveCallResult = { it ->
            it.forEach {
                it.sortByData = sortByData
            }
            repoDao.insertAll(it)
        },
        shouldFetch = {
            /**
             * conditional check, this will return true
             * 1. if first time db is empty
             * 2. when db data is 2 hours old
             * 3. when user performs swipe to refresh
             * */
            it.isNullOrEmpty() || (System.currentTimeMillis() - it[0].timeStamp) > ttl || forceFetch
        }
    ).distinctUntilChanged()

}
