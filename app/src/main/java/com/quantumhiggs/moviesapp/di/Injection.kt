package com.quantumhiggs.moviesapp.di

import android.content.Context
import com.quantumhiggs.moviesapp.data.MoviesRepository
import com.quantumhiggs.moviesapp.data.source.local.LocalRepository
import com.quantumhiggs.moviesapp.data.source.local.db.MoviesDatabase
import com.quantumhiggs.moviesapp.data.source.remote.RemoteRepository
import com.quantumhiggs.moviesapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MoviesRepository {
        val database = MoviesDatabase.getInstance(context)

        val localRepository = LocalRepository.getInstance(database.movieDao())
        val remoteRepository = RemoteRepository()
        val appExecutors = AppExecutors()

        return MoviesRepository.getInstance(remoteRepository, localRepository, appExecutors)!!
    }
}