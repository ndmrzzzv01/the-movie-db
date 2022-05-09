package com.example.themovies.screens.detail.tv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.themovies.databinding.FragmentDetailTvBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.MovieRepository
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
    private lateinit var binding: FragmentDetailTvBinding
    private val viewModel by viewModels<TvDetailsViewModel>()

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

    private fun showDetailsAboutTv() {
        viewModel.tv.observe(viewLifecycleOwner) { tv ->
            binding.apply {
                Glide
                    .with(requireContext())
                    .load("${MovieRepository.URL}${ConfigurationRepository.sizeOfPoster}${tv.backdropPath}")
                    .into(image)
                tvTitle.text = tv.name
                tvOriginalTitle.text = tv.originalName
                tvRelease.text = tv.releaseDate
                tvStatus.text = tv.status
                tvVote.text = tv.voteAverage.toString()
                tvHomepage.text = "Click here to follow the link!"
                tvHomepage.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(tv.homepage)
                    startActivity(intent)
                }
                tvDescriptionTv.text = tv.overview
            }
        }
        viewModel.getTv(id ?: 0)
    }
}