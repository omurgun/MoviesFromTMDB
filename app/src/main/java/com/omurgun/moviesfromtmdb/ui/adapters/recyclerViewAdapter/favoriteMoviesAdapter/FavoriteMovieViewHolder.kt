package com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.favoriteMoviesAdapter

import androidx.recyclerview.widget.RecyclerView
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.databinding.ItemPopularMovieBinding
import com.omurgun.moviesfromtmdb.util.downloadFromUrl

class FavoriteMovieViewHolder(private val binding: ItemPopularMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: ResponseMovie
    ) {

        binding.movieImage.downloadFromUrl(NetworkConstants.IMAGES_BASE_URL + movie.posterPath,null)
    }
}