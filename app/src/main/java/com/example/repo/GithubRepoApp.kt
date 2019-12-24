package com.example.repo

import android.app.Activity
import android.app.Application
import com.example.repo.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class GithubRepoApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }

}