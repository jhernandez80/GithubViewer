package com.livehappyapps.githubviewer.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.livehappyapps.githubviewer.Config
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.SettingsKey
import com.livehappyapps.githubviewer.data.Resource
import com.livehappyapps.githubviewer.databinding.FragmentMainBinding
import com.livehappyapps.githubviewer.utils.setTextOrHide
import com.livehappyapps.githubviewer.utils.toastShort
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val navController by lazy { findNavController() }
    private val preferences: SharedPreferences by inject()
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeToolbar()

        binding.swipeRefresh.apply {
            setColorSchemeColors(ContextCompat.getColor(context, R.color.accent))
            setOnRefreshListener {
                val org = preferences.getString(SettingsKey.ORGANIZATION, Config.DEFAULT_ORG)!!
                viewModel.updateOrganization(org)
                viewModel.updateRepos(org)
            }
        }

        val repoAdapter = RepoAdapter { owner, repo ->
            navController.navigate(MainFragmentDirections.toIssueContainer(owner, repo))
        }
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.organization.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val org = resource.data
                    binding.name.text = org.name
                    binding.description.setTextOrHide(org.description)
                    binding.locationIcon.isVisible = !org.location.isNullOrEmpty()
                    binding.location.setTextOrHide(org.location)
                    binding.linkIcon.isVisible = !org.blog.isNullOrEmpty()
                    binding.link.setTextOrHide(org.blog)
                    Glide.with(this)
                        .load(org.avatarUrl)
                        .transform(RoundedCorners(16))
                        .into(binding.avatar)
                }
                is Resource.Error -> {
                    Log.d(TAG, resource.message)
                    requireContext().toastShort(getString(R.string.unfortunately_an_error_occurred))
                }
            }
        }
        viewModel.repos.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progress.isVisible = !binding.swipeRefresh.isRefreshing
                }
                is Resource.Success -> {
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    repoAdapter.repos = resource.data
                }
                is Resource.Error -> {
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    Log.d(TAG, resource.message)
                    requireContext().toastShort(getString(R.string.unfortunately_an_error_occurred))
                }
            }
        }
    }

    private fun initializeToolbar() {
        binding.toolbar.inflateMenu(R.menu.main)
        binding.toolbar.setTitle(R.string.app_name)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    navController.navigate(MainFragmentDirections.toSettings())
                    true
                }
                else -> false
            }
        }
    }

    companion object {

        private val TAG = MainFragment::class.java.simpleName
    }
}