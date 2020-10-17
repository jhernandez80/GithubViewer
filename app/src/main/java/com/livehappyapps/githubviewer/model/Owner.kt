package com.livehappyapps.githubviewer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "owner")
data class Owner(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String? = null,

    @ColumnInfo(name = "followers_url")
    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @ColumnInfo(name = "url")
    @field:SerializedName("url")
    val url: String? = null,

    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null
)