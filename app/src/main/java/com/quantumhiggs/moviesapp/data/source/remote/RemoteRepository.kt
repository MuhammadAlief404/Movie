package com.quantumhiggs.moviesapp.data.source.remote

import com.quantumhiggs.moviesapp.BuildConfig
import com.quantumhiggs.moviesapp.network.NetworkConfig

class RemoteRepository {

    private val KEY = BuildConfig.API_KEY

    fun getPopular() = NetworkConfig.api().getPopular(KEY)

    fun getTopRated() = NetworkConfig.api().getTopRated(KEY)

    fun getNowPlaying() = NetworkConfig.api().getNowPlaying(KEY)

    fun getDetailMovie(id: Int) = NetworkConfig.api().getDetail(id, KEY)

    fun getReview(movieId: Int) = NetworkConfig.api().getReview(movieId, KEY)

}