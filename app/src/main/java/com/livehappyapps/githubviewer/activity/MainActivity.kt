package com.livehappyapps.githubviewer.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.livehappyapps.githubviewer.adapter.RepositoryAdapter
import com.livehappyapps.githubviewer.databinding.ActivityMainBinding
import com.livehappyapps.githubviewer.viewmodel.MainViewModel

/* TODO:
 * Move data loading into a ViewModel
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

        viewModel.getRepositories().observe(this, Observer { repos ->
            repoAdapter.repositories = repos
        })
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}