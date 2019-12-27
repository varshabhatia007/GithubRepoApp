package com.example.repo.ui

import androidx.lifecycle.ViewModel
import com.example.repo.repo.TrendingRepoRepository
import javax.inject.Inject

class MainVM  @Inject constructor(private val trendingRepoRepository: TrendingRepoRepository) : ViewModel() {
    val trendingRepos by lazy { trendingRepoRepository.getTrendingRepo() }

}