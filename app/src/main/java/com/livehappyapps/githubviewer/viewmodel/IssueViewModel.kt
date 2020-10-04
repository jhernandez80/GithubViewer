package com.livehappyapps.githubviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.livehappyapps.githubviewer.data.GithubDatabase
import com.livehappyapps.githubviewer.model.Issue
import com.livehappyapps.githubviewer.network.GithubRetrofitHelper
import com.livehappyapps.githubviewer.network.Resource
import com.livehappyapps.githubviewer.repo.IssueRepository
import com.livehappyapps.githubviewer.utils.async
import io.reactivex.disposables.CompositeDisposable


class IssueViewModel(
    owner: String,
    repo: String,
    state: String,
    retrofitHelper: GithubRetrofitHelper,
    database: GithubDatabase
) : ViewModel() {

    private val repository: IssueRepository = IssueRepository(database, retrofitHelper)
    private val compositeDisposable = CompositeDisposable()

    private val _issues: MutableLiveData<Resource<List<Issue>>> by lazy {
        MutableLiveData<Resource<List<Issue>>>()
    }
    val issues: MutableLiveData<Resource<List<Issue>>>
        get() = _issues

    init {
        getIssues(owner, repo, state)
    }

    private fun getIssues(owner: String, repo: String, state: String) {
        _issues.value = Resource.Loading
        val issueSubscription = repository.getIssues(owner, repo, state)
            .async()
            .subscribe({ issues ->
                _issues.value = Resource.Success(issues)
            }, { error ->
                _issues.value = Resource.Error("Error: ${error.message}")
            })
        compositeDisposable.add(issueSubscription)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

class IssueViewModelFactory(
    private val owner: String,
    private val repo: String,
    private val state: String,
    private val retrofitHelper: GithubRetrofitHelper,
    private val database: GithubDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IssueViewModel(owner, repo, state, retrofitHelper, database) as T
    }

}
