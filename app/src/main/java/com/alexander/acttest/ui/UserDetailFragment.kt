package com.alexander.acttest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.alexander.acttest.R
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.databinding.FragmentDetailUserBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment: Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private lateinit var repositoryAdapter: RepositoryListAdapter

    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
        getRepositoryList()
    }

    private fun setActionBar() {
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupUI() {
        setActionBar()
        binding.user = args.user
        loadImage(args.user.avatarUrl)
        setupListAdapter()
    }

    private fun setupObservers() {
        userDetailViewModel.repositoryListResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResponseStatus.Loading -> showLoading(response.showLoading)
                is ResponseStatus.Success -> {
                    showLoading(false)
                    repositoryAdapter.setRepositoryList(response.value)
                }
                is ResponseStatus.Failure -> {
                    showLoading(false)
                    Toast.makeText(context, context?.getString(R.string.get_repositories_failure), Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }

    private fun setupListAdapter() {
        binding.apply {
            rvRepositories.apply {
                repositoryAdapter = RepositoryListAdapter()
                adapter = repositoryAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            }
        }
    }

    private fun showLoading(showLoading: Boolean) {
        binding.pbLoading.isVisible = showLoading
    }

    private fun getRepositoryList() {
        userDetailViewModel.getRepositoriesByUser(args.user.login)
    }

    private fun loadImage(urlImage: String) {
        Glide.with(binding.ivImage)
            .load(urlImage)
            .into(binding.ivImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}