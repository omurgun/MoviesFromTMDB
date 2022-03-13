package com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.popularMoviesAdapter

import androidx.recyclerview.widget.RecyclerView
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.IMAGES_BASE_URL
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.databinding.ItemPopularMovieBinding
import com.omurgun.moviesfromtmdb.util.downloadFromUrl


class PopularMovieViewHolder(private val binding: ItemPopularMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: ResponseMovie
    ) {

        binding.movieImage.downloadFromUrl(IMAGES_BASE_URL + movie.posterPath,null)
    }
}