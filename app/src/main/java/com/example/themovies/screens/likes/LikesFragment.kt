package com.example.themovies.screens.likes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.views.adapters.LikesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikesFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<LikesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel.likes.observe(viewLifecycleOwner) {
            binding.apply {
                rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
                if (it.size != 0) {
                    rvMovies.adapter = LikesAdapter(it)
                } else {
                    tvNoLikes.visibility = View.VISIBLE
                }
            }

        }
        viewModel.getAllLikes()

        return binding.root
    }

}