package com.quantumhiggs.moviesapp.network

import androidx.lifecycle.LiveData
import com.quantumhiggs.moviesapp.data.source.remote.response.ApiResponse
import com.quantumhiggs.moviesapp.data.source.remote.response.DetailMovieResponse
import com.quantumhiggs.moviesapp.data.source.remote.response.MoviesResponse
import com.quantumhiggs.moviesapp.data.source.remote.response.ReviewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/popular")
    fun getPopular(@Query("api_key") id: String): LiveData<ApiResponse<MoviesResponse>>

    @GET("movie/top_rated")
    fun getTopRated(@Query("api_key") id: String): LiveData<ApiResponse<MoviesResponse>>

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("api_key") id: String): LiveData<ApiResponse<MoviesResponse>>

    @GET("movie/{id}/reviews")
    fun getReview(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ): LiveData<ApiResponse<ReviewsResponse>>

    @GET("movie/{id}")
    fun getDetail(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ): LiveData<ApiResponse<DetailMovieResponse>>
}