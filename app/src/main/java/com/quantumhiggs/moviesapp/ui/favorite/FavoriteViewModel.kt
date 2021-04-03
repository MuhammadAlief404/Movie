package com.quantumhiggs.moviesapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.quantumhiggs.moviesapp.data.MoviesRepository
import com.quantumhiggs.moviesapp.data.source.local.entity.FavoriteEntity

class FavoriteViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    fun getFavorite(): LiveData<PagedList<FavoriteEntity>> = moviesRepository.getFavoriteMovies()
}