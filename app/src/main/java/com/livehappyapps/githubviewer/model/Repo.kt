package com.livehappyapps.githubviewer.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

/* TODO: Ideally, the database and network models should not be the same object
 * Refer to the following article for more information:
 * https://proandroiddev.com/the-real-repository-pattern-in-android-efba8662b754
 */
@Entity(tableName = "repo")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class Repo(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "stargazers_count")
    @field:SerializedName("stargazers_count")
    val stargazersCount: Int? = null,

    @ColumnInfo(name = "language")
    @field:SerializedName("language")
    val language: String? = null,

    @ColumnInfo(name = "subscribers_count")
    @field:SerializedName("subscribers_count")
    val subscribersCount: Int? = null,

    @ColumnInfo(name = "full_name")
    @field:SerializedName("full_name")
    val fullName: String,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "open_issues_count")
    @field:SerializedName("open_issues_count")
    val openIssuesCount: Int? = null,

    @ColumnInfo(name = "description")
    @field:SerializedName("description")
    val description: String? = null,

    // TODO: Need to figure out how to save complex object in DB and how to keep the relationship
    @Embedded(prefix = "owner_")
    @field:SerializedName("owner")
    val owner: Owner? = null,

    @ColumnInfo(name = "url")
    @field:SerializedName("url")
    val url: String? = null
)