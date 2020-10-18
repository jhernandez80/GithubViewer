package com.livehappyapps.githubviewer.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.livehappyapps.githubviewer.Config
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.SettingsKey
import com.livehappyapps.githubviewer.data.Resource
import com.livehappyapps.githubviewer.databinding.ActivityMainBinding
import com.livehappyapps.githubviewer.ui.adapter.RepoAdapter
import com.livehappyapps.githubviewer.ui.viewmodel.MainViewModel
import com.livehappyapps.githubviewer.utils.setTextOrHide
import com.livehappyapps.githubviewer.utils.toastShort
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val preferences: SharedPreferences by inject()
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.swipeRefresh.apply {
            setColorSchemeColors(ContextCompat.getColor(context, R.color.accent))
            setOnRefreshListener {
                val org = preferences.getString(SettingsKey.ORGANIZATION, Config.DEFAULT_ORG)!!
                viewModel.updateOrganization(org)
                viewModel.updateRepos(org)
            }
        }

        val repoAdapter = RepoAdapter { owner, repo ->
            startActivity(IssueActivity.newInstance(this, owner, repo))
        }
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.organization.observe(this, Observer { resource ->
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
                    toastShort(getString(R.string.unfortunately_an_error_occurred))
                }
            }
        })
        viewModel.repos.observe(this, Observer { resource ->
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
                    toastShort(getString(R.string.unfortunately_an_error_occurred))
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startActivity(SettingsActivity.newInstance(this))
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName
    }
}