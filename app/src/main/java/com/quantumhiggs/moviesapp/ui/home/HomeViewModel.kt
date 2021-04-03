package com.quantumhiggs.moviesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.quantumhiggs.moviesapp.data.MoviesRepository
import com.quantumhiggs.moviesapp.data.source.local.entity.NowPlayingEntity
import com.quantumhiggs.moviesapp.data.source.local.entity.PopularEntity
import com.quantumhiggs.moviesapp.data.source.local.entity.TopRatedEntity
import com.quantumhiggs.moviesapp.vo.Resource

class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun getPopular(): LiveData<Resource<PagedList<PopularEntity>>> = moviesRepository.getPopular()

    fun getTopRated(): LiveData<Resource<PagedList<TopRatedEntity>>> = moviesRepository.getTopRated()

    fun getNowPlaying(): LiveData<Resource<PagedList<NowPlayingEntity>>> = moviesRepository.getNowPlaying()
}