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
import com.example.themovies.network.data.Record
import com.example.themovies.screens.activities.Loading
import com.example.themovies.screens.likes.data.LikesAdapter
import com.example.themovies.screens.people.PeopleFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikesFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<LikesViewModel>()
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
        initObservers()
        viewModel.getAllLikes()
    }

    private fun initObservers() {
        viewModel.likes.observe(viewLifecycleOwner) { list ->
            binding.apply {
                rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
                if (list.isNotEmpty()) {
                    val actionHandler = MediaItemTypeActionHandler()
                    actionHandler.onMediaTypeClick = OnMediaTypeClick {
                        loading?.showLoading()
                        when (it.type) {
                            Record.Movie -> {
                                findNavController().navigate(
                                    LikesFragmentDirections.actionLikesToDetailsMovieFragment(
                                        it.id ?: 0
                                    )
                                )
                            }
                            Record.TV -> {
                                findNavController().navigate(
                                    LikesFragmentDirections.actionLikesToDetailsTvFragment(
                                        it.id ?: 0
                                    )
                                )
                            }
                            Record.People -> {
                                //                            if (it.customParameter is PeopleFragment.CustomParameters)
                                findNavController().navigate(
                                    LikesFragmentDirections.actionLikesToDetailsPeopleFragment(
                                        id
                                    )
                                )
                            }
                        }
                    }
                    rvMovies.adapter = LikesAdapter(list, actionHandler)
                } else {
                    tvNoLikes.visibility = View.VISIBLE
                }
            }

        }
    }

}