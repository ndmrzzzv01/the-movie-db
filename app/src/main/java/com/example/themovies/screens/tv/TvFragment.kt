package com.example.themovies.screens.tv

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
import com.example.themovies.network.data.Record
import com.example.themovies.network.data.RecordClick
import com.example.themovies.paging.ListLoadStateAdapter
import com.example.themovies.screens.RecordAdapter
import com.example.themovies.screens.activities.Loading
import com.example.themovies.utils.ConnectivityTracker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TvFragment : Fragment() {

    @Inject
    lateinit var connectivityTracker: ConnectivityTracker
    var loading: Loading? = null
    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<TvViewModel>()
    private lateinit var tvAdapter: RecordAdapter
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
        tvAdapter = initAdapter()
        concatAdapter = tvAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvMovies.adapter = concatAdapter

        connectivityTracker.initGridLayoutManager(binding.rvMovies, tvAdapter, requireContext())
    }

    private fun initAdapter(): RecordAdapter {
        tvAdapter = RecordAdapter(object : RecordClick {
            override fun onRecordClickListener(id: Int, type: Record, customParameter: Any?) {
                loading?.showLoading()
                findNavController().navigate(
                    TvFragmentDirections.actionTvFragmentToDetailsTvFragment(
                        id
                    )
                )
            }
        })

        val loadStateAdapter: (CombinedLoadStates) -> Unit = {
            when (it.refresh) {
                is LoadState.Loading -> viewModel.showLoading()
                is LoadState.Error -> viewModel.showError()
                else -> viewModel.showList()
            }
        }

        tvAdapter.addLoadStateListener(loadStateAdapter)
        return tvAdapter

    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                tvAdapter.submitData(it)
            }
        }
        viewModel.retryLoadData = {
            tvAdapter.retry()
        }
    }
}