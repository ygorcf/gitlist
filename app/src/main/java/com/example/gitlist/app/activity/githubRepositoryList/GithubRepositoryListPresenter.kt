package com.example.gitlist.app.activity.githubRepositoryList

import com.example.gitlist.model.GithubRepository
import java.util.*

/**
 * Classe responsavel pelas regras de negocio da tela de listagem de repositorios do github.
 *
 * @property model A instancia da classe responsavel por manter os dados da tela.
 * @property timer Um timer para realizar o debounce para as requisicoes de buscar repositorios.
 * @property view A instancia da classe responsavel por apresentar os dados na tela.
 */
class GithubRepositoryListPresenter : GithubRepositoryListMVP.Presenter {

    private val model: GithubRepositoryListMVP.Model = GithubRepositoryListModel(DEFAULT_FILTER)
    private var timer: Timer? = null

    override lateinit var view: GithubRepositoryListMVP.View

    /**
     * Metodo de callback de quando a activity foi criada. Com o objetivo de definir o filtro
     * inicial e buscar os repositorios.
     */
    override fun onActivityCreated() {
        view.setStartFilter(DEFAULT_FILTER)
        getRepositories()
    }

    /**
     * Metodo de callback de quando foi apertado no botao de filtro. Com o objetivo de mostrar ou
     * esconder os campos de filtro.
     */
    override fun onFilterButtonClicked() {
        view.filterVisibility = !view.filterVisibility
    }

    /**
     * Metodo de callback de quando o texto do campo de filtro e alterado. Com o objetivo de buscar
     * os novos repositorios.
     *
     * @param filter O novo valor do campo de filtro.
     */
    override fun onFilterTextChange(filter: String) {
        model.filter = if (filter.isBlank()) DEFAULT_FILTER else filter
        getRepositoriesWithDebounce()
    }

    /**
     * Metodo de callback de quando o valor do campo de ordenacao foi alterado. Com o objetivo de
     * buscar novos repositorios.
     *
     * @param sort O novo valor do campo de ordenacao.
     */
    override fun onSortTextChange(sort: String?) {
        model.sort = sort
        getRepositoriesWithDebounce()
    }

    /**
     * Metodo para buscar os repositorios e apresenta-los na tela, com um debounce 0,5s.
     */
    private fun getRepositoriesWithDebounce() {
        timer?.cancel()
        timer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    getRepositories()
                }
            }, 500)
        }
    }

    /**
     * Metodo de buscar os repositorios e apresenta-los na tela.
     */
    private fun getRepositories() {
        model.getRepositories(object : GithubRepositoryListMVP.Model.RepositoryListListener {
            override fun onSuccess(repositories: List<GithubRepository>) {
                view.showRepositories(repositories)
            }

            override fun onError(error: Exception) {
                view.showRepositories(listOf())
            }
        })
    }

    companion object {
        /**
         * Valor base do campo de filtro.
         */
        private const val DEFAULT_FILTER = "language:javascript"
    }

}