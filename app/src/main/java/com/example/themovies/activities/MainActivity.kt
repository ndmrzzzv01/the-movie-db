package com.example.themovies.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.themovies.R
import com.example.themovies.databinding.ActivityMainBinding
import com.example.themovies.screens.detail.movie.DetailsFragment
import com.example.themovies.screens.movie.MainFragment
import com.example.themovies.screens.people.PeopleFragment
import com.example.themovies.screens.tv.TvFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    MainFragment.OnMovieItemClickListener,
    TvFragment.OnTvItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var fragment: Fragment? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        showDrawerMenu()
        showMainList()
    }

    private fun showMainList() {
        supportActionBar?.title = "Popular Movie"
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, MainFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onMovieClick(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, DetailsFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    override fun onTvClick(id: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, DetailsFragment.newInstance(id))
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
                        MainFragment()
                    }
                    R.id.popularTV -> {
                        supportActionBar?.title = "Popular TV Show"
                        TvFragment()
                    }
                    R.id.popularPeople -> {
                        supportActionBar?.title = "Popular People"
                        PeopleFragment()
                    }
                    else -> return@OnNavigationItemSelectedListener true
                }

                supportFragmentManager
                    .beginTransaction()
                    .replace(binding.fragmentContainer.id, fragment!!)
                    .addToBackStack(null)
                    .commit()

                binding.drawerLayout.closeDrawer(GravityCompat.START)

                true
            })
            menu.getItem(0).isChecked = true
        }
    }
}