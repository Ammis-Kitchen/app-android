package com.ammiskitchen.ammiskitchenapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentMenuBinding
import com.ammiskitchen.ammiskitchenapp.presentation.adapters.MenuCategoryViewPagerAdapter
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.MenuStateEvent
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.MenuViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }

    private var _binding: FragmentMenuBinding? = null
    private val binding
        get() = _binding!!

    val menuViewModel: MenuViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("BackStack", "oncreate MenuFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeObservers() {

        menuViewModel.setStateEvent(MenuStateEvent.GetMenuCategoryEvent)
        menuViewModel.menuCategoryState.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {
                    val categories = it.data.categories
//                    Log.d("CATEGORIES", categories!![0])

                    val mAdapter = MenuCategoryViewPagerAdapter(this, categories!!)
                    binding.viewPagerMenu
                        .apply {
                            adapter = mAdapter
                        }
                    TabLayoutMediator(binding.tabLayoutMenu, binding.viewPagerMenu) { tab, position ->
                        tab.text = categories[position]
                    }.attach()
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Error -> {
//                    Toast.makeText(requireContext(), it.exception.message.toString(), Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        })



//        fun getMenuByCategory(category: String): Menu {
//
//            var menu: Menu = Menu(emptyList())
//
//            menuViewModel.setStateEvent(MenuStateEvent.GetMenuEventByCategory())
//            menuViewModel.menuState.observe(viewLifecycleOwner, {
//                when (it) {
//                    is DataState.Loading -> {
//                        progressBar.visibility = View.VISIBLE
//                    }
//                    is DataState.Success -> {
//                        menu = it.data
//                        Log.d("Menu", it.data.items[0].imageUrl)
//                        progressBar.visibility = View.GONE
//                    }
//                    is DataState.Error -> {
//                        Log.d("deserialize", it.exception.message.toString())
//                        Toast.makeText(
//                            requireContext(),
//                            it.exception.message.toString(),
//                            Toast.LENGTH_LONG
//                        ).show()
//                        progressBar.visibility = View.GONE
//                    }
//                }
//            })
//            return menu
//        }

    }

}