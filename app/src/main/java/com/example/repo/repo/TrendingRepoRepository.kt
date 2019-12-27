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

    fun getTrendingRepo() = resultLiveData(
        databaseQuery = { repoDao.getTrendingRepo() },
        networkCall = { gitHubRepoDataSource.getTrendingRepo() },
        saveCallResult = { repoDao.insertAll(it) },
        shouldFetch = {
            /**
             * conditional check, this will return true if first time db is empty and when db data is 2 hours old
             * */
            it.isNullOrEmpty()  || (System.currentTimeMillis() -  it[0].timeStamp) > ttl
        }
    ).distinctUntilChanged()

}
