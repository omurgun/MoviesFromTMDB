package com.omurgun.moviesfromtmdb.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
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
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieVideos
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetSimilarMovies
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovie
import com.omurgun.moviesfromtmdb.data.models.response.ResponseMovieImage
import com.omurgun.moviesfromtmdb.databinding.FragmentMovieDetailBinding
import com.omurgun.moviesfromtmdb.ui.adapters.recyclerViewAdapter.movieImagesAdapter.VerticalMovieImageAdapter
import com.omurgun.moviesfromtmdb.ui.viewModelFactory.ViewModelFactory
import com.omurgun.moviesfromtmdb.ui.viewModels.MovieDetailViewModel
import com.omurgun.moviesfromtmdb.util.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import javax.inject.Inject


class MovieDetailFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment(){
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val movieDetailViewModel: MovieDetailViewModel by viewModels{viewModelFactory}
    private val verticalMovieImageAdapter : VerticalMovieImageAdapter = VerticalMovieImageAdapter()
    private var movieId : Int? = null
    private var verticalItems = arrayListOf<InternalVerticalMovieItem>()
    private lateinit var currentMovie : ResponseMovie
    private var currentMovieIsFavorite : Boolean = false
    private var countOfRefreshImages : Int = 0
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
        getMovieVideosByMovieIdFromAPI(RequestGetMovieVideos(movieId!!))
        getMovieFromRoom(RequestGetMovieDetail(movieId!!))
        getFavoriteMovieFromRoom(RequestGetMovieDetail(movieId!!))
        getAllSimilarMoviesFromRoom(movieId!!)
        getAllBackdropsFromRoom(movieId!!)
        getAllLogosFromRoom(movieId!!)
        getAllPostersFromRoom(movieId!!)

    }

    private fun initViews(){
        val args by navArgs<MovieDetailFragmentArgs>()
        movieId = args.movieId
        lifecycle.addObserver(binding.youtubePlayerView)



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
            getSimilarMoviesByMovieIdFromAPI(RequestGetSimilarMovies(movieId!!,1))
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

        observeLiveData()
    }

    private fun observeLiveData(){
        movieDetailViewModel.isRefreshImages.observe(viewLifecycleOwner) { result ->
            result?.let {
                if (it) {
                    if (countOfRefreshImages == 1)
                    {
                        getMovieImagesByMovieIdFromAPI(RequestGetMovieImages(movieId!!))
                        movieDetailViewModel.isRefreshImages.value = false
                    }

                }

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
                        deleteAllBackdropsFromRoom(movieId!!)
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(1,"Backdrops"),
                                it.data.backdrops.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data.backdrops
                            )
                        )
                        insertAllBackdropsToRoom(it.data.backdrops,movieId!!)
                    }

                    if (it.data.logos.isNotEmpty())
                    {
                        deleteAllLogosFromRoom(movieId!!)
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(2,"Logos"),
                                it.data.logos.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data.logos
                            )
                        )
                        insertAllLogosToRoom(it.data.logos,movieId!!)
                    }

                    if (it.data.posters.isNotEmpty())
                    {
                        deleteAllPostersFromRoom(movieId!!)
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(3,"Posters"),
                                it.data.posters.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data.posters
                            )
                        )
                        insertAllPostersToRoom(it.data.posters,movieId!!)
                    }

                    countOfRefreshImages = 0



                    verticalMovieImageAdapter.verticalItems = verticalItems


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

    private fun getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies : RequestGetSimilarMovies){
        val data = movieDetailViewModel.getSimilarMoviesByMovieIdFromAPI(requestGetSimilarMovies)

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
                        deleteAllSimilarMoviesFromRoom(movieId!!)
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(4,"Similars"),
                                it.data.similarMovies.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(0,0,data.posterPath)
                                ) },
                                null,it.data.similarMovies
                            )
                        )
                        insertAllSimilarMoviesToRoom(it.data.similarMovies,movieId!!)
                    }


                    verticalMovieImageAdapter.verticalItems = verticalItems


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
                    if (it.data != null)
                    {
                        currentMovie = it.data
                        updateUI(it.data)
                    }
                    else
                    {
                        getSimilarMovieFromRoom(movieId!!)
                    }

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

    private fun getAllSimilarMoviesFromRoom(movieId: Int){
        val data = movieDetailViewModel.getAllSimilarMoviesFromRoom(movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("getAllSimilarMoviesFromRoom : ${it.data}")
                    if (it.data != null && it.data.isNotEmpty())
                    {
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(4,"Similars"),
                                it.data.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(0,0,data.posterPath)
                                ) },
                                null
                            )
                        )
                        verticalMovieImageAdapter.verticalItems = verticalItems
                    }
                    else
                    {
                        getSimilarMoviesByMovieIdFromAPI(RequestGetSimilarMovies(movieId,1))
                    }
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

    private fun getSimilarMovieFromRoom(movieId: Int){
        val data = movieDetailViewModel.getSimilarMovieFromRoom(movieId)

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
                        currentMovie = it.data
                        updateUI(it.data)
                    }
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

    private fun insertAllSimilarMoviesToRoom(similarMovies: List<ResponseMovie>, movieId: Int){
        val data = movieDetailViewModel.insertAllSimilarMoviesToRoom(similarMovies,movieId)

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

                    }
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

    private fun deleteAllSimilarMoviesFromRoom(movieId: Int){
        val data = movieDetailViewModel.deleteAllSimilarMoviesFromRoom(movieId)

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

                    }
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

    private fun getAllBackdropsFromRoom(movieId: Int){
        val data = movieDetailViewModel.getAllBackdropsFromRoom(movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("getAllBackdropsFromRoom : ${it.data}")
                    if (it.data != null && it.data.isNotEmpty())
                    {
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(1,"Backdrops"),
                                it.data.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data
                        ))
                        verticalMovieImageAdapter.verticalItems = verticalItems
                    }
                    else
                    {
                        countOfRefreshImages++
                        movieDetailViewModel.isRefreshImages.value = true
                        //getSimilarMoviesByMovieIdFromAPI(RequestGetSimilarMovies(movieId,1))
                    }
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

    private fun insertAllBackdropsToRoom(backdrops: List<ResponseMovieImage>, movieId: Int){
        val data = movieDetailViewModel.insertAllBackdropsToRoom(backdrops,movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("insertAllBackdropsToRoom : ${it.data}")
                    if (it.data != null)
                    {

                    }
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

    private fun deleteAllBackdropsFromRoom(movieId: Int){
        val data = movieDetailViewModel.deleteAllBackdropsFromRoom(movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("deleteAllBackdropsFromRoom : ${it.data}")
                    if (it.data != null)
                    {

                    }
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

    private fun getAllLogosFromRoom(movieId: Int){
        val data = movieDetailViewModel.getAllLogosFromRoom(movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("getAllBackdropsFromRoom : ${it.data}")
                    if (it.data != null && it.data.isNotEmpty())
                    {
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(2,"Logos"),
                                it.data.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data
                            ))
                        verticalMovieImageAdapter.verticalItems = verticalItems
                    }
                    else
                    {
                        countOfRefreshImages++
                        movieDetailViewModel.isRefreshImages.value = true
                        //getSimilarMoviesByMovieIdFromAPI(RequestGetSimilarMovies(movieId,1))
                    }
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

    private fun insertAllLogosToRoom(logos: List<ResponseMovieImage>, movieId: Int){
        val data = movieDetailViewModel.insertAllLogosToRoom(logos,movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("insertAllLogosToRoom : ${it.data}")
                    if (it.data != null)
                    {

                    }
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

    private fun deleteAllLogosFromRoom(movieId: Int){
        val data = movieDetailViewModel.deleteAllLogosFromRoom(movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("deleteAllBackdropsFromRoom : ${it.data}")
                    if (it.data != null)
                    {

                    }
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

    private fun getAllPostersFromRoom(movieId: Int){
        val data = movieDetailViewModel.getAllPostersFromRoom(movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("getAllBackdropsFromRoom : ${it.data}")
                    if (it.data != null && it.data.isNotEmpty())
                    {
                        verticalItems.add(
                            InternalVerticalMovieItem(
                                InternalTitleItem(3,"Posters"),
                                it.data.map { data -> InternalHorizontalMovieImageItem.MovieImageSmall(
                                    ResponseMovieImage(data.height,data.width,data.path)
                                ) },
                                it.data
                            ))
                        verticalMovieImageAdapter.verticalItems = verticalItems
                    }
                    else
                    {
                        countOfRefreshImages++
                        movieDetailViewModel.isRefreshImages.value = true
                        //getSimilarMoviesByMovieIdFromAPI(RequestGetSimilarMovies(movieId,1))
                    }
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

    private fun insertAllPostersToRoom(posters: List<ResponseMovieImage>, movieId: Int){
        val data = movieDetailViewModel.insertAllPostersToRoom(posters,movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("insertAllPostersToRoom : ${it.data}")
                    if (it.data != null)
                    {

                    }
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

    private fun deleteAllPostersFromRoom(movieId: Int){
        val data = movieDetailViewModel.deleteAllPostersFromRoom(movieId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("deleteAllBackdropsFromRoom : ${it.data}")
                    if (it.data != null)
                    {

                    }
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

    private fun getMovieVideosByMovieIdFromAPI(requestGetMovieVideos : RequestGetMovieVideos){
        val data = movieDetailViewModel.getMovieVideosByMovieIdFromAPI(requestGetMovieVideos)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    binding.movieDetailContainer.makeInVisible()
                    binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("getMovieVideosByMovieIdFromAPI : ${it.data}")
                    if (it.data != null && it.data.results.isNotEmpty())
                    {
                        it.data.results.forEach { video ->
                            if (video.type == "Trailer")
                            {
                                setVideo(key = video.key)
                                return@forEach
                            }
                        }


                    }
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

    private fun setVideo(key : String){
        binding.youtubePlayerView.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(key, 0f)
                    super.onReady(youTubePlayer)

                }

                override fun onError(
                    youTubePlayer: YouTubePlayer,
                    error: PlayerConstants.PlayerError
                ) {

                    println("On Error")
                    super.onError(youTubePlayer, error)
                }
            })
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
        //binding.youtubePlayerView.release()

    }

}