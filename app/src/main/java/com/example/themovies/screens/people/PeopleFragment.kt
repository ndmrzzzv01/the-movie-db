package com.example.themovies.screens.people

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
import com.example.themovies.views.adapters.RecordAdapter
import com.example.themovies.screens.activities.Loading
import com.example.themovies.utils.ConnectivityTracker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    @Inject
    lateinit var connectivityTracker: ConnectivityTracker
    var loading: Loading? = null
    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<PeopleViewModel>()
    private lateinit var peopleAdapter: RecordAdapter
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
        peopleAdapter = initAdapter()
        concatAdapter = peopleAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvMovies.adapter = concatAdapter

        connectivityTracker.initGridLayoutManager(binding.rvMovies, peopleAdapter, requireContext())
    }

    private fun initAdapter(): RecordAdapter {
        val actionHandler = MediaItemTypeActionHandler()
        actionHandler.onMediaTypeClick = OnMediaTypeClick {
            loading?.showLoading()

            findNavController().navigate(
                PeopleFragmentDirections.actionPeopleFragmentToDetailsPeopleFragment(
                    it.id ?: 0
                )
            )

        }

        peopleAdapter = RecordAdapter(actionHandler)

        val loadStateAdapter: (CombinedLoadStates) -> Unit = {
            when (it.refresh) {
                is LoadState.Loading -> viewModel.showLoading()
                is LoadState.Error -> viewModel.showError()
                else -> viewModel.showList()
            }
        }

        peopleAdapter.addLoadStateListener(loadStateAdapter)
        return peopleAdapter
    }


    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                peopleAdapter.submitData(it)
            }
        }
        viewModel.retryLoadData = {
            peopleAdapter.retry()
        }
    }
}