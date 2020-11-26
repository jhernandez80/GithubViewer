package com.livehappyapps.githubviewer.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.livehappyapps.githubviewer.Config
import com.livehappyapps.githubviewer.SettingsKey
import com.livehappyapps.githubviewer.data.MainRepository
import com.livehappyapps.githubviewer.data.Resource
import com.livehappyapps.githubviewer.model.Organization
import com.livehappyapps.githubviewer.model.Repo
import com.livehappyapps.githubviewer.utils.async
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy


class MainViewModel(
    private val repository: MainRepository,
    private val preferences: SharedPreferences
) : ViewModel(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val compositeDisposable = CompositeDisposable()
    private var orgSubscription: Disposable? = null
    private var repoSubscription: Disposable? = null
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
        getOrganization(organization)
        getRepos(organization)
    }

    private fun getOrganization(organization: String) {
        _organization.value = Resource.Loading
        orgSubscription?.run { dispose() }
        orgSubscription = repository.getOrganization(organization)
            .async()
            .subscribeBy(
                onNext = { repos ->
                    _organization.value = Resource.Success(repos)
                },
                onError = { error ->
                    _organization.value = Resource.Error("Error getting orgs: ${error.message}")
                }
            )
        orgSubscription?.let { compositeDisposable.add(it) }
    }

    fun updateOrganization(organization: String) {
        val subscription = repository.updateOrganization(organization)
            .async()
            .subscribeBy(
                onError = { error ->
                    _organization.value = Resource.Error("Error updating orgs: ${error.message}")
                }
            )
        compositeDisposable.add(subscription)
    }

    private fun getRepos(organization: String) {
        _repos.value = Resource.Loading
        repoSubscription?.run { dispose() }
        repoSubscription = repository.getRepos(organization)
            .async()
            .subscribeBy(
                onNext = { repos ->
                    _repos.value = Resource.Success(repos)
                },
                onError = { error ->
                    _repos.value = Resource.Error("Error getting repos: ${error.message}")
                }
            )
        repoSubscription?.let { compositeDisposable.add(it) }
    }

    fun updateRepos(organization: String) {
        val subscription = repository.updateRepos(organization)
            .async()
            .subscribeBy(
                onError = { error ->
                    _repos.value = Resource.Error("Error updating repos: ${error.message}")
                }
            )
        compositeDisposable.add(subscription)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            SettingsKey.ORGANIZATION -> {
                val org = preferences.getString(SettingsKey.ORGANIZATION, Config.DEFAULT_ORG)!!
                getOrganization(org)
                getRepos(org)
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

