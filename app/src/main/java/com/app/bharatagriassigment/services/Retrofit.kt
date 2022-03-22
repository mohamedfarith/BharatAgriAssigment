package com.app.bharatagriassigment.services

import com.app.bharatagriassigment.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    fun getInstance(): NewsApiInterface {
        return apiInterface
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(HttpInterceptor()).build()
    }

    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    private val apiInterface: NewsApiInterface by lazy {
        retrofitInstance.create(NewsApiInterface::class.java)
    }


    class HttpInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder().addHeader(Constants.API_NAME, Constants.API_KEY).build()
            return chain.proceed(request)

        }

    }


}