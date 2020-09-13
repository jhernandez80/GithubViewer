package com.livehappyapps.githubviewer.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.livehappyapps.githubviewer.IssueState
import com.livehappyapps.githubviewer.R
import com.livehappyapps.githubviewer.databinding.ItemIssueBinding
import com.livehappyapps.githubviewer.model.Issue
import com.livehappyapps.githubviewer.utils.setTextOrHide
import com.livehappyapps.githubviewer.utils.toDp
import com.livehappyapps.githubviewer.view.LabelView

class IssueAdapter(
    private val issueState: IssueState
) : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    var issues = emptyList<Issue>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = issues.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding = ItemIssueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(issues[position])
    }

    inner class IssueViewHolder(
        private val binding: ItemIssueBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(issue: Issue) {
            val context = binding.root.context

            binding.title.text = issue.title
            binding.description.setTextOrHide(issue.body)
            binding.number.text = "#${issue.number}"

            val drawable = when (issueState) {
                IssueState.CLOSED -> R.drawable.ic_closed
                IssueState.OPEN -> R.drawable.ic_open
            }
            binding.icon.setImageDrawable(ContextCompat.getDrawable(context, drawable))

            // TODO: This doesn't seem efficient
            binding.labelHolder.removeAllViews()
            issue.labels?.also { binding.labelHolder.isVisible = true }?.forEach {
                val label = LabelView(context).apply {
                    setText(it.name)
                    val color = Color.parseColor("#${it.color}")
                    setCardBackgroundColor(color)
                }
                val params = binding.labelHolder.layoutParams as ViewGroup.MarginLayoutParams
                params.setMargins(0, 8.toDp, 8.toDp, 0)
                binding.labelHolder.addView(label, params)
            }
        }
    }
}
