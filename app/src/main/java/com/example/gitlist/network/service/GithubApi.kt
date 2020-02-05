package com.example.gitlist.network.service

import com.example.gitlist.network.dto.GithubSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun listRepositories(@Query("q") query: String, @Query("sort") sort: String) : Call<GithubSearchResponse>

}