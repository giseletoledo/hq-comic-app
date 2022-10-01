package com.ebac.hqcomicapp.api

import com.ebac.hqcomicapp.data.ComicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ComicsService {
    @GET("v1/public/comics")
    suspend fun getComicsList(
        @Query("ts") timestamp: String,
        @Query("apikey") publicKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int
    ): Response<ComicResponse>
}