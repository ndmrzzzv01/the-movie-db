package com.example.themovies.screens.tv

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
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
    var recordClick: RecordClick? = null
    var loading: Loading? = null
    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<TvViewModel>()
    private lateinit var tvAdapter: RecordAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        recordClick = context as RecordClick
        loading = context as Loading
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.apply {
            if (!connectivityTracker.isNetworkConnected(requireContext())) {
                rvMovies.visibility = View.INVISIBLE
                tvError.visibility = View.VISIBLE
                btnRetry.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        if (connectivityTracker.isNetworkConnected(requireContext())) {
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
        recordClick = null
        loading = null
    }

    private fun downloadData() {
        createRecyclerView()

        tvAdapter = RecordAdapter(object : RecordClick {
            override fun onRecordClickListener(id: Int, type: Record) {
                loading?.showLoading()
                recordClick?.onRecordClickListener(id, Record.TV)
            }
        })
        concatAdapter = tvAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvMovies.adapter = concatAdapter

        initObservers()
    }

    private fun createRecyclerView() {
        binding.rvMovies.visibility = View.VISIBLE
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovies.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == tvAdapter.itemCount && tvAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                tvAdapter.submitData(it)
            }
        }
    }
}