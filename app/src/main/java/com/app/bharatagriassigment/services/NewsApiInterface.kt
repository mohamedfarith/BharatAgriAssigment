package com.app.bharatagriassigment.services

import com.app.bharatagriassigment.models.NewsArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    @GET("top-headlines")
    suspend fun getNewsData(
        @Query("country") results: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") pageNumber: Int
    ): Response<NewsArticle>
}