package com.livehappyapps.githubviewer.ui.issues

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.databinding.FragmentIssueContainerBinding
import com.livehappyapps.githubviewer.utils.toastShort


class IssueContainerFragment : Fragment() {

    private lateinit var binding: FragmentIssueContainerBinding

    private val args by navArgs<IssueContainerFragmentArgs>()
    private var owner: String? = null
    private var repo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        owner = args.owner
        repo = args.repo
        if (owner == null || repo == null) {
            requireContext().toastShort(getString(R.string.issue_retrieving_repo_details))
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIssueContainerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = "${owner?.capitalize()} Issues"

        val pagerAdapter = IssuePagerAdapter(requireActivity(), owner, repo)
        binding.pager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = pagerAdapter.tabs[position].name
        }.attach()
    }
}