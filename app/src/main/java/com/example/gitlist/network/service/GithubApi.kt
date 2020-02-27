package com.example.gitlist.network.service

import com.example.gitlist.network.dto.GithubSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    /**
     * Metodo encapsulado de busca de repositorios no github.
     *
     * @param query A query da busca.
     * @param sort A ordenacao da busca.
     * @return A instancia da chamada da requisicao.
     */
    @GET("search/repositories")
    fun listRepositories(@Query("q") query: String, @Query("sort") sort: String?) : Call<GithubSearchResponse>

}