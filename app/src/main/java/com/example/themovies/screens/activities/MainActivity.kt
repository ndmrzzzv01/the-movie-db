package com.example.themovies.screens.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.themovies.databinding.ActivityMainBinding
import com.example.themovies.screens.login.LoginAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        supportActionBar?.hide()

        setupTabLayoutAndPager()

    }

    private fun setupTabLayoutAndPager() {
        binding.apply {

            tab.apply {
                addTab(this.newTab().setText("Login"))
                addTab(this.newTab().setText("SignUp"))
                tabGravity = TabLayout.GRAVITY_FILL
            }

            val adapter = LoginAdapter(supportFragmentManager, binding.tab.tabCount)
            pager.adapter = adapter

            pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tab))

            tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    pager.currentItem = tab?.position ?: 0
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })
        }
    }
}