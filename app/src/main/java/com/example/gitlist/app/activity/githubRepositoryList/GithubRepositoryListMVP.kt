package com.example.gitlist.app.activity.githubRepositoryList

import com.example.gitlist.model.GithubRepository

/**
 * Interface de comunicacao entre as classes do MVP.
 */
interface GithubRepositoryListMVP {

    /**
     * Interface das classes de manter os dados da activity de listar os repositorios do github.
     */
    interface Model {

        /**
         * Interface de ouvinte do metodo de obter a lista de respositorios.
         */
        interface RepositoryListListener {

            /**
             * Metodo de callback de quando a lista foi obtida com sucesso.
             *
             * @param repositories A lista de repositorios obtida.
             */
            fun onSuccess(repositories: List<GithubRepository>)

            /**
             * Metodo de callback de quando ocorrer um erro ao buscar a lista de repositorios.
             *
             * @param error O erro que ocorreu ao buscar a lista de repositorios.
             */
            fun onError(error: Exception)

        }

        /**
         * Metodo de obter a lista de repositorios do servidor.
         *
         * @param listener O ouvinte dabusca de repositorios.
         */
        fun getRepositories(listener: RepositoryListListener)

    }

    /**
     * Interface das classes de apresentar os dados na tela da activity de listar repositorios.
     *
     * @property filterVisibility A visibilidade dos campos de filtro.
     */
    interface View {

        var filterVisibility: Boolean

        /**
         * Metodo de comando para apresentar a lista de repositorios do github.
         *
         * @param repositories A lista de repositorios a ser apresentada.
         */
        fun showRepositories(repositories: List<GithubRepository>)

    }

    /**
     * Interface das classes de regras de negocio da activity de listar repositorios do github.
     *
     * @property view A instancia da classe de apresentar os dados.
     */
    interface Presenter {

        val view: View

        /**
         * Metodo de callback de quando a activity foi criada.
         */
        fun onActivityCreated()

        /**
         * Metodo de callback de quando for clicado no botao de filtro.
         */
        fun onFilterButtonClicked()

    }

}