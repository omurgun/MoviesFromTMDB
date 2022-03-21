package com.omurgun.moviesfromtmdb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.omurgun.moviesfromtmdb.R
import com.omurgun.moviesfromtmdb.databinding.FragmentFavoriteMoviesBinding
import com.omurgun.moviesfromtmdb.databinding.FragmentSearchMovieBinding
import com.omurgun.moviesfromtmdb.ui.viewModelFactory.ViewModelFactory
import javax.inject.Inject


class SearchMovieFragment  @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment() {
    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMovieBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}