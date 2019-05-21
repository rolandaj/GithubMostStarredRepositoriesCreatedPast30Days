package connection

import com.jaoude.githubprojects.domain.model.ResponseProjects
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("search/repositories")
    fun getProjects(@Query("q") q: String, @Query("sort") sort: String, @Query("order") order: String, @Query("page") page: Int): Call<ResponseProjects>

}