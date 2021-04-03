package com.quantumhiggs.moviesapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.quantumhiggs.moviesapp.data.source.local.entity.*
import com.quantumhiggs.moviesapp.vo.Resource

interface MovieDataSource {

    fun getPopular(): LiveData<Resource<PagedList<PopularEntity>>>
    fun getTopRated(): LiveData<Resource<PagedList<TopRatedEntity>>>
    fun getNowPlaying(): LiveData<Resource<PagedList<NowPlayingEntity>>>
    fun getDetailMovies(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getReview(movieId: Int): LiveData<Resource<PagedList<ReviewEntity>>>

    fun getFavoriteMovies(): LiveData<PagedList<FavoriteEntity>>
    fun setFavoriteMovie(movie: FavoriteEntity)
    fun unSetFavoriteMovie(movie: FavoriteEntity)
}