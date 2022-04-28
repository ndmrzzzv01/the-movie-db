package com.example.themovies.screens.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.data.People
import com.example.themovies.databinding.FragmentMainBinding
import com.example.themovies.views.adapters.PeopleAdapter

class PeopleFragment : Fragment(), PeopleContract.PeopleView {

    private lateinit var binding: FragmentMainBinding
    private var presenter: PeopleContract.PeoplePresenter? = null
    private var adapter = PeopleAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PeoplePresenter(this)
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
        presenter?.loadPopularPeople()
    }

    override fun onStop() {
        super.onStop()
        presenter?.cancel()
    }

    override fun displayListOfPeople(listOfPeople: List<People>) {
        binding.rvMovies.visibility = View.VISIBLE
        adapter.people = listOfPeople
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