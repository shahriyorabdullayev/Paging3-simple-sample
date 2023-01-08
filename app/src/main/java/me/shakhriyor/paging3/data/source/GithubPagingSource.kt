package me.shakhriyor.paging3.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.shakhriyor.paging3.data.model.GithubRepository
import me.shakhriyor.paging3.data.remote.ApiService


private const val FIRST_PAGE = 1;

class GithubPagingSource(
    private val api: ApiService,
    private val username: String,
) : PagingSource<Int, GithubRepository>() {

    override fun getRefreshKey(state: PagingState<Int, GithubRepository>): Int? {
        return state.anchorPosition?.let { anchorposition ->
            state.closestPageToPosition(anchorposition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorposition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = api.fetchRepos(username, page, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}