package com.jaoude.githubprojects.presentation.projectslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jaoude.githubprojects.R
import com.jaoude.githubprojects.presentation.model.ProjectItem
import kotlinx.android.synthetic.main.item_project.view.*

private const val TYPE_LOAD_MORE = 0
private const val TYPE_CELL = 1

class RepositoriesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: MutableList<ProjectItem?> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_LOAD_MORE)
            LoadMoreViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false))
        else
            RepositoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false))
    }

    override fun getItemViewType(position: Int) = if (list[position] == null) TYPE_LOAD_MORE else TYPE_CELL

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepositoryViewHolder)
            list[position]?.let { repository ->
                holder.bind(repository)
            }
    }

    override fun getItemCount() = list.size

    fun updateRepositories(tempList: List<ProjectItem>, canLoadMore: Boolean = true) {
        this.list.clear()
        this.list.addAll(tempList)
        if (canLoadMore)
            this.list.add(null)
        notifyDataSetChanged()
    }

    class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var project: ProjectItem

        fun bind(project: ProjectItem) {
            this.project = project
            itemView.name.text = project.name
            itemView.description.text = project.description
            itemView.username.text = project.owner?.name
            itemView.stars.text = largeNumberFormatter(project.stars)
            Glide.with(itemView).load(project.owner?.profilePictureUrl)
                .placeholder(R.drawable.ic_user_image_placeholder).into(itemView.userImage)
        }

        private fun largeNumberFormatter(number: Int): String {
            if (number < 1000)
                return String.format("%d", number)
            val exp = (Math.log(number.toDouble()) / Math.log(1000.0)).toInt()
            return String.format("%.1f %c", number / Math.pow(1000.0, exp.toDouble()), "KMGTPE"[exp - 1])
        }
    }

    class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}