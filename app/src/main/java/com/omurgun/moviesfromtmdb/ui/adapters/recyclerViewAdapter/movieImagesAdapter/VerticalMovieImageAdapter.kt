package com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.movieImagesAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omurgun.moviesfromtmdb.R
import com.omurgun.moviesfromtmdb.data.models.internal.InternalVerticalMovieItem
import com.omurgun.moviesfromtmdb.databinding.ItemPopularMovieBinding
import com.omurgun.moviesfromtmdb.databinding.ItemVerticalMovieImageBinding
import com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.popularMoviesAdapter.PopularMovieViewHolder


class VerticalMovieImageAdapter() : RecyclerView.Adapter<VerticalMovieViewHolder>()
{
    var verticalItems = listOf<InternalVerticalMovieItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalMovieViewHolder {
        return VerticalMovieViewHolder(ItemVerticalMovieImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: VerticalMovieViewHolder, position: Int) {

        holder.bind(verticalItems[position])

//        holder.itemView.setOnClickListener {
//            val action = PopularMoviesFragmentDirections.actionPopularMoviesFragmentToMovieDetailFragment(popularMovies[position].id)
//            Navigation.findNavController(it).navigate(action)
//        }

    }

    override fun getItemCount(): Int {
        return verticalItems.size
    }


}