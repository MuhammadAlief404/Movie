package com.quantumhiggs.moviesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.quantumhiggs.moviesapp.data.MoviesRepository
import com.quantumhiggs.moviesapp.data.source.local.entity.FavoriteEntity
import com.quantumhiggs.moviesapp.data.source.local.entity.MovieEntity
import com.quantumhiggs.moviesapp.data.source.local.entity.ReviewEntity
import com.quantumhiggs.moviesapp.vo.Resource

class DetailViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    var movieId: Int = 0

    fun setSelectedMovieId(movieId: Int) {
        this.movieId = movieId
    }

    fun getDetailMovie(): LiveData<Resource<MovieEntity>> =
        moviesRepository.getDetailMovies(movieId)

    fun getReview(): LiveData<Resource<PagedList<ReviewEntity>>> =
        moviesRepository.getReview(movieId)

    fun unsetFavorite(movie: FavoriteEntity) = moviesRepository.unSetFavoriteMovie(movie)

    fun setFavorite(movie: MovieEntity?) {
        val favorite = FavoriteEntity(
            movieId = movie?.movieId,
            genre = movie?.genre,
            homepage = movie?.homepage,
            duration = movie?.duration,
            movieReleaseDate = movie?.movieReleaseDate,
            movieRating = movie?.movieRating,
            movieName = movie?.movieName,
            movieLanguage = movie?.movieLanguage,
            movieImage = movie?.movieImage,
            movieDesc = movie?.movieDesc
        )
        moviesRepository.setFavoriteMovie(favorite)
    }
}