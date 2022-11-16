package com.example.androidchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidchallenge.R
import com.example.androidchallenge.databinding.FragmentLoginBinding
import com.example.androidchallenge.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        setObservers()
    }

    private fun setObservers(){
        viewModel.success.observe(viewLifecycleOwner) { success ->
            if (success)
                findNavController().navigate(R.id.mainFragment)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading){
                binding.llLoginScreen.visibility = View.GONE
                binding.progressBarLogin.visibility = View.VISIBLE
            }
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Snackbar.make(binding.llLogin,
                getString(R.string.unexpected_error) + error.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
        viewModel.errorBody.observe(viewLifecycleOwner) { errorBody ->
            Snackbar.make(binding.llLogin,
                getString(R.string.api_error) + errorBody?.string(), Snackbar.LENGTH_SHORT).show()
        }

        viewModel.userNameErrorMessageResId.observe(viewLifecycleOwner) {
            binding.textInputLayoutUser.error = getString(it)
        }
        viewModel.passwordErrorMessageResId.observe(viewLifecycleOwner) {
            binding.textInputLayoutPassword.error = getString(it)
        }
    }

}