package me.shakhriyor.paging3.data.remote

import me.shakhriyor.paging3.data.model.GithubRepository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users/{username}/repos")
    suspend fun fetchRepos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): List<GithubRepository>
}