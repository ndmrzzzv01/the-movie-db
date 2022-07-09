package com.example.themovies.screens.detail.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.themovies.database.data.Like
import com.example.themovies.databinding.FragmentDetailsMovieBinding
import com.example.themovies.notifications.NotificationService
import com.example.themovies.screens.activities.Loading
import com.example.themovies.screens.settings.SettingsFragment
import com.example.themovies.utils.SettingsUtils
import com.like.LikeButton
import com.like.OnLikeListener
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
        val value = SettingsUtils.provideSharedPreferences(requireContext())
            ?.getBoolean(SettingsFragment.NOTIFICATION_LIKE, false)
        val intent = Intent(requireContext(), NotificationService::class.java)

        initObservers(value, intent)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getMovie(id ?: 0)
        viewModel.isLiked(id ?: 0)
    }

    private fun initObservers(value: Boolean?, intent: Intent) {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.apply {
                loading?.hideLoading()

                btnLike.setOnLikeListener(object : OnLikeListener {
                    override fun liked(likeButton: LikeButton?) {
                        viewModel?.insertRecord(Like(idRecord = movie.id, type = 0))

                        if (value == true) {
                            intent.putExtra(SettingsFragment.NAME, movie.title)
                            this@MovieDetailFragment.activity?.startForegroundService(intent)
                        }
                    }

                    override fun unLiked(likeButton: LikeButton?) {
                        viewModel?.deleteRecord(movie.id ?: 0)
                    }

                })

            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

}