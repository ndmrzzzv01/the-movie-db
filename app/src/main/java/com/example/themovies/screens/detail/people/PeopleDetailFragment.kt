package com.example.themovies.screens.detail.people

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.themovies.database.data.Like
import com.example.themovies.databinding.FragmentDetailsPeopleBinding
import com.example.themovies.notifications.NotificationService
import com.example.themovies.screens.activities.Loading
import com.example.themovies.screens.detail.movie.MovieDetailFragment
import com.example.themovies.screens.settings.SettingsFragment
import com.example.themovies.utils.SettingsUtils
import com.like.LikeButton
import com.like.OnLikeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleDetailFragment : Fragment() {

    companion object {
        private const val PEOPLE_ID = "people_id"
        fun newInstance(id: Int): PeopleDetailFragment {
            val fragment = PeopleDetailFragment()
            fragment.arguments = Bundle().apply {
                putInt(PEOPLE_ID, id)
            }
            return fragment
        }
    }

    private var id: Int? = null
    var loading: Loading? = null
    private lateinit var binding: FragmentDetailsPeopleBinding
    private val viewModel by viewModels<PeopleDetailsViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as Loading
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(PEOPLE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsPeopleBinding.inflate(inflater, container, false)

        showDetailsAboutMovie()

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

    private fun showDetailsAboutMovie() {
        val value = SettingsUtils.provideSharedPreferences(requireContext())
            ?.getBoolean(SettingsFragment.NOTIFICATION_LIKE, false)
        val intent = Intent(requireContext(), NotificationService::class.java)

        initObservers(value, intent)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getPeople(id ?: 0)
        viewModel.isLiked(id ?: 0)
    }

    private fun initObservers(value: Boolean?, intent: Intent) {
        viewModel.people.observe(viewLifecycleOwner) { people ->
            loading?.hideLoading()
            binding.btnLike.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    viewModel.insertRecord(Like(idRecord = people.id, type = 2))

                    if (value == true) {
                        intent.putExtra(MovieDetailFragment.NAME, people.name)
                        this@PeopleDetailFragment.activity?.startForegroundService(intent)
                    }
                }

                override fun unLiked(likeButton: LikeButton?) {
                    viewModel.deleteRecord(people.id ?: 0)
                }

            })

        }
    }

}


