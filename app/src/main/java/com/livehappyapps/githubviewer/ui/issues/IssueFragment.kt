package com.livehappyapps.githubviewer.ui.issues

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.livehappyapps.githubviewer.IssueState
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.data.Resource
import com.livehappyapps.githubviewer.databinding.FragmentIssueBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class IssueFragment : Fragment() {

    private lateinit var binding: FragmentIssueBinding

    private val viewModel: IssueViewModel by viewModel {
        parametersOf(owner, repo, issueState.toString())
    }

    private val repo: String by lazy {
        arguments?.getString(ARG_REPO) ?: ""
    }
    private val owner: String by lazy {
        arguments?.getString(ARG_OWNER) ?: ""
    }
    private val issueState: IssueState by lazy {
        arguments?.getSerializable(ARG_ISSUE_STATE) as? IssueState ?: IssueState.OPEN
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

        binding.swipeRefresh.apply {
            setColorSchemeColors(ContextCompat.getColor(context, R.color.accent))
            setOnRefreshListener {
                viewModel.updateIssues(owner, repo, issueState.toString())
            }
        }

        viewModel.issues.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                Resource.Loading -> {
                    binding.progress.isVisible = !binding.swipeRefresh.isRefreshing
                }
                is Resource.Success -> {
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    binding.emptyMessage.isVisible = resource.data.isEmpty()
                    issueAdapter.issues = resource.data
                }
                is Resource.Error -> {
                    binding.progress.isVisible = false
                    binding.swipeRefresh.isRefreshing = false
                    Log.d(TAG, resource.message)
                }
            }
        }
    }

    companion object {

        private val TAG = IssueFragment::class.java.simpleName

        private const val ARG_ISSUE_STATE = "arg_issue_state"
        private const val ARG_OWNER = "arg_owner"
        private const val ARG_REPO = "arg_repo"

        fun newInstance(state: IssueState, owner: String?, repo: String?) =
            IssueFragment().apply {
                arguments = bundleOf(
                    ARG_ISSUE_STATE to state,
                    ARG_OWNER to owner,
                    ARG_REPO to repo,
                )
            }
    }
}