package com.example.themovies.screens.list

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.data.adapters.MovieAdapter
import com.example.themovies.data.paging.ListLoadStateAdapter
import com.example.themovies.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    interface OnMovieItemClickListener {
        fun onMovieClick(id: Int)
    }

    companion object {
        var sizeOfPoster: String = ""
    }

    var onMovieItemClickListener: OnMovieItemClickListener? = null
    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onMovieItemClickListener = context as OnMovieItemClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.apply {
            if (!isNetworkConnected()) {
                rvMovies.visibility = View.INVISIBLE
                tvError.visibility = View.VISIBLE
                btnRetry.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        if (isNetworkConnected()) {
                            visibility = View.INVISIBLE
                            tvError.visibility = View.INVISIBLE
                            downloadData()
                        } else {
                            return@setOnClickListener
                        }
                    }
                }
            } else {
                downloadData()
            }

            return root
        }

    }

    override fun onDetach() {
        super.onDetach()
        onMovieItemClickListener = null
    }

    private fun isNetworkConnected(): Boolean {
        val connect: ConnectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }

    private fun downloadData() {
        binding.rvMovies.visibility = View.VISIBLE
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovies.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == movieAdapter.itemCount && movieAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        movieAdapter = MovieAdapter(object : OnMovieItemClickListener {
            override fun onMovieClick(id: Int) {
                onMovieItemClickListener?.onMovieClick(id)
            }
        })
        concatAdapter = movieAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvMovies.adapter = concatAdapter

        viewModel.downloadConfiguration()

        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                movieAdapter.submitData(it)
            }
        }

    }

}