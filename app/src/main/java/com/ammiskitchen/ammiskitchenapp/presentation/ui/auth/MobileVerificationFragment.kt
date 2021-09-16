package com.ammiskitchen.ammiskitchenapp.presentation.ui.auth

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentLoginBinding
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentMobileVerificationBinding

class MobileVerificationFragment : Fragment() {

    private var _binding: FragmentMobileVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMobileVerificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val first = "Didn't receive code? "
        val second = "<font color='#000000'>Request again</font>"
        binding.textResendCode.setText(Html.fromHtml(first + second))

        binding.buttonVerify.setOnClickListener {
            findNavController().navigate(R.id.action_mobileVerificationFragment_to_mainFeedFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}