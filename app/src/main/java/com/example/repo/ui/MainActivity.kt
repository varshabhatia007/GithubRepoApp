package com.example.repo.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.repo.R
import com.example.repo.extensions.injectViewModel
import com.example.repo.githubapi.Resource
import com.example.repo.githubapi.TrendingRepo
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() , HasSupportFragmentInjector {

    companion object {
        val TAG = "MainActivity"
    }

    lateinit var repoAdapter: RVAdapter<TrendingRepo,RepoVH>
    private var trendingRepos = ArrayList<TrendingRepo>()

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var coroutineScope: CoroutineScope

    private lateinit var mainVM: MainVM

    private var selectedItem = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainVM = injectViewModel(viewModelFactory)

        getTrendingRepo()
    }

    private fun getTrendingRepo() {
        mainVM.trendingRepos.observe(this, Observer {
                result ->
            when(result.status) {
                Resource.Status.SUCCESS -> {
                    Log.e(TAG,"success  ${result.data}  ")

                    CoroutineScope(Dispatchers.Main).launch {
                        result.data?.let {
                            trendingRepos.clear()
                            trendingRepos.addAll(it)
                            updateRepoList()
                        }
                    }

                }
                Resource.Status.ERROR ,
                Resource.Status.LOADING ->  {
                    Log.e(TAG," ${result.message}  ")
                }
            }
        })
    }

    private fun updateRepoList() {
        repoAdapter = RVAdapter(this,
            R.layout.rv_repo_item,
            {
                RepoVH(it)
            },trendingRepos, {
                    holder, repo, position ->
                holder.bind(repo,selectedItem) {
                    val oldPosition = selectedItem

                    if(selectedItem == position) {
                        selectedItem = -1
                    } else {
                        selectedItem = position
                        repoAdapter.notifyItemChanged(selectedItem)
                    }
                    repoAdapter.notifyItemChanged(oldPosition)



                }
            }
        )

        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = repoAdapter
        repoAdapter.notifyDataSetChanged()

    }

}