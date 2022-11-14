package com.example.androidchallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidchallenge.R
import com.example.androidchallenge.databinding.FragmentLoginBinding
import com.example.androidchallenge.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLoginBinding.inflate(layoutInflater, container, false).apply {
        binding = this
        viewModel = this@LoginFragment.viewModel
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        when (viewModel.uiStateAuth.value){
            is LoginViewModel.AuthenticationUiState.Empty -> {}
            is LoginViewModel.AuthenticationUiState.Loading -> {
                binding.llLoginScreen.visibility = View.GONE
                binding.progressBarLogin.visibility = View.VISIBLE
            }
            is LoginViewModel.AuthenticationUiState.Error -> {

            }
            is LoginViewModel.AuthenticationUiState.Success -> {
                findNavController().navigate(R.id.mainFragment)
            }
        }

    }

}