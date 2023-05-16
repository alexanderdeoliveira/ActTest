package com.alexander.acttest.ui

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.databinding.ItemUserBinding
import com.bumptech.glide.Glide

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val onClick: (UserModel) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserModel) {
        binding.apply {
            this.user = user
            root.setOnClickListener {
                onClick.invoke(user)
            }
            loadImage(ivImage, user.avatarUrl)
        }
    }

    private fun loadImage(view: ImageView, urlImage: String) {
        Glide.with(view)
            .load(urlImage)
            .circleCrop()
            .into(view)
    }
}