package com.livehappyapps.githubviewer.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.livehappyapps.githubviewer.databinding.ItemRepositoryBinding
import com.livehappyapps.githubviewer.model.Repository
import com.livehappyapps.githubviewer.utils.ColorMapper
import com.livehappyapps.githubviewer.utils.setTextOrHide


class RepositoryAdapter(
    private val onItemClickListener: ((owner: String?, repo: String?) -> Unit)? = null
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories = emptyList<Repository>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    inner class RepositoryViewHolder(
        private val binding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: Repository) {
            val owner = repository.owner?.login
            val name = repository.name
            binding.fullName.text = Html.fromHtml("$owner/<b>$name</b>")
            binding.description.setTextOrHide(repository.description)
            binding.stargazerCount.text = repository.stargazersCount.toString()
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(repository.owner?.login, repository.name)
            }

            if (repository.language != null) {
                binding.languageIcon.visibility = View.VISIBLE
                binding.language.visibility = View.VISIBLE
                binding.language.text = repository.language
                binding.languageIcon.setColorFilter(ColorMapper.getColorFor(repository.language))
            } else {
                binding.languageIcon.visibility = View.GONE
                binding.language.visibility = View.GONE
            }
        }
    }
}