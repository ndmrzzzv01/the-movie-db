package com.example.themovies.screens.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
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
import com.example.themovies.screens.registration.activities.AuthorizationActivity
import com.example.themovies.screens.registration.activities.SignInActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity(), Loading {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityNavigationBinding
    private lateinit var navController: NavController
    private var sessionId: String? = null
    private var sessionSuccess: Boolean? = null
    private val viewModel by viewModels<NavigationViewModel>()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit) {
            viewModel.deleteSession(sessionId ?: "")
            sharedPreferences.edit().putBoolean(SplashScreenActivity.CHANGED_ACTIVITY, false)
                .commit()
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation)
        binding.view.setOnClickListener { }
        sessionId = sharedPreferences.getString(SignInActivity.ID, "")
        sessionSuccess = sharedPreferences.getBoolean(SignInActivity.SUCCESS, false)

        setupNavigationDrawer()
        setNameInNavigationHeader()
        initObservers()
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

    private fun initObservers() {
        viewModel.deleteSession.observe(this) {
            if (it.success) {
                startActivity(Intent(this, AuthorizationActivity::class.java))
                finish()
            }
        }
    }

    private fun setNameInNavigationHeader() {
        val header = binding.navigationView.getHeaderView(0)
        val name = header.findViewById<TextView>(R.id.tvNameHeader)

        if (sessionSuccess == true) {
            viewModel.getDetails(sessionId ?: "")
        } else {
            name.text = "guest!"
        }

        viewModel.user.observe(this) {
            name.text = it?.username + "!"
        }

    }

}