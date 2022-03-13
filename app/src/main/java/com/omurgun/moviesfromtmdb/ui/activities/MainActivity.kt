package com.omurgun.moviesfromtmdb.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.omurgun.moviesfromtmdb.R
import com.omurgun.moviesfromtmdb.databinding.ActivityMainBinding
import com.omurgun.moviesfromtmdb.ui.fragmentFactory.FragmentFactoryEntryPoint
import com.omurgun.moviesfromtmdb.util.Util
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromActivity(
            this,
            FragmentFactoryEntryPoint::class.java
        )
        supportFragmentManager.fragmentFactory = entryPoint.getFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.fullScreenActivity(window)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        binding.bottomNavigationView.selectedItemId = R.id.navigation_home


        binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.popularMoviesFragment)
                    binding.bottomNavigationView.menu.getItem(0).isChecked = true

                }
                R.id.navigation_favorite -> {
                    navController.navigate(R.id.favoriteMoviesFragment)
                    binding.bottomNavigationView.menu.getItem(1).isChecked = true

                }
            }
            false

        }
    }

    private fun setCurrentBottomTab(){
        when (navController.currentBackStackEntry?.destination?.label) {
            "fragment_popular_movies" -> {

                binding.bottomNavigationView.menu.getItem(0).isChecked = true

            }
            "fragment_favorite_movies" -> {

                binding.bottomNavigationView.menu.getItem(1).isChecked = true

            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setCurrentBottomTab()
    }
}