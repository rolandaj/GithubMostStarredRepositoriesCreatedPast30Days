package com.jaoude.githubprojects.data.repository

import com.jaoude.githubprojects.domain.model.Project
import com.jaoude.githubprojects.domain.model.ResponseProjects
import com.jaoude.githubprojects.domain.repository.ProjectRepository
import connection.RetrofitServices
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = "https://api.github.com/"

private const val NUMBER_OF_DAYS_LIMIT = 30
private const val SORT = "stars"
private const val ORDER = "desc"

class RetrofitRepository : ProjectRepository {

    private var isGettingData = false

    override fun getProjectsByPage(
        page: Int,
        onSuccess: (projects: MutableList<Project>) -> Unit,
        onFailure: () -> Unit
    ) {
        if (!isGettingData && page != -1) {
            isGettingData = true

            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -NUMBER_OF_DAYS_LIMIT)
            val pastNumberOfDaysLimitDate = SimpleDateFormat("yyyy-MM-dd").format(cal.time)

            val retrofitServices = getRetrofit().create(RetrofitServices::class.java)
            val getProjectsCall = retrofitServices.getProjects("created:$pastNumberOfDaysLimitDate", SORT, ORDER, page)
            getProjectsCall.enqueue(object : Callback<ResponseProjects> {
                override fun onResponse(call: Call<ResponseProjects>?, responseServer: Response<ResponseProjects>?) {
                    if (responseServer?.isSuccessful == true)
                        responseServer.body()?.projects?.let { projects ->
                            onSuccess(projects)
                        }
                    else
                        onFailure()

                    isGettingData = false
                }

                override fun onFailure(call: Call<ResponseProjects>?, t: Throwable?) {
                    onFailure()
                    isGettingData = false
                }
            })
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}