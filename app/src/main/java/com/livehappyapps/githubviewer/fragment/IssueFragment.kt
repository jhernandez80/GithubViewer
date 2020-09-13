package com.livehappyapps.githubviewer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.livehappyapps.githubviewer.IssueState
import com.livehappyapps.githubviewer.adapter.IssueAdapter
import com.livehappyapps.githubviewer.databinding.FragmentIssueBinding
import com.livehappyapps.githubviewer.network.GithubRetrofitHelper
import com.livehappyapps.githubviewer.utils.async


class IssueFragment : Fragment() {

    private lateinit var binding: FragmentIssueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIssueBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val issueState = arguments?.getSerializable(ARG_ISSUE_STATE) as IssueState
        val issueAdapter = IssueAdapter(issueState)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = issueAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        val repo = arguments?.getString(ARG_REPO)
        val owner = arguments?.getString(ARG_OWNER)
        if (owner != null && repo != null) {
            // TODO: Move to a ViewModel
            GithubRetrofitHelper().getIssues(owner, repo, issueState)
                .async()
                .subscribe({ issues ->
                    issueAdapter.issues = issues
                }, { error ->

                })
        }
    }

    companion object {
        private const val ARG_ISSUE_STATE = "arg_issue_state"
        private const val ARG_OWNER = "arg_owner"
        private const val ARG_REPO = "arg_repo"

        fun newInstance(state: IssueState, owner: String?, repo: String? ) =
            IssueFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ISSUE_STATE, state)
                    putSerializable(ARG_OWNER, owner)
                    putSerializable(ARG_REPO, repo)
                }
            }
    }
}