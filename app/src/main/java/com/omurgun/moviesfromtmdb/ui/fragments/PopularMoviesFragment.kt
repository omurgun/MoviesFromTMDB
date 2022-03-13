package com.omurgun.moviesfromtmdb.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.databinding.FragmentPopularMoviesBinding
import com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.popularMoviesAdapter.PopularMovieAdapter
import com.omurgun.moviesfromtmdb.ui.viewModelFactory.ViewModelFactory
import com.omurgun.moviesfromtmdb.ui.viewModels.PopularMoviesViewModel
import com.omurgun.moviesfromtmdb.util.ResultData
import com.omurgun.moviesfromtmdb.util.makeGone
import com.omurgun.moviesfromtmdb.util.makeInVisible
import com.omurgun.moviesfromtmdb.util.makeVisible
import javax.inject.Inject


class PopularMoviesFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!
    private val popularMoviesViewModel: PopularMoviesViewModel by viewModels{viewModelFactory}
    private val popularMovieAdapter: PopularMovieAdapter = PopularMovieAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        getAllMoviesFromRoom()
    }

    private fun initViews(){
        binding.popularMoviesRv.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = popularMovieAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            getPopularMoviesFromAPI(RequestGetPopularMovies(1))
        }
    }

    private fun getPopularMoviesFromAPI(requestGetPopularMovies: RequestGetPopularMovies){
        val data = popularMoviesViewModel.getPopularMoviesFromAPI(requestGetPopularMovies)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.popularMoviesLoading.makeVisible()
                    binding.popularMoviesContainer.makeInVisible()
                }
                is ResultData.Success -> {

                    println("Success")
                    println("data : ${it.data}")
                    Toast.makeText(requireContext(),"movies get from API",Toast.LENGTH_SHORT).show()
                    popularMovieAdapter.popularMovies = it.data?.popularMovies!!
                    savePopularMoviesFromRoom(it.data.popularMovies)
                    binding.popularMoviesLoading.makeGone()
                    binding.popularMoviesContainer.makeVisible()
                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.popularMoviesLoading.makeGone()
                    binding.popularMoviesContainer.makeVisible()

                }
            }
        }

    }
    private fun savePopularMoviesFromRoom(movies : List<ResponseMovie>){
        val data = popularMoviesViewModel.savePopularMoviesFromRoom(movies)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                }
                is ResultData.Success -> {
                    println("Success")
                    Toast.makeText(requireContext(),"movies added to room",Toast.LENGTH_SHORT).show()
                    println("data : ${it.data}")

                }
                is ResultData.Exception -> {
                    println("Exception")

                }
            }
        }

    }
    private fun getAllMoviesFromRoom(){
        val data = popularMoviesViewModel.getAllMoviesFromRoom()

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    binding.popularMoviesLoading.makeVisible()
                    binding.popularMoviesContainer.makeInVisible()
                    println("loading")
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")
                    Toast.makeText(requireContext(),"movies get from room",Toast.LENGTH_SHORT).show()
                    popularMovieAdapter.popularMovies = it.data!!
                    binding.popularMoviesLoading.makeGone()
                    binding.popularMoviesContainer.makeVisible()


                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.popularMoviesLoading.makeGone()
                    binding.popularMoviesContainer.makeVisible()

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}