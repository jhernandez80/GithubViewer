package com.livehappyapps.githubviewer.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.livehappyapps.githubviewer.Config
import com.livehappyapps.githubviewer.SettingsKey
import com.livehappyapps.githubviewer.model.Organization
import com.livehappyapps.githubviewer.model.Repo
import com.livehappyapps.githubviewer.network.GithubRetrofitHelper
import com.livehappyapps.githubviewer.network.Resource
import com.livehappyapps.githubviewer.utils.async
import io.reactivex.disposables.CompositeDisposable


class MainViewModel(
    private val preferences: SharedPreferences,
    private val githubHelper: GithubRetrofitHelper = GithubRetrofitHelper()
) : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val compositeDisposable = CompositeDisposable()
    private val _organization: MutableLiveData<Resource<Organization>> by lazy {
        MutableLiveData<Resource<Organization>>()
    }
    val organization: LiveData<Resource<Organization>>
        get() = _organization
    private val _repos: MutableLiveData<Resource<List<Repo>>> by lazy {
        MutableLiveData<Resource<List<Repo>>>()
    }
    val repos: LiveData<Resource<List<Repo>>>
        get() = _repos

    init {
        preferences.registerOnSharedPreferenceChangeListener(this)

        val organization = preferences.getString(SettingsKey.ORGANIZATION, Config.DEFAULT_ORG)!!
        fetchOrganization(organization)
        fetchRepos(organization)
    }

    fun fetchOrganization(organization: String) {
        _organization.value = Resource.Loading
        val orgSubscription = githubHelper.getOrganization(organization)
            .async()
            .subscribe({ org ->
                _organization.value = Resource.Success(org)
            }, { error ->
                _organization.value = Resource.Error("Error: ${error.message}")
            })
        compositeDisposable.add(orgSubscription)
    }

    fun fetchRepos(organization: String) {
        // TODO: Calling the API should be handled by the Repository
        _repos.value = Resource.Loading
        val repoSubscription = githubHelper.getRepos(organization)
            .async()
            .subscribe({ repos ->
                _repos.value = Resource.Success(repos)
            }, { error ->
                _repos.value = Resource.Error("Error: ${error.message}")
            })
        compositeDisposable.add(repoSubscription)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            SettingsKey.ORGANIZATION -> {
                val org = preferences.getString(SettingsKey.ORGANIZATION, Config.DEFAULT_ORG)!!
                fetchOrganization(org)
                fetchRepos(org)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        preferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}

class MainViewModelFactory(
    private val preferences: SharedPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(preferences) as T
    }

}