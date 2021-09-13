package com.ammiskitchen.ammiskitchenapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
//import androidx.navigation.NavController
//import androidx.navigation.fragment.findNavController
import com.ammiskitchen.ammiskitchenapp.R

import com.ammiskitchen.ammiskitchenapp.databinding.FragmentUserDataBinding
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.UserDataStateEvent
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.UserDataViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class UserDataFragment : Fragment() {

    private var _binding: FragmentUserDataBinding? = null
    private val binding
        get() = _binding!!

    private val userDataViewModel: UserDataViewModel by viewModels()

//    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUserDataBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        navController = findNavController()

        setListeners()
        subscribeObservers()

//        userDataViewModel.firstName = binding.etFirstName.text.toString().trim()
//        userDataViewModel.lastName = binding.etLastName.text.toString().trim()
//        userDataViewModel.address = binding.etAddress.text.toString().trim()
//        userDataViewModel.email = binding.etEmail.text.toString().trim()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {

        userDataViewModel.errorFirstName.observe(viewLifecycleOwner, {
            it?.let {
                binding.textInputLayoutFirstName.error = it
            }
        })
        userDataViewModel.errorLastName.observe(viewLifecycleOwner, {
            it?.let {
                binding.textInputLayoutLastName.error = it
            }
        })
        userDataViewModel.errorAddress.observe(viewLifecycleOwner, {
            it?.let {
                binding.textInputLayoutAddress.error = it
            }
        })
        userDataViewModel.errorEmail.observe(viewLifecycleOwner, {
            it?.let {
                binding.textInputLayoutEmail.error = it
            }
        })


        userDataViewModel.saveUserDataState.observe(viewLifecycleOwner, {
            when(it) {
                is DataState.Loading -> {
                    binding.parentLayout.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {
                    binding.progressBar.visibility - View.GONE
//                    navController.navigate(R.id.action_userDataFragment_to_homeFragment)
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.parentLayout.visibility = View.VISIBLE
                    showSnackbar("Something went wrong. Please try again.")
                }
            }
        })

    }

    private fun showSnackbar(message: String) {
        Snackbar
            .make(binding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun setListeners() {

        binding.skipUserData.setOnClickListener {

//            navController.navigate(UserDataFragmentDirections.actionUserDataFragmentToBottomNavigationFragment())
//            navController.navigate(R.id.action_userDataFragment_to_homeFragment)
        }

        binding.btnContinue.setOnClickListener {
            userDataViewModel.firstName = binding.etFirstName.text.toString().trim()
            userDataViewModel.lastName = binding.etLastName.text.toString().trim()
            userDataViewModel.address = binding.etAddress.text.toString().trim()
            userDataViewModel.email = binding.etEmail.text.toString().trim()

            userDataViewModel.setStateEvent(UserDataStateEvent.SaveUserDataStateEvent)
        }

    }

    private fun makeReplaceFragmentTransaction(fragmentClass: Class<out Fragment>) {
        childFragmentManager
            .beginTransaction()
            .apply {
                replace(R.id.root_fragment_container, fragmentClass, null)
                commit()
            }
    }

}