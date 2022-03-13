package com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.popularMoviesAdapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.databinding.ItemPopularMovieBinding
import com.omurgun.moviesfromtmdb.ui.fragments.PopularMoviesFragmentDirections


class PopularMovieAdapter() : RecyclerView.Adapter<PopularMovieViewHolder>()
{
    var popularMovies = listOf<ResponseMovie>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
      return PopularMovieViewHolder(ItemPopularMovieBinding.inflate(
          LayoutInflater.from(parent.context),
          parent,
          false
      ))
    }

    override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
        holder.bind(popularMovies[position])

        holder.itemView.setOnClickListener {
            val action = PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailFragment(popularMovies[position].id)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return popularMovies.size
    }


}