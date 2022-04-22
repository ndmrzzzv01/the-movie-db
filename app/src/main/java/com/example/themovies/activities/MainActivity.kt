package com.example.themovies.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.themovies.R
import com.example.themovies.databinding.ActivityMainBinding
import com.example.themovies.screens.detail.DetailsFragment
import com.example.themovies.screens.list.MainFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    MainFragment.OnMovieItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        showDrawerMenu()

        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.fragmentContainer.id, MainFragment())
                .commit()
        }
    }

    override fun onMovieClick(id: Int) {
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

        binding.navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.account -> Toast.makeText(this@MainActivity, "My Account", Toast.LENGTH_SHORT)
                    .show()
                R.id.settings -> Toast.makeText(this@MainActivity, "Settings", Toast.LENGTH_SHORT)
                    .show()
                R.id.mycart -> Toast.makeText(this@MainActivity, "My Cart", Toast.LENGTH_SHORT)
                    .show()
                else -> return@OnNavigationItemSelectedListener true
            }
            true
        })
    }
}