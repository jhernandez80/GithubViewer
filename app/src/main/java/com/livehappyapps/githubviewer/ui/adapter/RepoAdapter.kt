package com.livehappyapps.githubviewer.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.livehappyapps.githubviewer.databinding.ItemRepoBinding
import com.livehappyapps.githubviewer.model.Repo
import com.livehappyapps.githubviewer.utils.ColorMapper
import com.livehappyapps.githubviewer.utils.setTextOrHide


class RepoAdapter(
    private val onItemClickListener: ((owner: String?, repo: String?) -> Unit)? = null
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    var repos = emptyList<Repo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = repos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    inner class RepoViewHolder(
        private val binding: ItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            val owner = repo.owner?.login
            val name = repo.name
            binding.fullName.text = Html.fromHtml("$owner/<b>$name</b>")
            binding.description.setTextOrHide(repo.description)
            binding.stargazerCount.text = repo.stargazersCount.toString()
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(repo.owner?.login, repo.name)
            }

            if (repo.language != null) {
                binding.languageIcon.visibility = View.VISIBLE
                binding.language.visibility = View.VISIBLE
                binding.language.text = repo.language
                binding.languageIcon.setColorFilter(ColorMapper.getColorFor(repo.language))
            } else {
                binding.languageIcon.visibility = View.GONE
                binding.language.visibility = View.GONE
            }
        }
    }
}