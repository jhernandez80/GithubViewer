package com.livehappyapps.githubviewer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "organization")
data class Organization(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "repos_url")
    @field:SerializedName("repos_url")
    val reposUrl: String? = null,

    @ColumnInfo(name = "description")
    @field:SerializedName("description")
    val description: String? = null,

    @ColumnInfo(name = "blog")
    @field:SerializedName("blog")
    val blog: String? = null,

    @ColumnInfo(name = "company")
    @field:SerializedName("company")
    val company: String? = null,

    @ColumnInfo(name = "email")
    @field:SerializedName("email")
    val email: String? = null,

    @ColumnInfo(name = "is_verified")
    @field:SerializedName("is_verified")
    val isVerified: Boolean? = null,

    @ColumnInfo(name = "url")
    @field:SerializedName("url")
    val url: String? = null,

    @ColumnInfo(name = "issues_url")
    @field:SerializedName("issues_url")
    val issuesUrl: String? = null,

    @ColumnInfo(name = "followers")
    @field:SerializedName("followers")
    val followers: Int? = null,

    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo(name = "following")
    @field:SerializedName("following")
    val following: Int? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String? = null,

    @ColumnInfo(name = "location")
    @field:SerializedName("location")
    val location: String? = null
)