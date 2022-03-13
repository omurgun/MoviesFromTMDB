package com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.favoriteMoviesAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.databinding.ItemPopularMovieBinding
import com.omurgun.moviesfromtmdb.ui.fragments.FavoriteMoviesFragmentDirections

class FavoriteMovieAdapter: RecyclerView.Adapter<FavoriteMovieViewHolder>()
{
    var favoriteMovies = listOf<ResponseMovie>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        return FavoriteMovieViewHolder(
            ItemPopularMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(favoriteMovies[position])

        holder.itemView.setOnClickListener {
            val action = FavoriteMoviesFragmentDirections.actionFavoriteMoviesFragmentToMovieDetailFragment(favoriteMovies[position].id)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return favoriteMovies.size
    }


}