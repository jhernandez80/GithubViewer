package com.livehappyapps.githubviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.livehappyapps.githubviewer.model.Repository
import com.livehappyapps.githubviewer.network.GithubRetrofitHelper
import com.livehappyapps.githubviewer.network.Resource
import com.livehappyapps.githubviewer.utils.async
import io.reactivex.disposables.CompositeDisposable


class MainViewModel(
    private val githubHelper:GithubRetrofitHelper = GithubRetrofitHelper()
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val repositories: MutableLiveData<Resource<List<Repository>>> by lazy {
        MutableLiveData<Resource<List<Repository>>>()
    }

    init {
        fetchRepositories()
    }

    fun getRepositories(): LiveData<Resource<List<Repository>>> = repositories

    private fun fetchRepositories() {
        // TODO: Calling the API should be handled by the Repository
        repositories.value = Resource.Loading()
        val repoSubscription = githubHelper.getRepositories("intuit")
            .async()
            .subscribe({ repos ->
                repositories.value = Resource.Success(repos)
            }, { error ->
                repositories.value = Resource.Error("Error: ${error.message}")
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