package com.example.themovies.screens.likes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.network.MediaItemTypeActionHandler
import com.example.themovies.network.OnMediaTypeClick
import com.example.themovies.screens.activities.Loading
import com.example.themovies.screens.likes.data.LikesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikesFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<LikesViewModel>()
    private lateinit var adapter: LikesAdapter
    var loading: Loading? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as Loading
    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        showLikes()

        return binding.root
    }

    private fun showLikes() {
        viewModel.getAllLikes()
        initObservers()
    }

    private fun initObservers() {
        viewModel.likes.observe(viewLifecycleOwner) { list ->
            binding.apply {
                rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
                if (list.isNotEmpty()) {
                    rvMovies.visibility = View.VISIBLE
                    val actionHandler = MediaItemTypeActionHandler()
                    adapter = LikesAdapter(list, actionHandler)
                    actionHandler.onMediaTypeClick = OnMediaTypeClick {
                        loading?.showLoading()
                        when (it.type) {
                            0 -> {
                                findNavController().navigate(
                                    LikesFragmentDirections.actionLikesToDetailsMovieFragment(
                                        it.id ?: 0
                                    )
                                )
                            }
                            1 -> {
                                findNavController().navigate(
                                    LikesFragmentDirections.actionLikesToDetailsTvFragment(
                                        it.id ?: 0
                                    )
                                )
                            }
                            2 -> {
                                findNavController().navigate(
                                    LikesFragmentDirections.actionLikesToDetailsPeopleFragment(
                                        it.id ?: 0
                                    )
                                )
                            }
                        }
                    }
                    adapter.updateList(list)
                    rvMovies.adapter = adapter
                } else {
                    tvNoLikes.visibility = View.VISIBLE
                }
            }

        }
    }

}