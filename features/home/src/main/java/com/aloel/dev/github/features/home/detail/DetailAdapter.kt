package com.aloel.dev.github.features.home.detail

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aloel.dev.github.core.extensions.dpToPixel
import com.aloel.dev.github.core.models.Repository
import com.aloel.dev.github.core.models.User
import com.aloel.dev.github.features.home.databinding.ItemRepositoryBinding
import com.aloel.dev.github.features.home.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

class DetailAdapter(
    var list: ArrayList<Repository> = arrayListOf(),
    val onItemClick: (Repository) -> Unit = { }
): RecyclerView.Adapter<DetailAdapter.RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class RepositoryViewHolder(
        private val binding: ItemRepositoryBinding,
        private val onItemClick: (Repository) -> Unit
    ): ViewHolder(binding.root) {

        fun bind(repository: Repository) = with(binding) {
            tvName.text = repository.full_name
            tvDesc.text = repository.description ?: "-"
            tvLanguage.text = repository.language ?: "-"
            tvStar.text = repository.watchers_count.toString()

            root.setOnClickListener {
                onItemClick(repository)
            }
        }
    }
}