package com.example.themovies.screens.detail.movie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.themovies.activities.Loading
import com.example.themovies.databinding.FragmentDetailsMovieBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.MovieRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    companion object {
        private const val MOVIE_ID = "movie_id"
        fun newInstance(id: Int): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            fragment.arguments = Bundle().apply {
                putInt(MOVIE_ID, id)
            }
            return fragment
        }
    }

    private var id: Int? = null
    var loading: Loading? = null
    private lateinit var binding: FragmentDetailsMovieBinding
    private val viewModel by viewModels<MovieDetailsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as Loading
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsMovieBinding.inflate(inflater, container, false)

        showDetailsAboutMovie()

        return binding.root
    }

    private fun showDetailsAboutMovie() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.apply {
                loading?.hideLoading()
                tvTitle.text = movie.title
                tvOriginalTitle.text = movie.originalTitle
                Glide
                    .with(requireContext())
                    .load("${MovieRepository.URL}${ConfigurationRepository.sizeOfPoster}${movie.backdropPath}")
                    .into(image)
                when {
                    movie.budget.toString() == "0" && movie.revenue.toString() == "0" -> {
                        tvBudget.visibility = View.GONE
                        tvBudgetText.visibility = View.GONE
                        tvRevenue.visibility = View.GONE
                        tvRevenueText.visibility = View.GONE
                    }
                    movie.revenue.toString() == "0" -> {
                        tvRevenue.visibility = View.GONE
                        tvRevenueText.visibility = View.GONE
                        tvBudget.text = setDotAfterThreeNumbers(movie.budget.toString())
                        tvBudgetText.visibility = View.VISIBLE
                    }
                    movie.budget.toString() == "0" -> {
                        tvBudget.visibility = View.GONE
                        tvBudgetText.visibility = View.GONE
                        tvRevenue.text = setDotAfterThreeNumbers(movie.revenue.toString())
                        tvRevenueText.visibility = View.VISIBLE
                    }
                    else -> {
                        tvBudget.text = setDotAfterThreeNumbers(movie.budget.toString())
                        tvRevenue.text = setDotAfterThreeNumbers(movie.revenue.toString())
                        tvBudgetText.visibility = View.VISIBLE
                        tvRevenueText.visibility = View.VISIBLE
                    }
                }
                tvDescriptionMovie.text = movie.description
            }
        }
        viewModel.getMovie(id ?: 0)
    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

    private fun setDotAfterThreeNumbers(number: String): String {
        val length = number.length
        return if (length > 3) {
            val builder = StringBuilder(number)
            var dots = length / 3
            if (length % 3 == 0) dots -= 1
            for (i in 1..dots) {
                builder.insert(length - (i * 3), ".")
            }
            builder.toString()
        } else {
            number
        }
    }

}