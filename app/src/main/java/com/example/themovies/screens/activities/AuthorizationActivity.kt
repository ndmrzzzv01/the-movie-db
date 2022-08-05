package com.example.themovies.screens.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.themovies.databinding.ActivityMainBinding
import com.example.themovies.screens.registration.AuthorizationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity() {

    companion object {
        const val TOKEN = "token"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<AuthorizationViewModel>()

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

        viewModel.getRequestToken()
        supportActionBar?.hide()


    }

    private fun initViews() {
        binding.apply {
            val intent = Intent(this@AuthorizationActivity, SignUpActivity::class.java)
            viewModel.token.observe(this@AuthorizationActivity) {
                intent.putExtra(TOKEN, it?.token)
            }
            btnAuthorization.setOnClickListener {
                startActivity(intent)
            }

        }
    }

    private fun setAnimation() {
        binding.apply {
            tvTitle.translationY = 300f
            tvInfo.translationY = 300f
            btnAuthorization.translationY = 300f
            tvOr.translationY = 300f
            btnSgnUpLikeGuest.translationY = 300f

            tvTitle.alpha = 0f
            tvInfo.alpha = 0f
            btnAuthorization.alpha = 0f
            tvOr.alpha = 0f
            btnSgnUpLikeGuest.alpha = 0f

            tvTitle.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(100)
                .start()
            tvInfo.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(200)
                .start()
            btnAuthorization.animate().translationY(0f).alpha(1f).setDuration(1000)
                .setStartDelay(300)
                .start()
            tvOr.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(400)
                .start()
            btnSgnUpLikeGuest.animate().translationY(0f).alpha(1f).setDuration(1000)
                .setStartDelay(500)
                .start()
        }
    }
}