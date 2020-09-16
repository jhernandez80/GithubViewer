package com.livehappyapps.githubviewer.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.livehappyapps.githubviewer.Config
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.SettingsKey
import com.livehappyapps.githubviewer.adapter.RepositoryAdapter
import com.livehappyapps.githubviewer.databinding.ActivityMainBinding
import com.livehappyapps.githubviewer.network.Resource
import com.livehappyapps.githubviewer.viewmodel.MainViewModel
import com.livehappyapps.githubviewer.viewmodel.MainViewModelFactory

/* TODO:
 * Implement Caching (Add a repository)
 * Handle Paging & load more
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        binding.swipeRefresh.apply {
            setColorSchemeColors(ContextCompat.getColor(context, R.color.accent))
            setOnRefreshListener {
                viewModel.fetchRepositories(
                    preferences.getString(SettingsKey.ORGANIZATION, Config.DEFAULT_ORG)!!
                )
            }
        }

        val repoAdapter = RepositoryAdapter { owner, repo ->
            startActivity(IssueActivity.newInstance(this, owner, repo))
        }
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.repositories.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progress.isVisible = !binding.swipeRefresh.isRefreshing
                }
                is Resource.Success -> {
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    repoAdapter.repositories = resource.data
                }
                is Resource.Error -> {
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    Log.d(TAG, resource.message)
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