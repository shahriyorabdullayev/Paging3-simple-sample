package me.shakhriyor.paging3.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import me.shakhriyor.paging3.data.remote.ApiService
import me.shakhriyor.paging3.data.source.GithubPagingSource

class RepoListRepository(
    private val apiService: ApiService
) {

    fun fetchRepos(userName: String) = Pager(
        pagingSourceFactory = {GithubPagingSource(apiService, userName)},
        config = PagingConfig(10)
    ).flow
}