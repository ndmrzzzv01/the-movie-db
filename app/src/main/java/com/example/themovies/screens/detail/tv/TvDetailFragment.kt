package com.example.themovies.screens.detail.tv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.themovies.database.data.Like
import com.example.themovies.databinding.FragmentDetailTvBinding
import com.example.themovies.screens.activities.Loading
import com.like.LikeButton
import com.like.OnLikeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetailFragment : Fragment() {

    var loading: Loading? = null
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
        initObservers()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getTv(id.idTv)
        viewModel.isLiked(id.idTv)
    }

    private fun initObservers() {
        viewModel.tv.observe(viewLifecycleOwner) { tv ->
            binding.apply {
                loading?.hideLoading()

                (requireActivity() as AppCompatActivity).supportActionBar?.title = tv?.name

                btnLike.setOnLikeListener(object : OnLikeListener {
                    override fun liked(likeButton: LikeButton?) {
                        viewModel?.insertRecord(Like(idRecord = tv?.id ?: 0, type = 1))
                    }

                    override fun unLiked(likeButton: LikeButton?) {
                        viewModel?.deleteRecord(tv?.id ?: 0)
                    }

                })
            }
        }

    }
}