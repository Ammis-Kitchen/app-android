package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentMainFeedBinding
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentMobileVerificationBinding

class MainFeedFragment: Fragment() {

    private var _binding: FragmentMainFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainFeedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFeedBottomNavigation.setupWithNavController(Navigation.findNavController(
            requireActivity(), R.id.main_feed_host_fragment))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}