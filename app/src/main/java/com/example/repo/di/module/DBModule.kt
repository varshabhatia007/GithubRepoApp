package com.example.repo.di.module

import android.app.Application
import com.example.repo.db.GithubAppDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule  {

    @Singleton
    @Provides
    fun provideDb(app: Application) = GithubAppDB.getInstance(app)

    @Singleton
    @Provides
    fun provideLegoSetDao(db: GithubAppDB) = db.getRepoDao()
}