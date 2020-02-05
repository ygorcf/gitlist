package com.example.gitlist.app.listAdapter.holder

import android.graphics.BitmapFactory
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gitlist.R
import com.example.gitlist.model.GithubRepository
import kotlinx.android.synthetic.main.item_github_repository.view.*
import java.net.URL

class GithubRepositoryItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: GithubRepository) {
        if (item.image.isNotEmpty()) {
            val imageUrl = URL(item.image)
            val image = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
            itemView.githubRepositoryItemImage?.setImageBitmap(image)
        }

        itemView.githubRepositoryItemUser?.text = item.user
        itemView.githubRepositoryItemRepository?.text = item.repository
        itemView.githubRepositoryItemStars?.text = itemView.context.getString(R.string.github_repository_item_stars_title, item.stars)
    }

}