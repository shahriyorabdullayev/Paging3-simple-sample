package me.shakhriyor.paging3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import me.shakhriyor.paging3.data.model.GithubRepository
import me.shakhriyor.paging3.data.remote.ApiClient
import me.shakhriyor.paging3.domain.RepoListRepository

class RepoListViewModel: ViewModel() {

    private var repository: RepoListRepository = RepoListRepository(ApiClient.getNetworkApi())
    private var currentUserName: String? = null
    private var currentSearchResult: Flow<PagingData<GithubRepository>>? = null

    fun searchRepos(username: String): Flow<PagingData<GithubRepository>> {
        val lastResult = currentSearchResult
        if (username == currentUserName && lastResult != null) {
            return lastResult
        }
        currentUserName = username
        val newResult = repository.fetchRepos(username)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}