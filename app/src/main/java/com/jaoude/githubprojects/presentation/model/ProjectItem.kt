package com.jaoude.githubprojects.presentation.model

import com.jaoude.githubprojects.domain.model.Project

class ProjectItem(
    val name: String? = null,
    val description: String? = null,
    val stars: Int = 0,
    val owner: OwnerItem? = null
)

fun MutableList<Project>.mapToPresentation(): MutableList<ProjectItem> = map {
    ProjectItem(it.name, it.description, it.stargazersCount ?: 0, it.owner?.mapToPresentation())
}.toMutableList()