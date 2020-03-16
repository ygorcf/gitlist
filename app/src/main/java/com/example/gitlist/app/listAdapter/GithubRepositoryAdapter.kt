package com.example.gitlist.app.listAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitlist.R
import com.example.gitlist.app.listAdapter.holder.GithubRepositoryItemHolder
import com.example.gitlist.app.listAdapter.listener.EndlessRecyclerViewListener
import com.example.gitlist.model.GithubRepository

/**
 * Classe responsavel pela logica de apresentacao de uma lista de Repositorios.
 *
 * @property layoutManager O layout manager da recycler view.
 */
class GithubRepositoryAdapter(
    private val list: List<GithubRepository>,
    private val listener: EndlessRecyclerViewListener?
) : RecyclerView.Adapter<GithubRepositoryItemHolder>() {

    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepositoryItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_github_repository, parent, false)
        val holder = GithubRepositoryItemHolder(view)

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(parent.context, R.anim.item_animation))

        return holder
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

    fun bindToListView (recyclerView: RecyclerView, context: Context) {
        layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = this
        recyclerView.layoutManager = layoutManager
        if (listener !== null) {
            recyclerView.addOnScrollListener(listener)
        }
    }

}