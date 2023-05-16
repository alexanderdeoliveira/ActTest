package com.alexander.acttest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.databinding.ItemRepositoryBinding

class RepositoryListAdapter: RecyclerView.Adapter<RepositoryViewHolder>() {

    private var repositoryList: MutableList<RepositoryModel> = mutableListOf()

    fun setRepositoryList(repositoryList:List<RepositoryModel>) {
        this.repositoryList.addAll(repositoryList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositoryList[position])
    }
}