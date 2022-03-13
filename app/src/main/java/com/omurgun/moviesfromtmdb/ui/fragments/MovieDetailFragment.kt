package com.omurgun.moviesfromtmdb.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.omurgun.moviesfromtmdb.R
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.IMAGES_BASE_URL
import com.omurgun.moviesfromtmdb.data.models.internal.InternalHorizontalMovieImageItem
import com.omurgun.moviesfromtmdb.data.models.internal.InternalTitleItem
import com.omurgun.moviesfromtmdb.data.models.internal.InternalVerticalMovieItem
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetPopularMovies
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage
import com.omurgun.moviesfromtmdb.databinding.FragmentMovieDetailBinding
import com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.movieImagesAdapter.VerticalMovieImageAdapter
import com.omurgun.moviesfromtmdb.ui.viewModelFactory.ViewModelFactory
import com.omurgun.moviesfromtmdb.ui.viewModels.MovieDetailViewModel
import com.omurgun.moviesfromtmdb.util.*
import javax.inject.Inject


class MovieDetailFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val movieDetailViewModel: MovieDetailViewModel by viewModels{viewModelFactory}
    private val verticalMovieImageAdapter : VerticalMovieImageAdapter = VerticalMovieImageAdapter()
    private var movieId : Int? = null
    private var verticalItems = arrayListOf<InternalVerticalMovieItem>()
    private lateinit var currentMovie : ResponseMovie
    private var currentMovieIsFavorite : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        getMovieFromRoom(RequestGetMovieDetail(movieId!!))
        getFavoriteMovieFromRoom(RequestGetMovieDetail(movieId!!))
        //getMovieDetailFromAPI(RequestGetMovieDetail(movieId!!))
        //getMovieImagesByMovieIdFromAPI(RequestGetMovieImages(movieId!!))
        //getMovieImagesByMovieIdFromAPI(RequestGetSimilarMovies(movieId!!,1))

    }

    private fun initViews(){
        val args by navArgs<MovieDetailFragmentArgs>()
        movieId = args.movieId

        binding.movieImagesRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = verticalMovieImageAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            verticalItems.clear()
            getMovieDetailFromAPI(RequestGetMovieDetail(movieId!!))
            getMovieImagesByMovieIdFromAPI(RequestGetMovieImages(movieId!!))
            getMovieImagesByMovieIdFromAPI(RequestGetSimilarMovies(movieId!!,1))
        }

        binding.favoriteImage.setOnClickListener {
            if(currentMovieIsFavorite)
            {
                deleteFavoriteMovieFromRoom(currentMovie)
            }
            else
            {

                saveFavoriteMovieToRoom(currentMovie)
            }

        }
    }


    private fun getMovieDetailFromAPI(requestMovieDetail: RequestGetMovieDetail){
        val data = movieDetailViewModel.getMovieDetailFromAPI(requestMovieDetail)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {

                    println("Success")
                    println("data : ${it.data}")
                    currentMovie = it.data!!

                    updateUI(it.data)


                    Toast.makeText(requireContext(),"get movie from API", Toast.LENGTH_SHORT).show()
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun getMovieImagesByMovieIdFromAPI(requestGetMovieImages : RequestGetMovieImages){
        val data = movieDetailViewModel.getMovieImagesByMovieIdFromAPI(requestGetMovieImages)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")

                    if (it.data?.backdrops!!.isNotEmpty())
                    {
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(1,"Backdrops"),
                                it.data.backdrops.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data.backdrops
                            )
                        )
                    }

                    if (it.data.logos.isNotEmpty())
                    {
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(2,"Logos"),
                                it.data.logos.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data.logos
                            )
                        )
                    }

                    if (it.data.posters.isNotEmpty())
                    {
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(3,"Posters"),
                                it.data.posters.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data.posters
                            )
                        )
                    }



                    verticalMovieImageAdapter.verticalItems = verticalItems

                    Toast.makeText(requireContext(),"get movie images from API", Toast.LENGTH_SHORT).show()

                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun getMovieImagesByMovieIdFromAPI(requestGetSimilarMovies : RequestGetSimilarMovies){
        val data = movieDetailViewModel.getMovieImagesByMovieIdFromAPI(requestGetSimilarMovies)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")

                    if (it.data?.similarMovies!!.isNotEmpty())
                    {
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(4,"Similars"),
                                it.data.similarMovies.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(0,0,data.posterPath)
                                ) },
                                null
                            )
                        )
                    }


                    verticalMovieImageAdapter.verticalItems = verticalItems

                    Toast.makeText(requireContext(),"get similar movie from API", Toast.LENGTH_SHORT).show()

                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun saveFavoriteMovieToRoom(movie : ResponseMovie){
        val data = movieDetailViewModel.saveFavoriteMovieToRoom(movie)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")
                    binding.favoriteImage.setImageResource(R.drawable.ic_baseline_fill_white_favorite_24)
                    Toast.makeText(requireContext(),"save movie to room", Toast.LENGTH_SHORT).show()
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun getMovieFromRoom(requestMovieDetail: RequestGetMovieDetail){
        val data = movieDetailViewModel.getMovieFromRoom(requestMovieDetail)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("getMovieFromRoom : ${it.data}")
                    currentMovie = it.data!!
                    updateUI(it.data)
                    Toast.makeText(requireContext(),"get movie from room", Toast.LENGTH_SHORT).show()
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun getFavoriteMovieFromRoom(requestMovieDetail: RequestGetMovieDetail){
        val data = movieDetailViewModel.getFavoriteMovieFromRoom(requestMovieDetail)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("getFavoriteMovieFromRoom : ${it.data}")
                    if (it.data != null)
                    {
                        binding.favoriteImage.setImageResource(R.drawable.ic_baseline_fill_white_favorite_24)
                        currentMovieIsFavorite = true
                    }
                    Toast.makeText(requireContext(),"get movie from room", Toast.LENGTH_SHORT).show()
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun deleteFavoriteMovieFromRoom(movie : ResponseMovie){
        val data = movieDetailViewModel.deleteFavoriteMovieFromRoom(movie)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("deleteFavoriteMovieFromRoom : ${it.data}")
                    if (it.data != null)
                    {
                        binding.favoriteImage.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        currentMovieIsFavorite = false
                    }
                    Toast.makeText(requireContext(),"delete movie from room", Toast.LENGTH_SHORT).show()
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    binding.movieDetailLoading.makeGone()
                    binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun updateUI(responseMovie : ResponseMovie){
        binding.movieImage.downloadFromUrl(IMAGES_BASE_URL + responseMovie.posterPath,null)
        binding.movieTitle.text = responseMovie.title
        binding.movieReleaseDate.text = responseMovie.releaseDate
        binding.movieAverageVote.text = responseMovie.averageVote.toString()
        binding.movieDescription.text = responseMovie.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}