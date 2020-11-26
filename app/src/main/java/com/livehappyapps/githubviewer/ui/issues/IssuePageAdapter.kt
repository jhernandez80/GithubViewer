package com.livehappyapps.githubviewer.ui.issues

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.livehappyapps.githubviewer.IssueState
import com.livehappyapps.githubviewer.R


class IssuePagerAdapter(
    activity: FragmentActivity,
    owner: String?,
    repo: String?
) : FragmentStateAdapter(activity) {

    val tabs = listOf(
        Tab(activity.getString(R.string.open), IssueState.OPEN, owner, repo),
        Tab(activity.getString(R.string.closed), IssueState.CLOSED, owner, repo)
    )

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): IssueFragment {
        val tab = tabs[position]
        return IssueFragment.newInstance(tab.state, tab.owner, tab.repo)
    }

    data class Tab(val name: String, val state: IssueState, val owner: String?, val repo: String?)
}
