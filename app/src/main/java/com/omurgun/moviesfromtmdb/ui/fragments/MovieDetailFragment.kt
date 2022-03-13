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
import com.omurgun.moviesfromtmdb.application.constants.NetworkConstants.IMAGES_BASE_URL
import com.omurgun.moviesfromtmdb.data.models.internal.InternalHorizontalMovieImageItem
import com.omurgun.moviesfromtmdb.data.models.internal.InternalTitleItem
import com.omurgun.moviesfromtmdb.data.models.internal.InternalVerticalMovieItem
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieDetail
import com.omurgun.moviesfromtmdb.data.models.request.RequestGetMovieImages
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
        getMovieDetailFromAPI(RequestGetMovieDetail(movieId!!))
        getMovieImagesByMovieIdFromAPI(RequestGetMovieImages(movieId!!))


    }

    private fun initViews(){
        val args by navArgs<MovieDetailFragmentArgs>()
        movieId = args.movieId

        binding.movieImagesRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = verticalMovieImageAdapter
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

                    binding.movieImage.downloadFromUrl(IMAGES_BASE_URL + it.data?.posterPath,null)
                    binding.movieTitle.text = it.data?.title
                    binding.movieReleaseDate.text = it.data?.releaseDate
                    binding.movieAverageVote.text = it.data?.averageVote.toString()
                    binding.movieDescription.text = it.data?.description


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}