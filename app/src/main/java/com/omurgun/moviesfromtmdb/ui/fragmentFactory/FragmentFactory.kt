package com.omurgun.moviesfromtmdb.ui.fragmentFactory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.omurgun.moviesfromtmdb.ui.fragments.FavoriteMoviesFragment
import com.omurgun.moviesfromtmdb.ui.fragments.MovieDetailFragment
import com.omurgun.moviesfromtmdb.ui.fragments.PopularMoviesFragment
import com.omurgun.moviesfromtmdb.ui.viewModelFactory.ViewModelFactory
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val viewModelFactory: ViewModelFactory


) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            PopularMoviesFragment::class.java.name -> PopularMoviesFragment(viewModelFactory)
            MovieDetailFragment::class.java.name -> MovieDetailFragment(viewModelFactory)
            FavoriteMoviesFragment::class.java.name -> FavoriteMoviesFragment(viewModelFactory)


            else -> super.instantiate(classLoader, className)
        }
    }
}