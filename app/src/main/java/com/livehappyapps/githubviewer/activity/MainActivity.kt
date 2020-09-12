package com.livehappyapps.githubviewer.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.livehappyapps.githubviewer.adapter.RepositoryAdapter
import com.livehappyapps.githubviewer.databinding.ActivityMainBinding
import com.livehappyapps.githubviewer.network.Resource
import com.livehappyapps.githubviewer.viewmodel.MainViewModel

/* TODO:
 * Incorporate Issue Activity (Fragment with ViewPager2)
 *
 * Implement Caching
 * Handle Paging & load more
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repoAdapter = RepositoryAdapter()
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.getRepositories().observe(this, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    repoAdapter.repositories = resource.data!!
                }
                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progress.visibility = View.GONE
                    Log.d(TAG, resource.message!!)
                }
            }
        })
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}