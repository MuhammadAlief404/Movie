package com.quantumhiggs.moviesapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.quantumhiggs.moviesapp.data.source.local.db.MoviesDao
import com.quantumhiggs.moviesapp.data.source.local.entity.*

class LocalRepository(private val moviesDao: MoviesDao) {
    companion object {
        private var INSTANCE: LocalRepository? = null

        fun getInstance(movieDao: MoviesDao): LocalRepository {
            if (INSTANCE == null) {
                INSTANCE = LocalRepository(movieDao)
            }

            return INSTANCE as LocalRepository
        }
    }

    fun insertBuckMovies(movies: List<MovieEntity>) = moviesDao.insertBuckMovies(movies)

    fun insertMovie(movies: MovieEntity) = moviesDao.insertMovie(movies)

    fun insertBuckPopular(movies: List<PopularEntity>) = moviesDao.insertBuckPopular(movies)

    fun insertPopular(movies: PopularEntity) = moviesDao.insertPopular(movies)

    fun insertBuckTopRated(movies: List<TopRatedEntity>) = moviesDao.insertBuckTopRated(movies)

    fun insertTopRated(movies: TopRatedEntity) = moviesDao.insertTopRated(movies)

    fun insertBuckNowPlaying(movies: List<NowPlayingEntity>) = moviesDao.insertBuckNowPlaying(movies)

    fun insertNowPlaying(movies: NowPlayingEntity) = moviesDao.insertNowPlaying(movies)

    fun insertBuckFavorite(movies: List<FavoriteEntity>) = moviesDao.insertBuckFavorite(movies)

    fun insertFavorite(movies: FavoriteEntity) = moviesDao.insertFavorite(movies)

    fun insertBuckReview(review: List<ReviewEntity>) = moviesDao.insertBuckReview(review)

    fun insertReview(movies: ReviewEntity) = moviesDao.insertReview(movies)

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = moviesDao.getAllMovies()

    fun getPopular(): DataSource.Factory<Int, PopularEntity> = moviesDao.getPopular()

    fun getTopRated(): DataSource.Factory<Int, TopRatedEntity> = moviesDao.getTopRated()

    fun getNowPlaying(): DataSource.Factory<Int, NowPlayingEntity> = moviesDao.getNowPlaying()

    fun getReviews(movieId: Int): DataSource.Factory<Int, ReviewEntity> = moviesDao.getReviews(movieId)

    fun getFavorite(): DataSource.Factory<Int, FavoriteEntity> = moviesDao.getFavorite()

    fun getMovie(id: Int): LiveData<MovieEntity> = moviesDao.getMovie(id)

    fun removeFavorite(movie: FavoriteEntity) = moviesDao.removeFavorite(movie)

}