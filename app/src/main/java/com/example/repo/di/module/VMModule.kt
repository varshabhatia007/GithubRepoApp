package com.example.repo.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.repo.di.ViewModelFactory
import com.example.repo.di.ViewModelKey
import com.example.repo.ui.MainVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VMModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainVM::class)
    abstract fun bindMainVM(mainVM: MainVM) : ViewModel
}