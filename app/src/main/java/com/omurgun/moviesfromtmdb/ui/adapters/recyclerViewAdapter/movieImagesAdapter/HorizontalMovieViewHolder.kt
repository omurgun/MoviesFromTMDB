package com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.movieImagesAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.IMAGES_BASE_URL
import com.omurgun.moviesfromtmdb.data.models.internal.InternalHorizontalMovieImageItem
import com.omurgun.moviesfromtmdb.databinding.ItemMovieLargeBinding
import com.omurgun.moviesfromtmdb.databinding.ItemMovieMediumBinding
import com.omurgun.moviesfromtmdb.databinding.ItemMovieSmallBinding
import com.omurgun.moviesfromtmdb.databinding.ItemMovieSmallViewAllBinding
import com.omurgun.moviesfromtmdb.util.downloadFromUrl

sealed class HorizontalMovieViewHolder (binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class MovieSmallViewHolder(private val binding: ItemMovieSmallBinding) : HorizontalMovieViewHolder(binding){
        fun bind(movieImageSmall: InternalHorizontalMovieImageItem.MovieImageSmall){
            binding.gameImageView.clipToOutline = true
            binding.gameImageView.downloadFromUrl(IMAGES_BASE_URL + movieImageSmall.movieImage.path, null)
        }
    }

    class MovieSmallViewAllViewHolder(private val binding: ItemMovieSmallViewAllBinding) : HorizontalMovieViewHolder(binding){
        fun bind(movieImageSmall: InternalHorizontalMovieImageItem.MovieImageSmallViewAll){
            binding.gameImageView.clipToOutline = true
            binding.gameImageView.downloadFromUrl(IMAGES_BASE_URL + movieImageSmall.movieImage.path,null)
        }
    }

    class MovieMediumViewHolder(private val binding: ItemMovieMediumBinding) : HorizontalMovieViewHolder(binding){
        fun bind(movieImageMedium: InternalHorizontalMovieImageItem.MovieImageMedium){
            binding.gameImageView.clipToOutline = true
            binding.gameImageView.downloadFromUrl(IMAGES_BASE_URL + movieImageMedium.movieImage.path,null)
        }
    }

    class MovieLargeViewHolder(private val binding: ItemMovieLargeBinding) : HorizontalMovieViewHolder(binding){
        fun bind(movieImageLarge: InternalHorizontalMovieImageItem.MovieImageLarge){
            binding.gameImageView.clipToOutline = true
            binding.gameImageView.downloadFromUrl(IMAGES_BASE_URL + movieImageLarge.movieImage.path,null)

        }
    }


}