package com.example.themovies.screens.detail.movie

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.themovies.database.data.Like
import com.example.themovies.databinding.FragmentDetailsMovieBinding
import com.example.themovies.notifications.NotificationService
import com.example.themovies.screens.activities.Loading
import com.example.themovies.screens.settings.SettingsFragment
import com.like.LikeButton
import com.like.OnLikeListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    var loading: Loading? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentDetailsMovieBinding
    private val viewModel by viewModels<MovieDetailsViewModel>()
    private val id by navArgs<MovieDetailFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as Loading
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
        val value = sharedPreferences.getBoolean(SettingsFragment.NOTIFICATION_LIKE, false)
        val intent = Intent(requireContext(), NotificationService::class.java)

        initObservers(value, intent)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getMovie(id.idMovie)
        viewModel.isLiked(id.idMovie)
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