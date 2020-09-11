package com.livehappyapps.githubviewer.network

import com.livehappyapps.githubviewer.model.Repository
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface GithubApi {

    @GET("orgs/{org}/repos")
    fun getRepositories(
        @Path("org") organization: String
    ): Single<List<Repository>>
}