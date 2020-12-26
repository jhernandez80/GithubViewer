package com.livehappyapps.githubviewer.data

import com.livehappyapps.githubviewer.data.local.GithubDatabase
import com.livehappyapps.githubviewer.data.remote.api.GithubApi
import com.livehappyapps.githubviewer.model.Issue
import io.reactivex.Observable
import io.reactivex.Single


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

    fun updateIssues(owner: String, repo: String, state: String): Single<List<Issue>> {
        return api.getIssues(owner, repo, state)
            .doOnSuccess { issues ->
                val modifiedIssues = issues.map { it.copy(owner = owner, repoName = repo) }
                database.issueDao.insertAllIssues(modifiedIssues)
            }
    }
}