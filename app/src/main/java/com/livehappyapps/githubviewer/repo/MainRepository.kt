package com.livehappyapps.githubviewer.repo

import com.livehappyapps.githubviewer.data.GithubDatabase
import com.livehappyapps.githubviewer.model.Organization
import com.livehappyapps.githubviewer.model.Repo
import com.livehappyapps.githubviewer.network.GithubRetrofitHelper
import io.reactivex.Completable
import io.reactivex.Observable


/* TODO:
 * Add an expiration to the cache
 */
class MainRepository(
    private val database: GithubDatabase,
    private val retrofitHelper: GithubRetrofitHelper
) {

    fun getOrganization(organization: String): Observable<Organization> {
        return database.organizationDao.getOrganization(organization)
            .flatMap { optionalOrg ->
                if (optionalOrg.isPresent)
                    Observable.just(optionalOrg.get())
                else
                    updateOrganization(organization).toObservable()
            }
    }

    fun updateOrganization(organization: String): Completable {
        return retrofitHelper.getOrganization(organization)
            .doOnSuccess { database.organizationDao.insertOrganization(it) }
            .ignoreElement()
    }

    fun getRepos(organization: String): Observable<List<Repo>> {
        return database.repoDao.getRepos(organization)
            .flatMap { repos ->
                if (repos.isEmpty())
                    updateRepos(organization).toObservable()
                else
                    Observable.just(repos)
            }
    }

    fun updateRepos(organization: String): Completable {
        return retrofitHelper.getRepos(organization)
            .doOnSuccess { database.repoDao.insertAllRepos(it) }
            .ignoreElement()
    }

}