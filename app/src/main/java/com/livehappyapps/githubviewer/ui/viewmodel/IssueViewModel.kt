package com.livehappyapps.githubviewer.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.livehappyapps.githubviewer.model.Issue
import com.livehappyapps.githubviewer.data.Resource
import com.livehappyapps.githubviewer.data.IssueRepository
import com.livehappyapps.githubviewer.utils.async
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy


class IssueViewModel(
    owner: String,
    repo: String,
    state: String,
    private val repository: IssueRepository
) : ViewModel() {

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

    fun updateIssues(owner: String, repo: String, state: String) {
        val subscription = repository.updateIssues(owner, repo, state)
            .async()
            .subscribeBy(
                onError = { error ->
                    _issues.value = Resource.Error("Error updating issues: ${error.message}")
                }
            )
        compositeDisposable.add(subscription)
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
    private val repository: IssueRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IssueViewModel(owner, repo, state, repository) as T
    }

}
