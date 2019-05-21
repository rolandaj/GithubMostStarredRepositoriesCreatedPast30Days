package com.jaoude.githubprojects.presentation.projectslist

import android.arch.lifecycle.ViewModel
import com.jaoude.githubprojects.domain.usecase.ProjectUseCase

class ProjectsListViewModel (private val projectUseCase: ProjectUseCase) : ViewModel() {

    fun getRepositoriesLiveData() = projectUseCase.allProjects

    fun canLoadMore() = projectUseCase.canLoadMore()

    fun getNextRepositoriesPage(onFailure: () -> Unit = {}) {
        projectUseCase.getProjects(onFailure)
    }
}