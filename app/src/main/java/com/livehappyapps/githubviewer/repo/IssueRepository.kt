package com.livehappyapps.githubviewer.repo

import com.livehappyapps.githubviewer.data.GithubDatabase
import com.livehappyapps.githubviewer.model.Issue
import com.livehappyapps.githubviewer.network.GithubRetrofitHelper
import io.reactivex.Completable
import io.reactivex.Observable


class IssueRepository(
    private val database: GithubDatabase,
    private val retrofitHelper: GithubRetrofitHelper
) {

    fun getIssues(owner: String, repo: String, state: String): Observable<List<Issue>> {
        return database.issueDao.getIssues(owner, repo, state)
            .flatMap { issues ->
                if (issues.isEmpty()) {
                    updateIssues(owner, repo, state).toObservable()
                } else {
                    Observable.just(issues)
                }
            }
    }

    fun updateIssues(owner: String, repo: String, state: String): Completable {
        return retrofitHelper.getIssues(owner, repo, state)
            .doOnSuccess { issues ->
                // FIXME: See if there's a more optimal way to do this
                val modifiedIssues = issues.map { it.copy(owner = owner, repoName = repo) }
                database.issueDao.insertAllIssues(modifiedIssues)
            }
            .ignoreElement()
    }
}