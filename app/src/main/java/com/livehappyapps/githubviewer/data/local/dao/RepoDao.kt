package com.livehappyapps.githubviewer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.livehappyapps.githubviewer.model.Repo
import io.reactivex.Observable


@Dao
interface RepoDao {

    @Query("SELECT * FROM repo WHERE owner_login=:organization")
    fun getRepos(organization: String): Observable<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllRepos(repos: List<Repo>)

}