package com.mbds.newsletter.models

data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>)