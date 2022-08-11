package com.example.themovies.screens.registration.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.themovies.databinding.ActivityMainBinding
import com.example.themovies.screens.activities.NavigationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setAnimation()
        initViews()

        supportActionBar?.hide()

    }

    private fun initViews() {
        binding.apply {
            btnSignIn.setOnClickListener {
                startActivity(Intent(this@AuthorizationActivity, SignInActivity::class.java))
                finish()
            }

            btnSignInLikeGuest.setOnClickListener {
                startActivity(Intent(this@AuthorizationActivity, NavigationActivity::class.java))
            }

        }
    }

    private fun setAnimation() {
        binding.apply {
            tvTitle.translationY = 300f
            tvInfo.translationY = 300f
            btnSignIn.translationY = 300f
            tvOr.translationY = 300f
            btnSignInLikeGuest.translationY = 300f

            tvTitle.alpha = 0f
            tvInfo.alpha = 0f
            btnSignIn.alpha = 0f
            tvOr.alpha = 0f
            btnSignInLikeGuest.alpha = 0f

            tvTitle.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(100)
                .start()
            tvInfo.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(200)
                .start()
            btnSignIn.animate().translationY(0f).alpha(1f).setDuration(1000)
                .setStartDelay(300)
                .start()
            tvOr.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(400)
                .start()
            btnSignInLikeGuest.animate().translationY(0f).alpha(1f).setDuration(1000)
                .setStartDelay(500)
                .start()
        }
    }
}