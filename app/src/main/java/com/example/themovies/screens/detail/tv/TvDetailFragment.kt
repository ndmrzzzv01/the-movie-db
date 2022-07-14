package com.example.themovies.screens.detail.tv

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovies.database.data.Like
import com.example.themovies.databinding.FragmentDetailTvBinding
import com.example.themovies.notifications.NotificationService
import com.example.themovies.screens.activities.Loading
import com.example.themovies.screens.settings.SettingsFragment
import com.example.themovies.screens.tv.data.SeasonAdapter
import com.like.LikeButton
import com.like.OnLikeListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvDetailFragment : Fragment() {

    var loading: Loading? = null
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentDetailTvBinding
    private val viewModel by viewModels<TvDetailsViewModel>()
    private val id by navArgs<TvDetailFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as Loading
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
        val value = sharedPreferences.getBoolean(SettingsFragment.NOTIFICATION_LIKE, false)
        val intent = Intent(requireContext(), NotificationService::class.java)

        initObservers(value, intent)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getTv(id.idTv)
        viewModel.isLiked(id.idTv)
    }

    private fun initObservers(value: Boolean?, intent: Intent) {
        viewModel.tv.observe(viewLifecycleOwner) { tv ->
            binding.apply {
                loading?.hideLoading()

                (requireActivity() as AppCompatActivity).supportActionBar?.title = tv?.name

                btnLike.setOnLikeListener(object : OnLikeListener {
                    override fun liked(likeButton: LikeButton?) {
                        viewModel?.insertRecord(Like(idRecord = tv?.id ?: 0, type = 1))

                        if (value == true) {
                            intent.putExtra(SettingsFragment.NAME, tv?.name)
                            this@TvDetailFragment.activity?.startForegroundService(intent)
                        }
                    }

                    override fun unLiked(likeButton: LikeButton?) {
                        viewModel?.deleteRecord(tv?.id ?: 0)
                    }

                })
            }
        }

        viewModel.season.observe(viewLifecycleOwner) { list ->
            if (list?.seasons != null) {
                binding.apply {
                    rvSeason.visibility = View.VISIBLE
                    rvSeason.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvSeason.adapter = SeasonAdapter(list.seasons)
                }
            }
        }
    }
}