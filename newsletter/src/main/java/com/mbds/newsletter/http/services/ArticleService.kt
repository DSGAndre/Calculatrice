package com.mbds.newsletter.http.services

import com.mbds.newsletter.models.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("everything?apiKey=8261892cd50f455fb52dab184e26b748")
    fun list(@Query("q") category: String): Call<ArticlesResponse>
}