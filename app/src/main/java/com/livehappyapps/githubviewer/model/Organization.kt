package com.livehappyapps.githubviewer.model

import com.google.gson.annotations.SerializedName

data class Organization(

	@field:SerializedName("repos_url")
	val reposUrl: String? = null,

	@field:SerializedName("has_repository_projects")
	val hasRepositoryProjects: Boolean? = null,

	@field:SerializedName("members_url")
	val membersUrl: String? = null,

	@field:SerializedName("twitter_username")
	val twitterUsername: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("blog")
	val blog: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("public_members_url")
	val publicMembersUrl: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("is_verified")
	val isVerified: Boolean? = null,

	@field:SerializedName("public_gists")
	val publicGists: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("issues_url")
	val issuesUrl: String? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("events_url")
	val eventsUrl: String? = null,

	@field:SerializedName("has_organization_projects")
	val hasOrganizationProjects: Boolean? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("hooks_url")
	val hooksUrl: String? = null,

	@field:SerializedName("node_id")
	val nodeId: String? = null
)