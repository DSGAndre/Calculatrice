package com.mbds.newsletter.http.repositories

import com.mbds.newsletter.http.services.ArticleService
import com.mbds.newsletter.models.Article
import com.mbds.newsletter.models.ArticlesResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleRepository {
    private val service: ArticleService
    init {
        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://newsapi.org/v2/")
        }
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ArticleService::class.java)
    }

    suspend fun list(category: String): ArticlesResponse = service.list(category)
}