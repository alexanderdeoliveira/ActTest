package com.alexander.acttest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.databinding.ItemUserBinding

class UserListAdapter(
    private val userList: MutableList<UserModel> = mutableListOf(),
    private val onClick: (UserModel) -> Unit
): RecyclerView.Adapter<UserViewHolder>() {

    fun setUserList(userList: List<UserModel>) {
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context)), onClick)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }
}