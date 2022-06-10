package com.example.themovies.screens.detail.people

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.themovies.activities.Loading
import com.example.themovies.databinding.FragmentDetailsPeopleBinding
import com.example.themovies.network.ConfigurationRepository
import com.example.themovies.screens.movie.MovieRepository


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
        viewModel.people.observe(viewLifecycleOwner) {
            loading?.hideLoading()
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getPeople(id ?: 0)
    }

}


