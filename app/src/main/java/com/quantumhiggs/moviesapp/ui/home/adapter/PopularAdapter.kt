package com.quantumhiggs.moviesapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quantumhiggs.moviesapp.BuildConfig
import com.quantumhiggs.moviesapp.R
import com.quantumhiggs.moviesapp.data.source.local.entity.MovieEntity
import com.quantumhiggs.moviesapp.data.source.local.entity.PopularEntity
import com.quantumhiggs.moviesapp.ui.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_movie_card.view.*

class PopularAdapter : PagedListAdapter<PopularEntity, PopularAdapter.ViewHolder>(DiffCallback) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<PopularEntity>() {
            override fun areItemsTheSame(oldItem: PopularEntity, newItem: PopularEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: PopularEntity, newItem: PopularEntity): Boolean {
                val old = oldItem as MovieEntity
                val new = newItem as MovieEntity
                return old.movieId == new.movieId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bindItems(movie)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(movie: PopularEntity) {
            itemView.card_tv_title.text = movie.movieName
            itemView.card_tv_release.text = movie.movieRating
            itemView.card_tv_overview.text = movie.movieDesc

            Glide.with(itemView.context)
                .load(BuildConfig.BASE_IMG + movie.movieImage)
                .into(itemView.card_image)

            itemView.setOnClickListener {
                val directions =
                    HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(movie.movieId!!)
                it.findNavController().navigate(directions)
            }
        }
    }
}