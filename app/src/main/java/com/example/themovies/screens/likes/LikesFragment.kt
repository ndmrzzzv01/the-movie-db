package com.example.themovies.screens.likes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.network.data.Record
import com.example.themovies.network.data.RecordClick
import com.example.themovies.screens.activities.Loading
import com.example.themovies.views.adapters.LikesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikesFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<LikesViewModel>()
    var recordClick: RecordClick? = null
    var loading: Loading? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        recordClick = context as RecordClick
        loading = context as Loading
    }

    override fun onDetach() {
        super.onDetach()
        recordClick = null
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
        viewModel.likes.observe(viewLifecycleOwner) {
            binding.apply {
                rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
                if (it.size != 0) {
                    rvMovies.adapter = LikesAdapter(it, object : RecordClick {
                        override fun onRecordClickListener(id: Int, type: Record) {
                            loading?.showLoading()
                            recordClick?.onRecordClickListener(id, type)
                        }

                    })
                } else {
                    tvNoLikes.visibility = View.VISIBLE
                }
            }

        }
    }

}