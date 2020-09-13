package com.livehappyapps.githubviewer.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.livehappyapps.githubviewer.adapter.IssuePagerAdapter
import com.livehappyapps.githubviewer.databinding.ActivityIssueBinding


class IssueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIssueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIssueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f

        val owner = intent.getStringExtra(EXTRA_OWNER)
        val repo = intent.getStringExtra(EXTRA_REPO)
        val pagerAdapter = IssuePagerAdapter(this, owner, repo)
        binding.pager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = pagerAdapter.tabs[position].name
        }.attach()
    }

    companion object {
        private const val EXTRA_OWNER = "extra_owner"
        private const val EXTRA_REPO = "extra_repo"

        fun newInstance(
            context: Context,
            owner: String?,
            repo: String?
        ) = Intent(context, IssueActivity::class.java).apply {
            putExtra(EXTRA_OWNER, owner)
            putExtra(EXTRA_REPO, repo)
        }
    }
}