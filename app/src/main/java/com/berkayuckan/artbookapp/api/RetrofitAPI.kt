package com.berkayuckan.artbookapp.api

import com.berkayuckan.artbookapp.roomdb.model.ImageResponse
import com.berkayuckan.artbookapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = Constants.API_KEY
    ) : Response<ImageResponse>
}