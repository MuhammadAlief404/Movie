package com.quantumhiggs.moviesapp.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.quantumhiggs.moviesapp.data.source.MovieDataSource
import com.quantumhiggs.moviesapp.data.source.local.LocalRepository
import com.quantumhiggs.moviesapp.data.source.local.entity.*
import com.quantumhiggs.moviesapp.data.source.remote.RemoteRepository
import com.quantumhiggs.moviesapp.data.source.remote.response.ApiResponse
import com.quantumhiggs.moviesapp.data.source.remote.response.DetailMovieResponse
import com.quantumhiggs.moviesapp.data.source.remote.response.MoviesResponse
import com.quantumhiggs.moviesapp.data.source.remote.response.ReviewsResponse
import com.quantumhiggs.moviesapp.network.NetworkBoundResource
import com.quantumhiggs.moviesapp.utils.AppExecutors
import com.quantumhiggs.moviesapp.vo.Resource

class MoviesRepository private constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    companion object {
        @Volatile
        private var INSTANCE: MoviesRepository? = null

        fun getInstance(
            remoteRepository: RemoteRepository,
            localRepository: LocalRepository,
            appExecutors: AppExecutors
        ): MoviesRepository? {
            if (INSTANCE == null) {
                synchronized(MoviesRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MoviesRepository(remoteRepository, localRepository, appExecutors)
                    }
                }
            }

            return INSTANCE
        }
    }

    override fun getPopular(): LiveData<Resource<PagedList<PopularEntity>>> {
        return object :
            NetworkBoundResource<PagedList<PopularEntity>, MoviesResponse>(appExecutors) {

            override fun loadFromDB(): LiveData<PagedList<PopularEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .setPrefetchDistance(3)
                    .build()
                return LivePagedListBuilder(localRepository.getPopular(), config).build()
            }

            override fun shouldFetch(data: PagedList<PopularEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> =
                remoteRepository.getPopular()

            override fun saveCallResult(data: MoviesResponse) {
                val movieList = ArrayList<MovieEntity>()
                val popularList = ArrayList<PopularEntity>()

                data.results?.forEachIndexed { index, movies ->
                    val movie = MovieEntity(
                        movieId = movies?.id,
                        movieDesc = movies?.overview,
                        movieImage = movies?.posterPath,
                        movieLanguage = movies?.originalLanguage,
                        movieName = movies?.title,
                        movieRating = movies?.voteAverage.toString(),
                        movieReleaseDate = movies?.releaseDate
                    )
                    val popular = PopularEntity(
                        movieId = movies?.id,
                        movieDesc = movies?.overview,
                        movieImage = movies?.posterPath,
                        movieLanguage = movies?.originalLanguage,
                        movieName = movies?.title,
                        movieRating = movies?.voteAverage.toString(),
                        movieReleaseDate = movies?.releaseDate
                    )
                    movieList.add(movie)
                    popularList.add(popular)
                }
                localRepository.insertBuckPopular(popularList)
                localRepository.insertBuckMovies(movieList)
            }

        }.asLiveData()
    }


    override fun getTopRated(): LiveData<Resource<PagedList<TopRatedEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TopRatedEntity>, MoviesResponse>(appExecutors) {

            override fun loadFromDB(): LiveData<PagedList<TopRatedEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localRepository.getTopRated(), config).build()
            }

            override fun shouldFetch(data: PagedList<TopRatedEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> =
                remoteRepository.getTopRated()

            override fun saveCallResult(data: MoviesResponse) {
                val movieList = ArrayList<TopRatedEntity>()

                data.results?.forEachIndexed { index, movies ->
                    val movie = TopRatedEntity(
                        movieId = movies?.id,
                        movieDesc = movies?.overview,
                        movieImage = movies?.posterPath,
                        movieLanguage = movies?.originalLanguage,
                        movieName = movies?.title,
                        movieRating = movies?.voteAverage.toString(),
                        movieReleaseDate = movies?.releaseDate
                    )
                    movieList.add(movie)
                }
                localRepository.insertBuckTopRated(movieList)
            }

        }.asLiveData()
    }

    override fun getNowPlaying(): LiveData<Resource<PagedList<NowPlayingEntity>>> {
        return object :
            NetworkBoundResource<PagedList<NowPlayingEntity>, MoviesResponse>(appExecutors) {

            override fun loadFromDB(): LiveData<PagedList<NowPlayingEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localRepository.getNowPlaying(), config).build()
            }

            override fun shouldFetch(data: PagedList<NowPlayingEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MoviesResponse>> =
                remoteRepository.getNowPlaying()

            override fun saveCallResult(data: MoviesResponse) {
                val movieList = ArrayList<NowPlayingEntity>()

                data.results?.forEachIndexed { index, movies ->
                    val movie = NowPlayingEntity(
                        movieId = movies?.id,
                        movieDesc = movies?.overview,
                        movieImage = movies?.posterPath,
                        movieLanguage = movies?.originalLanguage,
                        movieName = movies?.title,
                        movieRating = movies?.voteAverage.toString(),
                        movieReleaseDate = movies?.releaseDate
                    )
                    movieList.add(movie)
                }
                localRepository.insertBuckNowPlaying(movieList)
            }

        }.asLiveData()
    }

    override fun getDetailMovies(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, DetailMovieResponse>(appExecutors) {

            override fun loadFromDB(): LiveData<MovieEntity> {
                return localRepository.getMovie(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null || data.genre.isNullOrEmpty() || data.duration.isNullOrEmpty() || data.homepage.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<DetailMovieResponse>> =
                remoteRepository.getDetailMovie(movieId)

            override fun saveCallResult(data: DetailMovieResponse) {
                val genres = StringBuilder()
                data.genres?.forEach {
                    genres
                        .append(it?.name)
                        .append(", ")
                }
                val movie = MovieEntity(
                    movieId = data.id,
                    movieDesc = data.overview,
                    movieImage = data.posterPath,
                    movieLanguage = data.originalLanguage,
                    movieName = data.title,
                    movieRating = data.voteAverage.toString(),
                    movieReleaseDate = data.releaseDate,
                    homepage = data.homepage,
                    duration = data.runtime.toString(),
                    genre = genres.toString()
                )
                localRepository.insertMovie(movie)
            }
        }.asLiveData()
    }

    override fun getReview(movieId: Int): LiveData<Resource<PagedList<ReviewEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ReviewEntity>, ReviewsResponse>(appExecutors) {

            override fun loadFromDB(): LiveData<PagedList<ReviewEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()
                return LivePagedListBuilder(localRepository.getReviews(movieId), config).build()
            }

            override fun shouldFetch(data: PagedList<ReviewEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<ReviewsResponse>> =
                remoteRepository.getReview(movieId)

            override fun saveCallResult(data: ReviewsResponse) {
                val reviewList = ArrayList<ReviewEntity>()

                data.results?.forEachIndexed { index, reviews ->
                    val review = ReviewEntity(
                        id = reviews?.id,
                        name = reviews?.authorDetails?.name,
                        movieId = movieId,
                        username = reviews?.authorDetails?.username,
                        author = reviews?.author,
                        avatarPath = reviews?.authorDetails?.avatarPath,
                        content = reviews?.content,
                        createdAt = reviews?.createdAt,
                        rating = reviews?.authorDetails?.rating
                    )
                    reviewList.add(review)
                }
                localRepository.insertBuckReview(reviewList)
            }

        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<FavoriteEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()
        return LivePagedListBuilder(localRepository.getFavorite(), config).build()
    }

    override fun setFavoriteMovie(movie: FavoriteEntity) {
        appExecutors.diskIO().execute {
            localRepository.insertFavorite(movie)
        }
    }

    override fun unSetFavoriteMovie(movie: FavoriteEntity) {
        appExecutors.diskIO().execute {
            localRepository.removeFavorite(movie)
        }
    }
}