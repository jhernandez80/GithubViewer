package com.livehappyapps.githubviewer.model

import com.google.gson.annotations.SerializedName

data class Organization(

    @field:SerializedName("repos_url")
    val reposUrl: String? = null,

    @field:SerializedName("twitter_username")
    val twitterUsername: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("blog")
    val blog: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("is_verified")
    val isVerified: Boolean? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("issues_url")
    val issuesUrl: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null
)