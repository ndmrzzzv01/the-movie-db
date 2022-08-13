package com.example.themovies.screens.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import com.example.themovies.databinding.ActivitySplashBinding
import com.example.themovies.screens.registration.activities.AuthorizationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val CHANGED_ACTIVITY = "changed activity"
    }

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onStart() {
        super.onStart()
        animate(binding.imageLabel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()

        val value = sharedPreferences.getBoolean(CHANGED_ACTIVITY, false)

        lifecycleScope.launch {
            delay(2000L)
            if (value) {
                startActivity(Intent(this@SplashScreenActivity, NavigationActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashScreenActivity, AuthorizationActivity::class.java))
                finish()
            }

        }

    }

    private fun animate(view: ImageView) {
        val alphaAnimator = ObjectAnimator
            .ofFloat(view, View.ALPHA, 0f, 1f)
            .setDuration(2000)

        val paddingAnimator = ValueAnimator.ofInt(200, 20)
            .setDuration(2000)
        paddingAnimator.addUpdateListener {
            view.setPadding(it.animatedValue as Int)
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(alphaAnimator, paddingAnimator)
        animatorSet.start()
    }

}