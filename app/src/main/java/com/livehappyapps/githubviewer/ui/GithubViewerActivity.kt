package com.livehappyapps.githubviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.navOptions
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.databinding.ActivityGithubViewerBinding
import com.livehappyapps.githubviewer.ui.main.MainFragment

class GithubViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_viewer)
    }
}