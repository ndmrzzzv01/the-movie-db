package com.example.themovies.screens.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class LoginAdapter(fragmentManager: FragmentManager, var totalTabs: Int) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = totalTabs

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> LoginFragment()
        else -> RegisterFragment()
    }

}