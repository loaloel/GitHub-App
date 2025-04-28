package com.aloel.dev.github.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aloel.dev.github.core.extensions.dpToPixel
import com.aloel.dev.github.core.models.User
import com.aloel.dev.github.features.home.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

class HomeAdapter(
    var list: ArrayList<User> = arrayListOf(),
    val onItemClick: (User, ImageView) -> Unit = { user, imageView -> }
): RecyclerView.Adapter<HomeAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class UserViewHolder(
        private val binding: ItemUserBinding,
        private val onItemClick: (User, ImageView) -> Unit = { user, imageView -> }
    ): ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {
            tvUser.text = user.login
            ivUser.transitionName = user.avatar_url
            Glide.with(binding.root.context)
                .load(user.avatar_url)
                .apply(
                    RequestOptions().transforms(
                        RoundedCorners(8.dpToPixel(root.context)),
                    )
                )
                .into(ivUser)

            root.setOnClickListener {
                onItemClick(user, ivUser)
            }
        }
    }
}