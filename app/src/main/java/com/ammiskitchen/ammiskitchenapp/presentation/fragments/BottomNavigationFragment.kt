package com.ammiskitchen.ammiskitchenapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentBottomNavigationBinding
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.CartEvent
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.CartViewModel

class BottomNavigationFragment : Fragment() {

    companion object {
        fun newInstance() = BottomNavigationFragment()
    }


    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding
        get() = _binding!!

    val cartViewModel: CartViewModel by activityViewModels()

    private lateinit var homeFragment: HomeFragment
    private lateinit var menuFragment: MenuFragment
    private lateinit var cartFragment: CartFragment
    private lateinit var accountFragment: AccountFragment
    private lateinit var activeFragment: Fragment

    private var home = false
    private var cart = false
    private var menu = false
    private var account = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListeners()
        initializeBottomNavigationFragments()
        subscribeObservers()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initializeBottomNavigationFragments() {
        homeFragment = HomeFragment()
        menuFragment = MenuFragment()
        cartFragment = CartFragment()
        accountFragment = AccountFragment()

        activeFragment = homeFragment
        binding.bottomNavigationView.selectedItemId = R.id.home
    }

    private fun addListeners() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    if (!home) {
                        home = true
                        childFragmentManager.commit {
//                            hide(activeFragment)
                            add(R.id.bottom_navigation_fragment_container, homeFragment)
                        }
                    } else {
                        childFragmentManager.commit {
                            hide(activeFragment)
                            show(homeFragment)
                        }
                    }
                    activeFragment = homeFragment
                }
                R.id.menu -> {
                    if (!menu) {
                        menu = true
                        childFragmentManager.commit {
                            hide(activeFragment)
                            add(R.id.bottom_navigation_fragment_container, menuFragment)
                        }
                    } else {
                        childFragmentManager.commit {
                            hide(activeFragment)
                            show(menuFragment)
                        }
                    }
                    activeFragment = menuFragment
                }
                R.id.cart -> {
                    if (!cart) {
                        cart = true
                        childFragmentManager.commit {
                            hide(activeFragment)
                            add(R.id.bottom_navigation_fragment_container, cartFragment)
                        }
                    } else {
                        childFragmentManager.commit {
                            hide(activeFragment)
                            show(cartFragment)
                        }
                    }
                    activeFragment = cartFragment
                }
                R.id.account -> {
                    if (!account) {
                        account = true
                        childFragmentManager.commit {
                            hide(activeFragment)
                            add(R.id.bottom_navigation_fragment_container, accountFragment)
                        }
                    } else {
                        childFragmentManager.commit {
                            hide(activeFragment)
                            show(accountFragment)
                        }
                    }
                    activeFragment = accountFragment
                }
            }
            true
        }
    }

    private fun subscribeObservers() {

        cartViewModel.setStateEvent(CartEvent.GetCartCountEvent)
        cartViewModel.cartCountState.observe(viewLifecycleOwner, {
            it?.let {
                binding.bottomNavigationView
                    .getOrCreateBadge(R.id.cart)
                    .apply {
                        number = (it * 2).toInt()
                        isVisible = true
                    }
            }
        })
    }

}