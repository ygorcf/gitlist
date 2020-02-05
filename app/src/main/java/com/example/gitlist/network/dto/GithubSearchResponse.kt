package com.example.gitlist.network.dto

import com.example.gitlist.model.GithubRepository
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class GithubSearchResponse {

    val items = ArrayList<GithubResult>()

    @JsonIgnoreProperties(ignoreUnknown = true)
    class GithubResult {

        val name = ""
        val owner = GithubOwner()

        val githubRepository: GithubRepository
            get() = GithubRepository().apply {
                user = owner.login
                repository = name
            }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class GithubOwner {

        val login = ""

    }

}