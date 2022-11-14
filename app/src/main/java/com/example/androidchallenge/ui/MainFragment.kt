package com.example.androidchallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidchallenge.databinding.FragmentMainBinding
import com.example.androidchallenge.ui.adapters.TeamsAdapter
import com.example.androidchallenge.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    private val adapter: TeamsAdapter by lazy {
        TeamsAdapter {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMainBinding.inflate(layoutInflater, container, false).apply {
        binding = this
        viewModel = this@MainFragment.viewModel
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        with (binding.rvTeams) {
            adapter = this@MainFragment.adapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        viewModel.teams.observe(viewLifecycleOwner) {
            adapter.submitList(it.map { team ->
                TeamsAdapter.Item(
                    item = team
                )
            })
        }

    }

}