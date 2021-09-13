package com.ammiskitchen.ammiskitchenapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentMenuCategoryListBinding
import com.ammiskitchen.ammiskitchenapp.presentation.adapters.MenuCategoryRecyclerViewAdapter
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.MenuStateEvent
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuCategoryListFragment : Fragment() {

    companion object {
        fun getInstance(name: String): Fragment {
            val fragment = MenuCategoryListFragment()
            val args = Bundle()
            args.putString("category", name)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentMenuCategoryListBinding? = null
    private val binding
        get() = _binding!!


    private val menuViewModel: MenuViewModel by activityViewModels()
    private lateinit var menuCategoryRecyclerViewAdapter: MenuCategoryRecyclerViewAdapter
    private lateinit var category: String

    private lateinit var onMenuItemListener: MenuCategoryRecyclerViewAdapter.OnMenuItemListener
    private lateinit var customizeItemBottomSheetDialogFragment: CustomizeItemBottomSheetDialogFragment

//    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("oncreatelisttab", "phirse nai yaar, ${arguments?.getString("category").toString()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuCategoryListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category = arguments?.getString("category").toString()

        customizeItemBottomSheetDialogFragment = CustomizeItemBottomSheetDialogFragment.newInstance()

//        configureBottomSheet()

        setListeners()

        initRecyclerView()

        subscribeObservers()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {

        onMenuItemListener = object : MenuCategoryRecyclerViewAdapter.OnMenuItemListener {
            override fun addItem(position: Int) {

                menuViewModel.setStateEvent(
                    MenuStateEvent.SetSelectedMenuItemEvent(
                        position,
                        category
                    )
                )

                customizeItemBottomSheetDialogFragment
                    .show(
                        childFragmentManager,
                        "item_customization"
                    )

            }

        }

    }

    private fun subscribeObservers() {

        menuViewModel.setStateEvent(MenuStateEvent.GetMenuByCategoryEvent(category))
        menuViewModel.observeMenuCategory(category)?.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {

                    menuCategoryRecyclerViewAdapter.submitList(it.data.items)
                    menuCategoryRecyclerViewAdapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.exception?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

    }

    private fun initRecyclerView() {
        menuCategoryRecyclerViewAdapter = MenuCategoryRecyclerViewAdapter(onMenuItemListener)
        binding.menuCategoryRecyclerView.apply {
            adapter = menuCategoryRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }


//    private fun configureBottomSheet() {
//        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.bottomSheetBehavior)
//
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//
//                val myView = binding.bottomSheet.addToCartLinear
//
//                myView
//                    .animate()
//                    .y(
//                        (if (slideOffset <= 0)
//                            bottomSheet.y + bottomSheetBehavior.peekHeight - myView.height
//                        else
//                            bottomSheet.height - myView.height).toFloat()
//                    )
//                    .setDuration(0)
//                    .start()
//            }
//
//        })
//    }

}