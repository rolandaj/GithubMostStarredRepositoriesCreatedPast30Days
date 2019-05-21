package com.jaoude.githubprojects.domain.repository

import com.jaoude.githubprojects.domain.model.Project

interface ProjectRepository {

    fun getProjectsByPage(
        page: Int,
        onSuccess: (projects: MutableList<Project>) -> Unit = {},
        onFailure: () -> Unit = {}
    )
}