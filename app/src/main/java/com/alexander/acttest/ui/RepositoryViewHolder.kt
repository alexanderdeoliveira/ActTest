package com.alexander.acttest.ui

import androidx.recyclerview.widget.RecyclerView
import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.databinding.ItemRepositoryBinding

class RepositoryViewHolder(
    private val binding: ItemRepositoryBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bind(repository: RepositoryModel) {
        binding.repository = repository
    }
}