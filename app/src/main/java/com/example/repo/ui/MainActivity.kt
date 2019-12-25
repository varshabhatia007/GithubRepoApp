package com.example.repo.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.repo.R
import com.example.repo.extensions.injectViewModel
import com.example.repo.githubapi.Resource
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() , HasSupportFragmentInjector {

    companion object {
        val TAG = "MainActivity"
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var coroutineScope: CoroutineScope

    private lateinit var mainVM: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainVM = injectViewModel(viewModelFactory)

        coroutineScope.launch {
            val result = mainVM.getTrendingRepos()
            when(result.status) {
                Resource.Status.SUCCESS -> {
                    Log.e(TAG,"success  ${result.data}  ")
                }
                Resource.Status.ERROR ,
                Resource.Status.LOADING ->  {
                    Log.e(TAG," ${result.message}  ")
                }
            }
        }

    }

}