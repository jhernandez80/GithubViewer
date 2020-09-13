package com.livehappyapps.githubviewer


enum class IssueState(private val state: String) {
    CLOSED("closed"),
    OPEN("open");

    override fun toString(): String {
        return state
    }
}
