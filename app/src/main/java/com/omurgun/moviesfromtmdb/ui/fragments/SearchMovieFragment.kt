package com.omurgun.moviesfromtmdb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.omurgun.moviesfromtmdb.R
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestSearchMovie
import com.omurgun.moviesfromtmdb.databinding.FragmentFavoriteMoviesBinding
import com.omurgun.moviesfromtmdb.databinding.FragmentSearchMovieBinding
import com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.popularMoviesAdapter.PopularMovieAdapter
import com.omurgun.moviesfromtmdb.ui.viewModelFactory.ViewModelFactory
import com.omurgun.moviesfromtmdb.ui.viewModels.MovieDetailViewModel
import com.omurgun.moviesfromtmdb.ui.viewModels.SearchMovieViewModel
import com.omurgun.moviesfromtmdb.util.ResultData
import com.omurgun.moviesfromtmdb.util.makeGone
import com.omurgun.moviesfromtmdb.util.makeInVisible
import com.omurgun.moviesfromtmdb.util.makeVisible
import javax.inject.Inject


class SearchMovieFragment  @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment() {
    private var _binding: FragmentSearchMovieBinding? = null
    private val binding get() = _binding!!

    private val searchMovieViewModel: SearchMovieViewModel by viewModels{viewModelFactory}
    private val popularMovieAdapter: PopularMovieAdapter = PopularMovieAdapter()
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

        initViews()
        searchMoviesFromAPI(RequestSearchMovie("fast and",1))
    }

    private fun initViews(){
        binding.moviesRv.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = popularMovieAdapter
        }
        popularMovieAdapter.isSearch = true
    }

    private fun searchMoviesFromAPI(requestSearchMovie: RequestSearchMovie){
        val data = searchMovieViewModel.searchMoviesFromAPI(requestSearchMovie)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")

                }
                is ResultData.Success -> {

                    println("Success")
                    println("data : ${it.data}")


                    popularMovieAdapter.popularMovies = it.data?.popularMovies!!


                }
                is ResultData.Exception -> {
                    println("Exception")


                }
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}