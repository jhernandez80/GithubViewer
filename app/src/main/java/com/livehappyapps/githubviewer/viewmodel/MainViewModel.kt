package com.livehappyapps.githubviewer.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.livehappyapps.githubviewer.Config
import com.livehappyapps.githubviewer.SettingsKey
import com.livehappyapps.githubviewer.model.Repository
import com.livehappyapps.githubviewer.network.GithubRetrofitHelper
import com.livehappyapps.githubviewer.network.Resource
import com.livehappyapps.githubviewer.utils.async
import io.reactivex.disposables.CompositeDisposable


class MainViewModel(
    private val preferences: SharedPreferences,
    private val githubHelper: GithubRetrofitHelper = GithubRetrofitHelper()
) : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val compositeDisposable = CompositeDisposable()
    private val _repositories: MutableLiveData<Resource<List<Repository>>> by lazy {
        MutableLiveData<Resource<List<Repository>>>()
    }
    val repositories: LiveData<Resource<List<Repository>>>
        get() = _repositories

    init {
        preferences.registerOnSharedPreferenceChangeListener(this)

        val organization = preferences.getString(SettingsKey.ORGANIZATION, Config.DEFAULT_ORG)!!
        fetchRepositories(organization)
    }

    fun fetchRepositories(organization: String) {
        // TODO: Calling the API should be handled by the Repository
        _repositories.value = Resource.Loading
        val repoSubscription = githubHelper.getRepositories(organization)
            .async()
            .subscribe({ repos ->
                _repositories.value = Resource.Success(repos)
            }, { error ->
                _repositories.value = Resource.Error("Error: ${error.message}")
            })
        compositeDisposable.add(repoSubscription)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            SettingsKey.ORGANIZATION -> {
                val org = preferences.getString(SettingsKey.ORGANIZATION, Config.DEFAULT_ORG)!!
                fetchRepositories(org)
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