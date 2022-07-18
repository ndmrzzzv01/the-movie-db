package com.example.themovies.screens.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.example.themovies.R
import com.example.themovies.databinding.ActivityMainBinding
import com.example.themovies.network.data.Record
import com.example.themovies.network.data.RecordClick
import com.example.themovies.screens.movie.popular.MovieFragmentDirections
import com.example.themovies.screens.people.PeopleFragment
import com.example.themovies.screens.people.PeopleFragmentDirections
import com.example.themovies.screens.tv.TvFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity(), RecordClick, Loading {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view.setOnClickListener { }

        setupNavigationDrawer()

    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.view.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.view.visibility = View.GONE
    }

    override fun onRecordClickListener(id: Int, type: Record, customParameter: Any?) {
        when (type) {
            Record.Movie -> {
                navController.navigate(
                    directions = MovieFragmentDirections.actionMainFragmentToDetailsFragment(
                        id
                    )
                )
            }
            Record.TV -> {
                navController.navigate(
                    directions = TvFragmentDirections.actionTvFragmentToDetailsTvFragment(
                        id
                    )
                )
            }
            Record.People -> {
                if (customParameter is PeopleFragment.CustomParameters)
                    navController.navigate(
                        directions = PeopleFragmentDirections.actionPeopleFragmentToDetailsPeopleFragment(
                            id, customParameter
                        )
                    )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    private fun setupNavigationDrawer() {
        binding.apply {
            setSupportActionBar(toolbar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment?
            navController = navHostFragment?.navController ?: throw NullPointerException()
            NavigationUI.setupWithNavController(navigationView, navController)
            NavigationUI.setupActionBarWithNavController(
                this@NavigationActivity,
                navController,
                drawerLayout
            )
        }
    }

}