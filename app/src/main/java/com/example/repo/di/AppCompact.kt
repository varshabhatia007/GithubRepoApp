package com.example.repo.di

import android.app.Application
import com.example.repo.GithubRepoApp
import com.example.repo.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class]
)
interface AppComponent {


    @Component.Builder
    interface Builder {


        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appModule(application: AppModule): Builder

        fun build(): AppComponent

    }

    fun inject(githubRepoApp: GithubRepoApp)

}