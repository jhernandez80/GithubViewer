package com.livehappyapps.githubviewer.network

import com.livehappyapps.githubviewer.model.Issue
import com.livehappyapps.githubviewer.model.Organization
import com.livehappyapps.githubviewer.model.Repo
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class GithubRetrofitHelper {
    private val BASE_URL = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    private val githubService = retrofit.create<GithubApi>()

    fun getOrganization(organization: String): Single<Organization> {
        return githubService.getOrganization(organization)
    }

    fun getRepos(owner: String): Single<List<Repo>> {
        return githubService.getRepos(owner)
    }

    fun getIssues(owner: String, repo: String, state: String): Single<List<Issue>> {
        return githubService.getIssues(owner, repo, state)
    }
}