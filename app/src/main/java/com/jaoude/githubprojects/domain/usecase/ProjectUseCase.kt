package com.jaoude.githubprojects.domain.usecase

import android.arch.lifecycle.MutableLiveData
import com.jaoude.githubprojects.domain.repository.ProjectRepository
import com.jaoude.githubprojects.presentation.model.ProjectItem
import com.jaoude.githubprojects.presentation.model.mapToPresentation

class ProjectUseCase(private val projectRepository: ProjectRepository) {

    private var page = 1

    val allProjects = MutableLiveData<MutableList<ProjectItem>>()

    fun canLoadMore() = page != -1

    fun getProjects(onFailure: () -> Unit = {}) {
        projectRepository.getProjectsByPage(page, { projects ->
            page = if (projects.isNotEmpty()) page + 1 else -1
            val currentListRepositories = allProjects.value ?: mutableListOf()
            currentListRepositories.addAll(projects.mapToPresentation())
            this.allProjects.value = currentListRepositories
        }, onFailure)
    }

}