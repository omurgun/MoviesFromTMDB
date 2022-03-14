package com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.movieImagesAdapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants
import com.omurgun.moviesfromtmdb.data.models.internal.InternalVerticalMovieItem
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.databinding.*
import com.omurgun.moviesfromtmdb.util.downloadFromUrl

class VerticalMovieViewHolder (private val binding: ItemVerticalMovieImageBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var horizontalMovieImageAdapter : HorizontalMovieImageAdapter

    fun bind(movieImageItem : InternalVerticalMovieItem){
        horizontalMovieImageAdapter = HorizontalMovieImageAdapter()
        binding.movieImagesRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(binding.root.context,
                LinearLayoutManager.HORIZONTAL,false)
            adapter = horizontalMovieImageAdapter
        }
        horizontalMovieImageAdapter.items = movieImageItem.horizontalMovieImageItems
        horizontalMovieImageAdapter.movieImages = movieImageItem.movieImages ?: listOf()
        horizontalMovieImageAdapter.similarMovies = movieImageItem.similarMovies ?: listOf()
        binding.title.titleTextView.text = movieImageItem.titleItem.title
    }

}