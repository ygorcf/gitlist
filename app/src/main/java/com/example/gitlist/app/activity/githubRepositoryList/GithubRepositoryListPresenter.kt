package com.example.gitlist.app.activity.githubRepositoryList

import com.example.gitlist.model.GithubRepository

class GithubRepositoryListPresenter : GithubRepositoryListMVP.Presenter {

    private val model: GithubRepositoryListMVP.Model = GithubRepositoryListModel()
    override lateinit var view: GithubRepositoryListMVP.View

    override fun onActivityCreated() {
        model.getRepositories(object : GithubRepositoryListMVP.Model.RepositoryListListener {
            override fun onSuccess(repositories: List<GithubRepository>) {
                view.showRepositories(repositories)
            }

            override fun onError(error: Exception) {
                view.showRepositories(listOf())
            }

        })
    }

    override fun onFilterButtonClicked() {
        view.filterVisibility = !view.filterVisibility
    }

}