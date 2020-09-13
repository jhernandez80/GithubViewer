package com.livehappyapps.githubviewer.model

import com.google.gson.annotations.SerializedName

data class Milestone(

	@field:SerializedName("creator")
	val creator: Creator? = null,

	@field:SerializedName("closed_at")
	val closedAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("closed_issues")
	val closedIssues: Int? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("due_on")
	val dueOn: String? = null,

	@field:SerializedName("labels_url")
	val labelsUrl: String? = null,

	@field:SerializedName("number")
	val number: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("open_issues")
	val openIssues: Int? = null,

	@field:SerializedName("node_id")
	val nodeId: String? = null
)