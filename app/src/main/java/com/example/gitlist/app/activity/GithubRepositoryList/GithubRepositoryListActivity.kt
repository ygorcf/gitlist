package com.example.gitlist.app.activity.GithubRepositoryList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitlist.R
import com.example.gitlist.app.listAdapter.GithubRepositoryAdapter
import com.example.gitlist.network.dto.GithubSearchResponse
import com.example.gitlist.network.service.GithubApi
import com.example.gitlist.network.service.ServiceFactory
import kotlinx.android.synthetic.main.activity_github_repository_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepositoryListActivity : AppCompatActivity() {

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
                    githubRepositoryList?.adapter = adapter
                    githubRepositoryList?.layoutManager = LinearLayoutManager(context)
                }
            }

        })
    }
}
