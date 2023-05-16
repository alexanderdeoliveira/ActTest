package com.alexander.acttest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexander.acttest.R
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.databinding.BottomSheetSearchBinding
import com.alexander.acttest.databinding.FragmentListUserBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment: Fragment() {

    private var _binding: FragmentListUserBinding? = null
    private val binding get() = _binding!!

    private val usersListViewModel: UserListViewModel by viewModel()
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        getUserList()
    }

    private fun setupUI() {
        setupMenu()
        setupListAdapter()
    }

    private fun setupListAdapter() {
        binding.apply {
            rvUsers.apply {
                userListAdapter = UserListAdapter {
                    goToUserDetail(it)
                }
                adapter = userListAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            }
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_search -> {
                        showSearchBottomSheet()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupObservers() {
        usersListViewModel.apply {
            userListResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is ResponseStatus.Loading -> showLoading(response.showLoading)
                    is ResponseStatus.Success -> {
                        showLoading(false)
                        userListAdapter.setUserList(response.value)
                    }
                    is ResponseStatus.Failure -> {
                        showLoading(false)
                        Toast.makeText(context, context?.getString(R.string.get_users_failure), Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
            }

            userFetchedResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is ResponseStatus.Loading -> showLoading(response.showLoading)
                    is ResponseStatus.Success -> {
                        showLoading(false)
                        goToUserDetail(response.value)
                    }
                    is ResponseStatus.Failure -> {
                        showLoading(false)
                        Toast.makeText(context, context?.getString(R.string.get_user_failure), Toast.LENGTH_LONG).show()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun showSearchBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val binding = BottomSheetSearchBinding.inflate(layoutInflater).apply {
            btSearch.setOnClickListener {
                usersListViewModel.getUserByUsername(etName.text.toString())
                dialog.dismiss()
            }
            usersListViewModel.searchButtonEnabled.observe(viewLifecycleOwner) {
                btSearch.isEnabled = it
            }
        }
        dialog.setCancelable(true)
        dialog.setContentView(binding.root)
        dialog.show()
    }

    private fun showLoading(showLoading: Boolean) {
        binding.pbLoading.isVisible = showLoading
    }

    private fun getUserList() {
       usersListViewModel.getUsers()
    }

    private fun goToUserDetail(user: UserModel) {
        findNavController().navigate(
            UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
                user
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}