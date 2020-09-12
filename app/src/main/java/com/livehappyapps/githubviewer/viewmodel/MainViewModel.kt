package com.livehappyapps.githubviewer.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.livehappyapps.githubviewer.model.Repository
import com.livehappyapps.githubviewer.network.GithubRetrofitHelper
import com.livehappyapps.githubviewer.utils.async
import io.reactivex.disposables.CompositeDisposable


class MainViewModel(
    private val githubHelper:GithubRetrofitHelper = GithubRetrofitHelper()
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val repositories: MutableLiveData<List<Repository>> by lazy {
        MutableLiveData<List<Repository>>().also { fetchRepositories() }
    }

    fun getRepositories(): LiveData<List<Repository>> = repositories

    private fun fetchRepositories() {
        // TODO: Calling the API should be handled by the Repository
        val repoSubscription = githubHelper.getRepositories("intuit")
            .async()
            .subscribe({ repos ->
                repositories.value = repos
            }, { error ->
                Log.d(TAG, "Error: ${error.message}")
            })
        compositeDisposable.add(repoSubscription)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}