package com.livehappyapps.githubviewer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.livehappyapps.githubviewer.model.Organization
import io.reactivex.Observable
import java.util.*


@Dao
interface OrganizationDao {

    @Query("SELECT * FROM organization WHERE login=:organization")
    fun getOrganization(organization: String): Observable<Optional<Organization>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrganization(organizations: Organization)

}