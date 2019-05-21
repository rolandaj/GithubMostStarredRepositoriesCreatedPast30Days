package com.jaoude.githubprojects

import com.jaoude.githubprojects.data.repository.RetrofitRepository
import com.jaoude.githubprojects.domain.repository.ProjectRepository
import com.jaoude.githubprojects.domain.usecase.ProjectUseCase
import com.jaoude.githubprojects.presentation.projectslist.ProjectsListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        useCaseModule,
        repositoryModule
    )
}

val viewModelModule: Module = module {
    viewModel { ProjectsListViewModel(projectUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { ProjectUseCase(projectRepository = get()) }
}

val repositoryModule: Module = module {
    single { RetrofitRepository() as ProjectRepository }
}
