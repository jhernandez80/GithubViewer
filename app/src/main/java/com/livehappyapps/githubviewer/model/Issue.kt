package com.livehappyapps.githubviewer.model

import com.google.gson.annotations.SerializedName

data class Issue(

	@field:SerializedName("assignees")
	val assignees: List<AssigneesItem?>? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("labels_url")
	val labelsUrl: String? = null,

	@field:SerializedName("author_association")
	val authorAssociation: String? = null,

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("performed_via_github_app")
	val performedViaGithubApp: Boolean? = null,

	@field:SerializedName("comments_url")
	val commentsUrl: String? = null,

	@field:SerializedName("active_lock_reason")
	val activeLockReason: String? = null,

	@field:SerializedName("repository_url")
	val repositoryUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("locked")
	val locked: Boolean? = null,

	@field:SerializedName("comments")
	val comments: Int? = null,

	@field:SerializedName("closed_at")
	val closedAt: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("labels")
	val labels: List<LabelsItem>? = null,

	@field:SerializedName("milestone")
	val milestone: Milestone? = null,

	@field:SerializedName("events_url")
	val eventsUrl: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("assignee")
	val assignee: Assignee? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("node_id")
	val nodeId: String? = null
)