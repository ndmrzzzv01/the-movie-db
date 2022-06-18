package com.example.themovies.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.themovies.R
import com.example.themovies.data.Record
import com.example.themovies.data.RecordClick
import com.example.themovies.databinding.ActivityMainBinding
import com.example.themovies.screens.detail.movie.MovieDetailFragment
import com.example.themovies.screens.detail.people.PeopleDetailFragment
import com.example.themovies.screens.detail.tv.TvDetailFragment
import com.example.themovies.screens.likes.LikesFragment
import com.example.themovies.screens.movie.MovieFragment
import com.example.themovies.screens.people.PeopleFragment
import com.example.themovies.screens.settings.SettingsFragment
import com.example.themovies.screens.tv.TvFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    RecordClick, Loading {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var fragment: Fragment? = null
    private var fragmentDetail: Fragment? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.view.setOnClickListener { }

        if (savedInstanceState == null) {
            showMainList()
        }
        showDrawerMenu()

    }

    private fun showMainList() {
        supportActionBar?.title = "Popular Movie"
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, MovieFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.view.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.view.visibility = View.GONE
    }

    override fun onRecordClickListener(id: Int, type: Record) {
        fragmentDetail = when (type) {
            Record.Movie -> {
                MovieDetailFragment.newInstance(id)
            }
            Record.TV -> {
                TvDetailFragment.newInstance(id)
            }
            Record.People -> {
                PeopleDetailFragment.newInstance(id)
            }
        }
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, fragmentDetail!!)
            .addToBackStack(null)
            .commit()
    }


    private fun showDrawerMenu() {
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navigationView.apply {
            setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
                fragment = when (item.itemId) {
                    R.id.popularMovie -> {
                        supportActionBar?.title = "Popular Movie"
                        MovieFragment()
                    }
                    R.id.popularTV -> {
                        supportActionBar?.title = "Popular TV Show"
                        TvFragment()
                    }
                    R.id.popularPeople -> {
                        supportActionBar?.title = "Popular People"
                        PeopleFragment()
                    }
                    R.id.likes -> {
                        supportActionBar?.title = "Likes"
                        LikesFragment()
                    }
                    R.id.settings -> {
                        supportActionBar?.title = "Settings"
                        SettingsFragment()
                    }
                    else -> return@OnNavigationItemSelectedListener true
                }
                supportFragmentManager
                    .beginTransaction()
                    .replace(binding.fragmentContainer.id, fragment!!)
                    .commit()

                binding.drawerLayout.closeDrawer(GravityCompat.START)

                true
            })
            menu.getItem(0).isChecked = true
        }
    }


}