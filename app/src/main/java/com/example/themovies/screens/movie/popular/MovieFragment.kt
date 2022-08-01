package com.example.themovies.screens.movie.popular

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.network.MediaItemTypeActionHandler
import com.example.themovies.network.OnMediaTypeClick
import com.example.themovies.paging.ListLoadStateAdapter
import com.example.themovies.screens.RecordAdapter
import com.example.themovies.screens.activities.Loading
import com.example.themovies.utils.ConnectivityTracker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment() {

    @Inject
    lateinit var connectivityTracker: ConnectivityTracker
    var loading: Loading? = null
    private val viewModel by viewModels<MovieViewModel>()
    private lateinit var binding: FragmentMainBinding
    private lateinit var movieAdapter: RecordAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loading = context as Loading
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initList()
        initObservers()

        return binding.root

    }

    override fun onDetach() {
        super.onDetach()
        loading = null
    }

    private fun initList() {
        movieAdapter = initAdapter()
        concatAdapter = movieAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvMovies.adapter = concatAdapter

        connectivityTracker.initGridLayoutManager(binding.rvMovies, movieAdapter, requireContext())
    }

    private fun initAdapter(): RecordAdapter {
        val actionHandler = MediaItemTypeActionHandler()
        actionHandler.onMediaTypeClick = OnMediaTypeClick {
            loading?.showLoading()
            findNavController().navigate(
                MovieFragmentDirections.actionMainFragmentToDetailsFragment(
                    it.id ?: 0
                )
            )
        }
        movieAdapter = RecordAdapter(actionHandler)

        val loadStateAdapter: (CombinedLoadStates) -> Unit = {
            when (it.refresh) {
                is LoadState.Loading -> viewModel.showLoading()
                is LoadState.Error -> viewModel.showError()
                else -> viewModel.showList()
            }
        }

        movieAdapter.addLoadStateListener(loadStateAdapter)
        return movieAdapter
    }


    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                movieAdapter.submitData(it)
            }
        }
        viewModel.retryLoadData = {
            movieAdapter.retry()
        }
    }

}