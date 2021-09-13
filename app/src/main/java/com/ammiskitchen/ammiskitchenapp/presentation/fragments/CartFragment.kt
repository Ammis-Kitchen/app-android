package com.ammiskitchen.ammiskitchenapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentCartBinding
import com.ammiskitchen.ammiskitchenapp.presentation.adapters.CartListAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.CartEvent
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.CartViewModel
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.google.android.material.bottomnavigation.BottomNavigationView


class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }


    private var _binding: FragmentCartBinding? = null
    private val binding
        get() = _binding!!

    val cartViewModel: CartViewModel by activityViewModels()

    private lateinit var cartListAdapter: CartListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BackStack", "oncreate CartFragment")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartListAdapter = CartListAdapter()

        binding.apply {
            cartRecyclerView.apply {
                adapter = cartListAdapter
                layoutManager = LinearLayoutManager(requireContext())
//                setHasFixedSize(true)
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
        }
        subscribeObservers()
        setListeners()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {
        cartViewModel.setStateEvent(CartEvent.GetCartEvent)
        cartViewModel.getCartState.observe(viewLifecycleOwner, {
            when(it) {
                is DataState.Success -> {
                    if (it.data.isNullOrEmpty()) {
                        binding.apply {
                            cartRecyclerView.visibility = View.GONE
                            exploreMenu.visibility = View.VISIBLE
                        }
                    } else {
                        binding.apply {
                            cartRecyclerView.visibility = View.VISIBLE
                            exploreMenu.visibility = View.GONE
                        }
//            Log.d("cartnai", "subscribeObservers: ${it[0].imageUrl}")
                        cartListAdapter.submitList(it.data)

                    }
                }
                else -> {
                    // kuch nai
                }
            }

        })
    }

    private fun setListeners() {
        binding.apply {
            exploreMenu.setOnClickListener {
                val bottomNavBar =
                    requireParentFragment().view?.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

                if (bottomNavBar != null) {
                    bottomNavBar.selectedItemId = R.id.menu
                }

            }
        }
    }

}