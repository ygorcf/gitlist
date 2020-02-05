package com.example.gitlist.app.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitlist.R
import com.example.gitlist.app.listAdapter.holder.GithubRepositoryItemHolder
import com.example.gitlist.model.GithubRepository

class GithubRepositoryAdapter(
    private val list: List<GithubRepository>
) : RecyclerView.Adapter<GithubRepositoryItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepositoryItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_github_repository, parent, false)
        return GithubRepositoryItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GithubRepositoryItemHolder, position: Int) {
        val item = list.getOrNull(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}