package com.example.androidchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidchallenge.R
import com.example.androidchallenge.databinding.FragmentMainBinding
import com.example.androidchallenge.databinding.NavHeaderBinding
import com.example.androidchallenge.ui.adapters.TeamsAdapter
import com.example.androidchallenge.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var bindingHeader: NavHeaderBinding

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
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        setupHeaderBinding()
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

        viewModel.user.observe(viewLifecycleOwner) {
            bindingHeader.user = it
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            with(binding){
                if (it){
                    progressBarList.visibility = View.VISIBLE
                    rvTeams.visibility = View.GONE
                } else {
                    progressBarList.visibility = View.GONE
                    rvTeams.visibility = View.VISIBLE
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(binding.main,
                getString(R.string.unexpected_error) + it.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

    }

    private fun setupHeaderBinding(){
        val navigationView = requireActivity().findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        bindingHeader = NavHeaderBinding.bind(headerView)
    }

}