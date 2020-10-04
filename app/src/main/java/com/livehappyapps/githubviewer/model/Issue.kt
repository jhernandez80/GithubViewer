package com.livehappyapps.githubviewer.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Issue(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "created_at")
    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    val title: String? = null,

    @ColumnInfo(name = "body")
    @field:SerializedName("body")
    val body: String? = null,

    @ColumnInfo(name = "number")
    @field:SerializedName("number")
    val number: Int? = null,

    @ColumnInfo(name = "repository_url")
    @field:SerializedName("repository_url")
    val repositoryUrl: String? = null,

    @ColumnInfo(name = "state")
    @field:SerializedName("state")
    val state: String? = null,

    @ColumnInfo(name = "url")
    @field:SerializedName("url")
    val url: String? = null,

    @ColumnInfo(name = "labels")
    @field:SerializedName("labels")
    val labels: List<LabelsItem>? = null
)