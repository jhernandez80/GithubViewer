package com.livehappyapps.githubviewer.model

import com.google.gson.annotations.SerializedName

data class Repo(

    @field:SerializedName("stargazers_count")
    val stargazersCount: Int? = null,

    @field:SerializedName("language")
    val language: String? = null,

    @field:SerializedName("subscribers_count")
    val subscribersCount: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("open_issues_count")
    val openIssuesCount: Int? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("owner")
    val owner: Owner? = null,

    @field:SerializedName("url")
    val url: String? = null
)