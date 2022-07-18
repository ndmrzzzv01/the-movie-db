package com.example.themovies.screens.people

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.network.data.KnownForPerson
import com.example.themovies.network.data.Record
import com.example.themovies.network.data.RecordClick
import com.example.themovies.paging.ListLoadStateAdapter
import com.example.themovies.screens.RecordAdapter
import com.example.themovies.screens.activities.Loading
import com.example.themovies.utils.ConnectivityTracker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class PeopleFragment : Fragment() {

    @Parcelize
    data class CustomParameters(var id: List<KnownForPerson>) : Parcelable

    @Inject
    lateinit var connectivityTracker: ConnectivityTracker
    var recordClick: RecordClick? = null
    var loading: Loading? = null
    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<PeopleViewModel>()
    private lateinit var peopleAdapter: RecordAdapter
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
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        recordClick = null
        loading = null
    }

    private fun downloadData() {
        createRecyclerView()

        peopleAdapter = RecordAdapter(object : RecordClick {
            override fun onRecordClickListener(id: Int, type: Record, customParameter: Any?) {
                loading?.showLoading()
                recordClick?.onRecordClickListener(id, Record.People, customParameter)
            }
        })
        concatAdapter = peopleAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvMovies.adapter = concatAdapter

        initObservers()
    }

    private fun createRecyclerView() {
        binding.rvMovies.visibility = View.VISIBLE
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovies.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == peopleAdapter.itemCount && peopleAdapter.itemCount > 0) {
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
                peopleAdapter.submitData(it)
            }
        }
    }
}