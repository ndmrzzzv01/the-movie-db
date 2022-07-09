package com.example.themovies.screens.detail.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovies.database.data.Like
import com.example.themovies.databinding.FragmentDetailTvBinding
import com.example.themovies.notifications.NotificationService
import com.example.themovies.screens.activities.Loading
import com.example.themovies.screens.settings.SettingsFragment
import com.example.themovies.utils.SettingsUtils
import com.example.themovies.screens.tv.data.SeasonAdapter
import com.like.LikeButton
import com.like.OnLikeListener
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
        val value = SettingsUtils.provideSharedPreferences(requireContext())
            ?.getBoolean(SettingsFragment.NOTIFICATION_LIKE, false)
        val intent = Intent(requireContext(), NotificationService::class.java)

        initObservers(value, intent)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getTv(id ?: 0)
        viewModel.isLiked(id ?: 0)
    }

    private fun initObservers(value: Boolean?, intent: Intent) {
        viewModel.tv.observe(viewLifecycleOwner) { tv ->
            binding.apply {
                loading?.hideLoading()
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