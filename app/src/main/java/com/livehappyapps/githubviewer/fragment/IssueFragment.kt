package com.livehappyapps.githubviewer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.livehappyapps.githubviewer.IssueState
import com.livehappyapps.githubviewer.adapter.IssueAdapter
import com.livehappyapps.githubviewer.databinding.FragmentIssueBinding
import com.livehappyapps.githubviewer.network.Resource
import com.livehappyapps.githubviewer.viewmodel.IssueViewModel
import com.livehappyapps.githubviewer.viewmodel.IssueViewModelFactory


class IssueFragment : Fragment() {

    private lateinit var binding: FragmentIssueBinding
    private var viewModel: IssueViewModel? = null

    private var issueState: IssueState = IssueState.OPEN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repo = arguments?.getString(ARG_REPO)
        val owner = arguments?.getString(ARG_OWNER)
        if (repo != null && owner != null) {
            // FIXME: Mixing IssueState and string is a bad idea
            val issueState = arguments?.getSerializable(ARG_ISSUE_STATE)
            val issueFactory = IssueViewModelFactory(owner, repo, issueState.toString())
            viewModel = ViewModelProvider(this, issueFactory).get(IssueViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIssueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val issueAdapter = IssueAdapter(issueState)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = issueAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel?.issues?.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                Resource.Loading -> {
                    binding.progress.isVisible = true
                    binding.emptyMessage.isVisible = false
                }
                is Resource.Success -> {
                    binding.progress.isVisible = false
                    binding.emptyMessage.isVisible = resource.data.isEmpty()
                    issueAdapter.issues = resource.data
                }
                is Resource.Error -> {
                    binding.progress.isVisible = false
                    Log.d(TAG, resource.message)
                }
            }
        })
    }

    companion object {
        private val TAG = IssueFragment::class.java.simpleName

        private const val ARG_ISSUE_STATE = "arg_issue_state"
        private const val ARG_OWNER = "arg_owner"
        private const val ARG_REPO = "arg_repo"

        fun newInstance(state: IssueState, owner: String?, repo: String?) =
            IssueFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ISSUE_STATE, state)
                    putString(ARG_OWNER, owner)
                    putString(ARG_REPO, repo)
                }
            }
    }
}