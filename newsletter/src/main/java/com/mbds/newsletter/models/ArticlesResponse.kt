package com.mbds.newsletter.models

class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>) {}