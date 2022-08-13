package com.example.themovies.screens.registration.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.themovies.databinding.ActivitySignupBinding
import com.example.themovies.screens.activities.NavigationActivity
import com.example.themovies.screens.activities.SplashScreenActivity
import com.example.themovies.screens.registration.viewmodels.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    companion object {
        const val ID = "id"
        const val SUCCESS = "success"
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getRequestToken()

        viewModel.tokenValue.observe(this) {
            binding.webview.loadUrl("https://www.themoviedb.org/authenticate/${it?.token}?redirect_to=https://nadyasmoviedb.com/approved")
        }

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (request?.url.toString().contains("https://nadyasmoviedb.com")) {
                    viewModel.createSession()

                    viewModel.session.observe(this@SignInActivity) {
                        if (it?.success == true) {
                            sharedPreferences.edit()
                                .putBoolean(SplashScreenActivity.CHANGED_ACTIVITY, true)
                                .putString(ID, it.id)
                                .putBoolean(SUCCESS, it.success)
                                .commit()
                            startActivity(Intent(this@SignInActivity, NavigationActivity::class.java))
                            finish()
                        }
                    }

                    return true
                }
                return false
            }
        }
    }
}