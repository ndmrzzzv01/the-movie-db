package com.example.themovies.screens.detail.tv

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovies.activities.Loading
import com.example.themovies.databinding.FragmentDetailTvBinding
import com.example.themovies.views.adapters.SeasonAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetailFragment : Fragment() {

    companion object {
        private const val TV_ID = "tv_id"
        fun newInstance(id: Int): TvDetailFragment {
            val fragment = TvDetailFragment()
            fragment.arguments = Bundle().apply {
                putInt(TV_ID, id)
            }
            return fragment
        }
    }

    private var id: Int? = null
    var loading: Loading? = null
    private lateinit var binding: FragmentDetailTvBinding
    private val viewModel by viewModels<TvDetailsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as Loading
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(TV_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailTvBinding.inflate(inflater, container, false)

        showDetailsAboutTv()

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

    private fun showDetailsAboutTv() {
        viewModel.tv.observe(viewLifecycleOwner) { tv ->
            binding.apply {
                loading?.hideLoading()
                tvReleaseDateText.visibility = View.VISIBLE
                tvStatusText.visibility = View.VISIBLE
                tvVoteText.visibility = View.VISIBLE
            }
        }

        viewModel.season.observe(viewLifecycleOwner) { list ->
            if (list?.seasons != null) {
                binding.apply {
                    rvSeason.visibility = View.VISIBLE
                    rvSeason.layoutManager = LinearLayoutManager(requireContext())
                    rvSeason.adapter = SeasonAdapter(list.seasons)
                }
            }
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getTv(id ?: 0)
    }
}