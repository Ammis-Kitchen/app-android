package com.ammiskitchen.ammiskitchenapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentAuthBinding
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.AuthStateEvent
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthFragment : Fragment() {

    companion object {
        fun newInstance() = AuthFragment()
    }


    private var _binding: FragmentAuthBinding? = null
    private val binding
        get() = _binding!!

    val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root

//        binding = DataBindingUtil
//            .inflate(inflater, R.layout.fragment_auth, container, false)
//
//        phoneAuthOptionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setActivity(requireActivity())
//            .setTimeout(60L, TimeUnit.SECONDS)
//
//        authViewModelFactory = AuthViewModelFactory(userRepositoryImpl, phoneAuthOptionsBuilder)
//        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)
//
//        binding.authViewModel = authViewModel
//
////        if working with live data use lifecycle owner
//        binding.lifecycleOwner = this
//
//        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        subscribeObservers()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {

        authViewModel.OTPState.observe(viewLifecycleOwner, {
            when(it) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.ammisKitchenLogo.visibility = View.GONE
                    binding.layoutPhone.visibility = View.GONE
                }
                is DataState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.ammisKitchenLogo.visibility = View.VISIBLE
                    binding.layoutVerification.visibility = View.VISIBLE
                }
                is DataState.Error -> {
                    binding.layoutPhone.visibility = View.VISIBLE
                    binding.ammisKitchenLogo.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    when(it.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            binding.textInputLayoutMobileNumber.error = it.message.toString()
                            showSnackbar(it.message.toString())
                        }
                        else -> {
                            showSnackbar(it.message.toString())
                        }
                    }
                }
            }
        })

        authViewModel.signInState.observe(viewLifecycleOwner, {
            when(it) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.ammisKitchenLogo.visibility = View.GONE
                    binding.layoutVerification.visibility = View.GONE
                }
                is DataState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    authViewModel.setStateEvent(AuthStateEvent.UserStateEvent)
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.ammisKitchenLogo.visibility = View.VISIBLE
                    binding.layoutVerification.visibility = View.VISIBLE
                    when(it.exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            binding.textInputLayoutOTP.error = "Invalid OTP."
                        }
                        else -> {
                            showSnackbar("Something went Wrong. Please try again.")
                        }
                    }
                }
            }
        })

        authViewModel.userState.observe(viewLifecycleOwner, {
            when(it) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if(it.data != null) {
//                        makeReplaceFragmentTransaction(BottomNavigationFragment::class.java)
//                        navController.navigate(AuthFragmentDirections.actionAuthFragmentToBottomNavigationFragment())
//                        navController.navigate(R.id.action_authFragment_to_homeFragment)
                    }
                    else {
                        authViewModel.setStateEvent(AuthStateEvent.CreateUserStateEvent)
                    }
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showSnackbar(it.message.toString())
                }
            }
        })

        authViewModel.createUserState.observe(viewLifecycleOwner, {
            when(it) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    makeReplaceFragmentTransaction(UserDataFragment::class.java)
//                    navController.navigate(AuthFragmentDirections.actionAuthFragmentToUserDataFragment())
//                    navController.navigate(R.id.action_authFragment_to_userDataFragment)
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showSnackbar(it.message.toString())
                }
            }
        })

    }

    //    private fun subscribeObservers() {
//        authViewModel.goToUserDataFragment.observe(viewLifecycleOwner, {
//            if(it) {
//                navController.navigate(R.id.action_authFragment_to_userDataFragment)
//            }
//        })
//
//        authViewModel.goToHomeFragment.observe(viewLifecycleOwner, {
//            if(it) {
////                val navigationGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)
////                navigationGraph.startDestination = R.id.homeFragment
////                navController.graph = navigationGraph
//                navController.navigate(R.id.action_authFragment_to_homeFragment)
//            }
//        })
//
//        authViewModel.errorMessage.observe(viewLifecycleOwner, {
//            it?.let {
////                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
//                showSnackbar(it)
//            }
//        })
//
//    }
//
    private fun showSnackbar(message: String) {
        Snackbar
            .make(binding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun setListeners() {
        binding.btnSendOTP.setOnClickListener {

            authViewModel.countryCode = binding.ccp.selectedCountryCode.toString().trim()
            authViewModel.phoneNumber = binding.etMobileNumber.text.toString().trim()

            Log.d("CCP", authViewModel.countryCode!!)

            val phoneAuthOptionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setActivity(requireActivity())
            authViewModel.phoneAuthOptionsBuilder = phoneAuthOptionsBuilder

            authViewModel.setStateEvent(AuthStateEvent.GetOTPEvent)
        }

        binding.btnSubmitOTP.setOnClickListener {
            authViewModel.verificationCode = binding.etOTP.text.toString().trim()
            authViewModel.setStateEvent(AuthStateEvent.SubmitOTPEvent)
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