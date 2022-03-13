package com.omurgun.moviesfromtmdb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.omurgun.moviesfromtmdb.databinding.FragmentFavoriteMoviesBinding
import com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.favoriteMoviesAdapter.FavoriteMovieAdapter
import com.omurgun.moviesfromtmdb.ui.viewModelFactory.ViewModelFactory
import com.omurgun.moviesfromtmdb.ui.viewModels.FavoriteMoviesViewModel
import com.omurgun.moviesfromtmdb.util.ResultData
import com.omurgun.moviesfromtmdb.util.makeGone
import com.omurgun.moviesfromtmdb.util.makeInVisible
import com.omurgun.moviesfromtmdb.util.makeVisible
import javax.inject.Inject


class FavoriteMoviesFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment() {
    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!
    private val favoriteMoviesViewModel: FavoriteMoviesViewModel by viewModels{viewModelFactory}
    private val favoriteMovieAdapter: FavoriteMovieAdapter = FavoriteMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        getAllFavoriteMoviesFromRoom()
    }

    private fun initViews(){
        binding.favoriteMoviesRv.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = favoriteMovieAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            getAllFavoriteMoviesFromRoom()
        }
    }

    private fun getAllFavoriteMoviesFromRoom(){
        val data = favoriteMoviesViewModel.getAllFavoriteMoviesFromRoom()

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.favoriteMoviesContainer.makeInVisible()
                    binding.favoriteMoviesLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")
                    favoriteMovieAdapter.favoriteMovies = it.data!!
                    Toast.makeText(requireContext(),"get favorites movies from room", Toast.LENGTH_SHORT).show()
                    binding.favoriteMoviesLoading.makeGone()
                    binding.favoriteMoviesContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.favoriteMoviesLoading.makeGone()
                    binding.favoriteMoviesContainer.makeVisible()

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}