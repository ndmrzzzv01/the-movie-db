package com.example.themovies.screens.people

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.activities.Loading
import com.example.themovies.data.RecordType
import com.example.themovies.data.RecordClick
import com.example.themovies.data.Record
import com.example.themovies.data.paging.ListLoadStateAdapter
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.utils.NetworkUtils
import com.example.themovies.views.adapters.MovieAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PeopleFragment : Fragment(), PeopleContract.PeopleView {

    var recordClick: RecordClick? = null
    private lateinit var binding: FragmentMainBinding
//    @Inject
//    lateinit var presenter: PeopleContract.PeoplePresenter
    var presenter: PeopleContract.PeoplePresenter? = null
    private lateinit var peopleAdapter: MovieAdapter
    private lateinit var concatAdapter: ConcatAdapter
    var loading: Loading? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        recordClick = context as RecordClick
        loading = context as Loading
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PeoplePresenterImpl(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        showInfoWithoutInternet()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter?.loadPopularPeople()

    }

    override fun onStop() {
        super.onStop()
        presenter?.cancel()
    }

    override fun onDetach() {
        super.onDetach()
        recordClick = null
    }

    override fun displayListOfPeople(pager: Pager<Int, RecordType>) {
        createRecyclerView()

        peopleAdapter = MovieAdapter(object : RecordClick {
            override fun onRecordClickListener(id: Int, type: Record) {
                loading?.showLoading()
                recordClick?.onRecordClickListener(id, Record.People)
            }
        })
        concatAdapter = peopleAdapter.withLoadStateFooter(ListLoadStateAdapter())
        binding.rvMovies.adapter = concatAdapter

        lifecycleScope.launch {
            pager.flow.collectLatest {
                peopleAdapter.submitData(it)
            }
        }

    }

    override fun onFail() {
        binding.apply {
            rvMovies.visibility = View.INVISIBLE
            btnRetry.visibility = View.GONE
            tvError.apply {
                visibility = View.VISIBLE
                text = "Something wrong..."
            }
        }
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

    private fun showInfoWithoutInternet() {
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
                            rvMovies.visibility = View.VISIBLE
                            presenter?.loadPopularPeople()
                        } else {
                            return@setOnClickListener
                        }
                    }
                }
            }
        }
    }

}