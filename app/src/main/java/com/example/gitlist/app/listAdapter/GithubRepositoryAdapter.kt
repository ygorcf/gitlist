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
class GithubRepositoryAdapter : RecyclerView.Adapter<GithubRepositoryItemHolder>() {

    private val list = ArrayList<GithubRepository>()
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

    fun bindToListView (recyclerView: RecyclerView, context: Context, infinityScrollListener: (page: Int, recyclerView: RecyclerView) -> Unit) {
        layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = this
        recyclerView.layoutManager = layoutManager
        recyclerView.addOnScrollListener(object : EndlessRecyclerViewListener(layoutManager!! as LinearLayoutManager) {
            override fun onLoadMore(page: Int, recyclerView: RecyclerView) {
                infinityScrollListener(page, recyclerView)
            }
        })
    }

    /**
     * Metodo de comando, com o objetivo de adicionar repositorios na lista.
     * @param repositories - Os repositorioes a serem adicionados.
     */
    fun addRepositories (repositories: Collection<GithubRepository>) {
        this.list.addAll(repositories)
        this.notifyDataSetChanged()
    }

}