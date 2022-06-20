package com.example.themovies.screens.detail.people

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.themovies.activities.Loading
import com.example.themovies.databinding.FragmentDetailsPeopleBinding
import com.example.themovies.screens.settings.ForegroundService
import com.example.themovies.screens.settings.SettingsFragment
import com.example.themovies.utils.SettingsUtils
import com.like.LikeButton
import com.like.OnLikeListener


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
        val intent = Intent(requireContext(), ForegroundService::class.java)

        viewModel.people.observe(viewLifecycleOwner) { people ->
            loading?.hideLoading()
            binding.btnLike.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton?) {
                    Toast.makeText(requireContext(), "Like ${people.name}", Toast.LENGTH_LONG).show()

                    if (value == true) {
                        intent.putExtra("name", people.name)
                        this@PeopleDetailFragment.activity?.startForegroundService(intent)
                    }
                }

                override fun unLiked(likeButton: LikeButton?) {
                    Toast.makeText(requireContext(), "Unlike ${people.name}", Toast.LENGTH_LONG).show()
                }

            })

//                viewModelLikes.insertRecord(Like(people.id, 2))

        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getPeople(id ?: 0)
    }

}


