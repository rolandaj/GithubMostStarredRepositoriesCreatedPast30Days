package com.jaoude.githubprojects.presentation.model

import com.jaoude.githubprojects.domain.model.Owner

class OwnerItem(
    var name: String? = null,
    var profilePictureUrl: String? = null
)

fun Owner.mapToPresentation(): OwnerItem {
    return OwnerItem(login, avatarUrl)
}