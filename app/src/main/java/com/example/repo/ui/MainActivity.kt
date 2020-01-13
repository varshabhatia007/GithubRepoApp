package com.example.repo.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.repo.R
import com.example.repo.extensions.injectViewModel
import com.example.repo.githubapi.Resource
import com.example.repo.githubapi.TrendingRepo
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loading_error.*
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector,
    SearchView.OnQueryTextListener {

    companion object {
        val TAG = "MainActivity"
    }

    lateinit var repoAdapter: RVAdapter<TrendingRepo, RepoVH>
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

        updateRepoList()

        getTrendingRepo()

        srlList.setOnRefreshListener {
            srlList.isRefreshing = true
            getTrendingRepo(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {

            R.id.sortByName -> {
                trendingRepos.sortBy { it.name }
                repoAdapter.notifyDataSetChanged()
                true
            }
            R.id.sortByWeekly -> {
                getTrendingRepo(true, "weekly")
                return true
            }
            R.id.sortByMonthly -> {
                getTrendingRepo(true, "monthly")
                return true
            }

            R.id.search -> {
                val searchView = MenuItemCompat.getActionView(item) as SearchView
                searchView.setOnQueryTextListener(this)
                getListener(item)

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getListener(items: MenuItem) {
        items.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                repoAdapter.updateItems(trendingRepos)
                return true
            }
        })
    }

    private fun getTrendingRepo(forceFetch: Boolean = false, sortByData: String = "daily") {
        mainVM.getTrendingRepos(forceFetch, sortByData)?.observe(this, Observer { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    Log.e(TAG, "success  ${result.data?.size}  ")


                    result.data?.let {
                        if (it.isNotEmpty()) {
                            showUI(result.status)
                            trendingRepos.clear()
                            trendingRepos.addAll(it)

                            repoAdapter.updateItems(trendingRepos)
                        }
                    }

                }
                Resource.Status.ERROR -> {
                    if (trendingRepos.isEmpty()) {
                        showUI(result.status)
                        btnRetry.setOnClickListener {
                            getTrendingRepo(true, sortByData)
                        }
                    }
                }
                Resource.Status.LOADING -> {
                    Log.e(TAG, "loading ${result.message}  ")
                    showUI(result.status)
                }
            }
        })
    }

    private fun updateRepoList() {
        repoAdapter = RVAdapter(
            context = this,
            layoutId = R.layout.rv_repo_item,
            holderMaker = { RepoVH(it) },
            mutableList = trendingRepos,
            binder = { holder, repo, position ->
                holder.bind(repo, selectedItem) {
                    val oldPosition = selectedItem

                    if (selectedItem == position) {
                        selectedItem = -1
                    } else {
                        selectedItem = position
                        repoAdapter.notifyItemChanged(selectedItem)
                    }
                    repoAdapter.notifyItemChanged(oldPosition)
                }
            }
        )

        rvList.adapter = repoAdapter
        repoAdapter.notifyDataSetChanged()
    }


    private fun showUI(status: Resource.Status) {
        layoutError.visibility = if (status == Resource.Status.ERROR) View.VISIBLE else View.GONE
        srlList.visibility = if (status == Resource.Status.SUCCESS) View.VISIBLE else View.GONE
        loadingView.visibility = if (status == Resource.Status.LOADING) View.VISIBLE else View.GONE
        if (status == Resource.Status.LOADING)
            loadingView.startShimmerAnimation()
        else {
            loadingView.stopShimmerAnimation()
            srlList.isRefreshing = false
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        val filteredModelList: MutableList<TrendingRepo> = filter(
            trendingRepos.toMutableList(),
            newText.toString()
        )
        repoAdapter.setSearchResult(filteredModelList)
        return true
    }

    private fun filter(
        models: List<TrendingRepo>,
        query: String
    ): MutableList<TrendingRepo> {
        var query = query
        query = query.toLowerCase()
        val filteredModelList: MutableList<TrendingRepo> = java.util.ArrayList<TrendingRepo>()
        for (model in models) {
            val text: String = model.name!!.toLowerCase()
            if (text.contains(query)) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }

}