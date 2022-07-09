package com.example.themovies.screens.movie.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.network.data.Movie
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.screens.likes.data.LikesAdapter

class TopRatedMoviesFragment : Fragment(), TopRatedMoviesContract.TopRatedMoviesView {

    private lateinit var binding: FragmentMainBinding
    private var presenter: TopRatedMoviesContract.TopRatedMoviesPresenter? = null
    private var adapter = LikesAdapter(mutableListOf(), null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TopRatedMoviesPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovies.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter?.loadTopRatedMovies()
    }

    override fun onStop() {
        super.onStop()
        presenter?.cancel()
    }

    override fun displayListOfTopRatedMovies(list: List<Movie>) {
        binding.rvMovies.visibility = View.VISIBLE
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

    override fun onFail() {
        binding.apply {
            rvMovies.visibility = View.INVISIBLE
            btnRetry.visibility = View.VISIBLE
            tvError.visibility = View.VISIBLE
        }
    }
}