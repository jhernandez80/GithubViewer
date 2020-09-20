package com.livehappyapps.githubviewer.model

import com.google.gson.annotations.SerializedName

data class Issue(

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("body")
    val body: String? = null,

    @field:SerializedName("number")
    val number: Int? = null,

    @field:SerializedName("repository_url")
    val repositoryUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("labels")
    val labels: List<LabelsItem>? = null
)