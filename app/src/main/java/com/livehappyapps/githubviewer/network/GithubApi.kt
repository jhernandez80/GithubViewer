package com.livehappyapps.githubviewer.network

import com.livehappyapps.githubviewer.IssueState
import com.livehappyapps.githubviewer.model.Issue
import com.livehappyapps.githubviewer.model.Repository
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubApi {

    @GET("orgs/{owner}/repos")
    fun getRepositories(
        @Path("owner") owner: String
    ): Single<List<Repository>>

    @GET("repos/{owner}/{repo}/issues")
    fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String
    ): Single<List<Issue>>
}