package com.quantumhiggs.moviesapp.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quantumhiggs.moviesapp.BuildConfig
import com.quantumhiggs.moviesapp.R
import com.quantumhiggs.moviesapp.data.source.local.entity.ReviewEntity
import kotlinx.android.synthetic.main.item_movie_card.view.*
import kotlinx.android.synthetic.main.item_review_card.view.*

class ReviewAdapter :
    PagedListAdapter<ReviewEntity, ReviewAdapter.ViewHolder>(DiffCallback) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<ReviewEntity>() {
            override fun areItemsTheSame(
                oldItem: ReviewEntity,
                newItem: ReviewEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ReviewEntity,
                newItem: ReviewEntity
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_review_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = getItem(position)
        if (review != null) {
            holder.bindItems(review)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(review: ReviewEntity) {
            itemView.card_tv_username.text = review.username
            itemView.card_tv_content.text = review.content
            itemView.card_tv_date.text = review.createdAt

            Glide.with(itemView.context)
                .load(BuildConfig.BASE_IMG + review.avatarPath)
                .into(itemView.card_image)
        }
    }
}