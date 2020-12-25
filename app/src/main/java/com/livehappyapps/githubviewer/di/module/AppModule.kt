package com.livehappyapps.githubviewer.di.module

import androidx.preference.PreferenceManager
import com.livehappyapps.githubviewer.data.IssueRepository
import com.livehappyapps.githubviewer.data.MainRepository
import com.livehappyapps.githubviewer.data.local.GithubDatabase
import com.livehappyapps.githubviewer.data.remote.api.GithubApi
import com.livehappyapps.githubviewer.ui.issues.IssueViewModel
import com.livehappyapps.githubviewer.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single { GithubApi() }
    single { GithubDatabase.getDatabase(androidContext()) }
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }

    single { MainRepository(database = get(), api = get()) }
    single { IssueRepository(database = get(), api = get()) }

    viewModel { MainViewModel(repository = get(), preferences = get()) }
    viewModel { (owner: String, repo: String, state: String) ->
        IssueViewModel(owner, repo, state, get())
    }
}