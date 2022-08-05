package com.example.themovies.screens.activities

import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.themovies.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(AuthorizationActivity.TOKEN)

        binding.webview.loadUrl("https://www.themoviedb.org/authenticate/$token?redirect_to=https://nadyasmoviedb.com/approved")
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url
                if (url.toString().contains("https://nadyasmoviedb.com")) {
                    val intent = Intent(this@SignUpActivity, NavigationActivity::class.java)
                    startActivity(intent)
                    return true
                }
                return false
            }
        }


    }
}