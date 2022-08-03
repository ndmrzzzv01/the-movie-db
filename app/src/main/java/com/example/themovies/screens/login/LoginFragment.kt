package com.example.themovies.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.themovies.databinding.FragmentLoginBinding
import com.example.themovies.screens.activities.NavigationActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setAnimation()

        binding.btnLogin.setOnClickListener {
            val intent = Intent(requireContext(), NavigationActivity::class.java)
            this.activity?.startActivity(intent)
        }

        return binding.root
    }

    private fun setAnimation() {
        binding.apply {
            tvLogin.translationY = 300f
            otfEmail.translationY = 300f
            otfPassword.translationY = 300f
            btnLogin.translationY = 300f

            tvLogin.alpha = 0f
            otfEmail.alpha = 0f
            otfPassword.alpha = 0f
            btnLogin.alpha = 0f

            tvLogin.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(50)
                .start()
            otfEmail.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(100)
                .start()
            otfPassword.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(300)
                .start()
            btnLogin.animate().translationY(0f).alpha(1f).setDuration(1000).setStartDelay(500)
                .start()
        }
    }
}