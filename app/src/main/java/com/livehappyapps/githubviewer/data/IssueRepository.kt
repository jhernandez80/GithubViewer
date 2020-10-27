package com.livehappyapps.githubviewer.data

import com.livehappyapps.githubviewer.data.local.GithubDatabase
import com.livehappyapps.githubviewer.model.Issue
import com.livehappyapps.githubviewer.data.remote.api.GithubApi
import io.reactivex.Completable
import io.reactivex.Observable


class IssueRepository(
    private val database: GithubDatabase,
    private val api: GithubApi
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
        // FIXME: Loading state is never cleared if we get an empty response
        return api.getIssues(owner, repo, state)
            .doOnSuccess { issues ->
                // FIXME: See if there's a more optimal way to do this
                val modifiedIssues = issues.map { it.copy(owner = owner, repoName = repo) }
                database.issueDao.insertAllIssues(modifiedIssues)
            }
            .ignoreElement()
    }
}