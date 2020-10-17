package com.livehappyapps.githubviewer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.livehappyapps.githubviewer.model.Issue
import io.reactivex.Observable


@Dao
interface IssueDao {

    @Query("SELECT * FROM issue WHERE owner=:owner AND repo=:repo AND state=:state")
    fun getIssues(owner: String, repo: String, state: String): Observable<List<Issue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllIssues(issues: List<Issue>)
}