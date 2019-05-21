package com.jaoude.githubprojects.presentation.projectslist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.jaoude.githubprojects.R
import com.jaoude.githubprojects.injectFeature
import kotlinx.android.synthetic.main.activity_projects_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class ProjectsListActivity : AppCompatActivity() {

    private val viewModel by viewModel<ProjectsListViewModel>()

    private val adapter = RepositoriesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects_list)

        title = getString(R.string.trending_repos)

        injectFeature()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        adapter.updateRepositories(mutableListOf())

        viewModel.getRepositoriesLiveData().observe(this, Observer { projects ->
            projects?.let {
                adapter.updateRepositories(projects, viewModel.canLoadMore())
            }
        })

        // we don't want to call next page each time the user rotates his device
        if (savedInstanceState == null)
            callNextPage()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.getRepositoriesLiveData().value?.size) {
                    callNextPage()
                }
            }
        })
    }

    private fun callNextPage() {
        viewModel.getNextRepositoriesPage(onFailure = {
            Toast.makeText(this, getString(R.string.please_check_your_internet_connection), Toast.LENGTH_LONG).show()
        })
    }
}