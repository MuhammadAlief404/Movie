package com.quantumhiggs.moviesapp.data.source.local.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.quantumhiggs.moviesapp.data.source.local.entity.*

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBuckMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBuckPopular(movies: List<PopularEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopular(movies: PopularEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBuckTopRated(movies: List<TopRatedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopRated(movies: TopRatedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBuckNowPlaying(movies: List<NowPlayingEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNowPlaying(movies: NowPlayingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBuckFavorite(movies: List<FavoriteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(movies: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBuckReview(movies: List<ReviewEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(movies: ReviewEntity)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movies where id = :id")
    fun getMovie(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM popular")
    fun getPopular(): DataSource.Factory<Int, PopularEntity>

    @Query("SELECT * FROM top_rated")
    fun getTopRated(): DataSource.Factory<Int, TopRatedEntity>

    @Query("SELECT * FROM now_playing")
    fun getNowPlaying(): DataSource.Factory<Int, NowPlayingEntity>

    @Query("SELECT * FROM review where movie_id = :id")
    fun getReviews(id: Int): DataSource.Factory<Int, ReviewEntity>

    @Query("SELECT * FROM movies_favorite")
    fun getFavorite(): DataSource.Factory<Int, FavoriteEntity>

    @Delete
    fun removeFavorite(movie: FavoriteEntity)

}