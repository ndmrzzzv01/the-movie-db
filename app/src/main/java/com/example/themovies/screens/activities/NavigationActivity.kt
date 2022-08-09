package com.example.themovies.screens.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.example.themovies.R
import com.example.themovies.databinding.ActivityNavigationBinding
import com.example.themovies.screens.activities.data.NavigationViewModel
import com.example.themovies.screens.registration.activities.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity(), Loading {

    private lateinit var binding: ActivityNavigationBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<NavigationViewModel>()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation)
        binding.view.setOnClickListener { }

        val token = intent.getStringExtra(SignUpActivity.TOKEN)

        viewModel.createSession(token)

        viewModel.session.observe(this) {
            if (it?.success == true) {
                viewModel.getDetails(it.id)
            }
        }

        setupNavigationDrawer()
        setNameInNavigationHeader()

    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.view.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.view.visibility = View.GONE
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

    private fun setNameInNavigationHeader() {
        val header = binding.navigationView.getHeaderView(0)
        val name = header.findViewById<TextView>(R.id.tvNameHeader)
        viewModel.user.observe(this) {
            name.text = it?.username
        }
    }

}