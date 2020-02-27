package com.example.gitlist.app.activity.githubRepositoryList

import android.util.Log
import com.example.gitlist.network.dto.GithubSearchResponse
import com.example.gitlist.network.service.GithubApi
import com.example.gitlist.network.service.ServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Classe responsavel por manter os dados da tela de listagem de repositorios.
 *
 * @property filter O valor atual do filtro de repositorios.
 * @property sort O valor atual do campo de ordenacao.
 */
class GithubRepositoryListModel(defaultFilter: String) : GithubRepositoryListMVP.Model {

    override var filter = defaultFilter
    override var sort: String? = null

    /**
     * Metodo para obter a lista de repositorios.
     *
     * @param listener O ovutinte de quando terminar a busca.
     */
    override fun getRepositories(listener: GithubRepositoryListMVP.Model.RepositoryListListener) {
        val service = ServiceFactory.createService(GithubApi::class.java)
        service.listRepositories(filter, sort).enqueue(object :
            Callback<GithubSearchResponse> {
            override fun onFailure(call: Call<GithubSearchResponse>, t: Throwable) {
                val e = Exception("deu ruim")
                Log.i("asd", e.message, t)
                listener.onError(e)
            }

            override fun onResponse(
                call: Call<GithubSearchResponse>,
                response: Response<GithubSearchResponse>
            ) {
                val repositoryList = response.body()?.items?.map { it.githubRepository }

                if (repositoryList != null) {
                    listener.onSuccess(repositoryList)
                } else {
                    listener.onError(Exception("lista vazia"))
                }
            }
        })
    }

}