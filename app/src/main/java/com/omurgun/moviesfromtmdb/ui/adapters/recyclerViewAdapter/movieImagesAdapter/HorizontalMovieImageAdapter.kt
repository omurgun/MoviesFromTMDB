package com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.movieImagesAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.omurgun.moviesfromtmdb.R
import com.omurgun.moviesfromtmdb.data.models.internal.InternalHorizontalMovieImageItem
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage
import com.omurgun.moviesfromtmdb.databinding.*
import com.omurgun.moviesfromtmdb.ui.fragments.MovieDetailFragmentDirections
import retrofit2.Response

class HorizontalMovieImageAdapter() : RecyclerView.Adapter<HorizontalMovieViewHolder>()
{
    var items = listOf<InternalHorizontalMovieImageItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var movieImages = listOf<ResponseMovieImage>()
    var similarMovies = listOf<ResponseMovie>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalMovieViewHolder {
        return when(viewType){
            R.layout.item_movie_small -> HorizontalMovieViewHolder.MovieSmallViewHolder(
                ItemMovieSmallBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.item_movie_small_view_all -> HorizontalMovieViewHolder.MovieSmallViewAllViewHolder(
                ItemMovieSmallViewAllBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_movie_medium -> HorizontalMovieViewHolder.MovieMediumViewHolder(
                ItemMovieMediumBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_movie_large -> HorizontalMovieViewHolder.MovieLargeViewHolder(
                ItemMovieLargeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }







    }

    override fun onBindViewHolder(holder: HorizontalMovieViewHolder, position: Int) {
        when(holder){
            is HorizontalMovieViewHolder.MovieSmallViewHolder -> holder.bind(items[position] as InternalHorizontalMovieImageItem.MovieImageSmall)
            is HorizontalMovieViewHolder.MovieSmallViewAllViewHolder -> holder.bind(items[position] as InternalHorizontalMovieImageItem.MovieImageSmallViewAll)
            is HorizontalMovieViewHolder.MovieMediumViewHolder -> holder.bind(items[position] as InternalHorizontalMovieImageItem.MovieImageMedium)
            is HorizontalMovieViewHolder.MovieLargeViewHolder -> holder.bind(items[position] as InternalHorizontalMovieImageItem.MovieImageLarge)
        }

        holder.itemView.setOnClickListener {
            println("similarMoviesSize : ${similarMovies.size}")
            println("movieImagesSize : ${movieImages.size}")
            if (similarMovies.isNotEmpty())
            {
                val action = MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(similarMovies[position].id)
                Navigation.findNavController(it).navigate(action)
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is InternalHorizontalMovieImageItem.MovieImageSmall -> R.layout.item_movie_small
            is InternalHorizontalMovieImageItem.MovieImageSmallViewAll -> R.layout.item_movie_small_view_all
            is InternalHorizontalMovieImageItem.MovieImageMedium -> R.layout.item_movie_medium
            is InternalHorizontalMovieImageItem.MovieImageLarge -> R.layout.item_movie_large
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}