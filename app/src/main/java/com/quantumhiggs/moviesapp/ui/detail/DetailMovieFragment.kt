package com.quantumhiggs.moviesapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.quantumhiggs.moviesapp.BuildConfig
import com.quantumhiggs.moviesapp.R
import com.quantumhiggs.moviesapp.data.source.local.entity.FavoriteEntity
import com.quantumhiggs.moviesapp.databinding.FragmentDetailMovieBinding
import com.quantumhiggs.moviesapp.utils.ViewModelFactory

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args by navArgs<DetailMovieFragmentArgs>()

        binding.rvReview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val adapter = ReviewAdapter()

        viewModel.setSelectedMovieId(args.movieId)

        binding.rvReview.adapter = adapter

        viewModel.getDetailMovie().observe(viewLifecycleOwner, Observer {
            val movie = it.data
            Glide.with(this)
                .load(BuildConfig.BASE_IMG + movie?.movieImage)
                .into(binding.detailImage)

            binding.detailTvOverview.text = movie?.movieDesc
            binding.detailTvRelease.text = movie?.movieReleaseDate
            binding.detailTvTitle.text = movie?.movieName

            binding.detailBtnFav.setOnClickListener {
                viewModel.setFavorite(movie)
            }
        })

        viewModel.getReview().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.data)
            adapter.notifyDataSetChanged()
        })
    }
}