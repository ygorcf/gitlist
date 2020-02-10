package com.example.gitlist.app.activity.githubRepositoryList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitlist.R
import com.example.gitlist.app.listAdapter.GithubRepositoryAdapter
import com.example.gitlist.model.GithubRepository
import com.example.gitlist.network.dto.GithubSearchResponse
import com.example.gitlist.network.service.GithubApi
import com.example.gitlist.network.service.ServiceFactory
import kotlinx.android.synthetic.main.activity_github_repository_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Classe de apresentar os dados da activity de listar repositorios.
 *
 * @property filterVisibility A visibilidade dos campos de filtro.
 */
class GithubRepositoryListActivity : AppCompatActivity(), GithubRepositoryListMVP.View {

    override var filterVisibility: Boolean
        get() = layout_filter?.visibility == View.VISIBLE
        set(value) {
            layout_filter?.visibility = if (value) View.VISIBLE else View.GONE
        }

    /**
     * Metodo para apresentar a lista de repositorios.
     *
     * @param repositories A lista de repositorios a serem apresentados.
     */
    override fun showRepositories(repositories: List<GithubRepository>) {

    }

    // Metodos do ciclo de vida do android.

    /**
     * Metodo do ciclo de vida do android de quando foi criada a activity.
     *
     * @param savedInstanceState Os dados de estado salvos da ultima instancia.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repository_list)
        val service = ServiceFactory.createService(GithubApi::class.java)
        val context = this
        service.listRepositories("language:swift", "stars").enqueue(object : Callback<GithubSearchResponse> {
            override fun onFailure(call: Call<GithubSearchResponse>, t: Throwable) {
                Log.i("asd", "deu ruim", t)
            }

            override fun onResponse(
                call: Call<GithubSearchResponse>,
                response: Response<GithubSearchResponse>
            ) {
                val respositoryList = response.body()?.items?.map { it.githubRepository }

                if (respositoryList != null) {
                    val adapter = GithubRepositoryAdapter(respositoryList)
                    github_repository_list?.adapter = adapter
                    github_repository_list?.layoutManager = LinearLayoutManager(context)
                }
            }

        })
    }
}
