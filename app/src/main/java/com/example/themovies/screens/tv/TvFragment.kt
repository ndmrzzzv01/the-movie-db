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
import com.example.themovies.data.RecordClick
import com.example.themovies.data.Record
import com.example.themovies.data.paging.ListLoadStateAdapter
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.utils.NetworkUtils
import com.example.themovies.views.adapters.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvFragment : Fragment() {

    var recordClick: RecordClick? = null
    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<TvViewModel>()
    private lateinit var tvAdapter: MovieAdapter
    private lateinit var concatAdapter: ConcatAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        recordClick = context as RecordClick
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.apply {
            if (!NetworkUtils.isNetworkConnected(requireContext())) {
                rvMovies.visibility = View.INVISIBLE
                tvError.visibility = View.VISIBLE
                btnRetry.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        if (NetworkUtils.isNetworkConnected(requireContext())) {
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
    }

    private fun downloadData() {
        createRecyclerView()

        tvAdapter = MovieAdapter(object : RecordClick {
            override fun onRecordClickListener(id: Int, type: Record) {
                recordClick?.onRecordClickListener(id, Record.TV)
            }
        })
        concatAdapter = tvAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvMovies.adapter = concatAdapter

        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                tvAdapter.submitData(it)
            }
        }
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
}