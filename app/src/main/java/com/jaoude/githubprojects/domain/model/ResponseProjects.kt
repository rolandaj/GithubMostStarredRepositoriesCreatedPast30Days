package com.jaoude.githubprojects.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseProjects {

    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null
    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean? = null
    @SerializedName("items")
    @Expose
    var projects: MutableList<Project> = mutableListOf()

}