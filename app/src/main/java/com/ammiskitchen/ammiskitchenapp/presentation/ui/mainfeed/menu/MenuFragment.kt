package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ammiskitchen.ammiskitchenapp.data.util.Resource
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentMenuBinding
import com.ammiskitchen.ammiskitchenapp.domain.usecase.menu.GetMenuUseCase
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter.CuisinesListAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter.MenuListAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter.MenuLoadStateAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter.SubCuisinesListAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.paging.MenuPagingRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MenuFragment : Fragment() {

    @Inject
    lateinit var menuViewModelFactory: MenuViewModelFactory

    @Inject
    lateinit var cuisinesListAdapter: CuisinesListAdapter

    @Inject
    lateinit var subCuisinesListAdapter: SubCuisinesListAdapter

    @Inject
    lateinit var getMenuUseCase: GetMenuUseCase

    @Inject
    lateinit var menuListAdapter: MenuListAdapter

    @Inject
    lateinit var menuLoadStateAdapter: MenuLoadStateAdapter

    private lateinit var viewModel: MenuViewModel

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    var subCuisine: String = ""
    var mainCuisine: String = ""

    var cuisinesList = listOf("Chinese", "Italian", "Indian", "Punjabi")

    private lateinit var menuPagingRepository: MenuPagingRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, menuViewModelFactory).get(MenuViewModel::class.java)
        initRecyclerView()
        viewModel.responseCuisines.observe({ lifecycle }) {
            when(it) {
                is Resource.Success -> {
                    it.data?.let { responseCuisines ->
                        cuisinesListAdapter.submitList(responseCuisines.cuisines)
                        mainCuisine = responseCuisines.cuisines[0]
                        getSubCuisines(mainCuisine)
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }

        viewModel.responseSubCuisines.observe({ lifecycle }) {
            when(it) {
                is Resource.Success -> {
                    it.data?.let { responseCuisines ->
                        subCuisinesListAdapter.submitList(responseCuisines.subcategory)
                        subCuisine = responseCuisines.subcategory[0]

                        setPagingRepository()
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }

        cuisinesListAdapter.setOnCuisineClickListener {
            println("Debug: ${it}")
            mainCuisine = it
            getSubCuisines(mainCuisine)
        }

        subCuisinesListAdapter.setOnSubCuisineClickListener {
            println("Debug: ${it}")
            subCuisine = it
            setPagingRepository()
        }

    }

    private fun initRecyclerView() {
        binding.recyclerviewCuisines.apply {
            adapter = cuisinesListAdapter
            cuisinesListAdapter.notifyDataSetChanged()
        }
        binding.recyclerviewSubcuisines.apply {
            adapter = subCuisinesListAdapter
            subCuisinesListAdapter.notifyDataSetChanged()
        }

        binding.recyclerviewMenu.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuListAdapter.withLoadStateFooter(
                footer = menuLoadStateAdapter
            )
        }
        getCuisines()
    }

    private fun setPagingRepository() {
        menuPagingRepository = MenuPagingRepository(getMenuUseCase, "italian", "main")
        viewModel.setPagingRepository(menuPagingRepository)
        getMenuList()
    }

    private fun getMenuList() {
        lifecycleScope.launch {
            viewModel.getMenu(mainCuisine, subCuisine).collect {
                it.let { data ->
                    menuListAdapter.submitData(data)
                }
            }
        }
    }

    private fun getCuisines() {
        viewModel.getCuisinesList()
    }

    private fun getSubCuisines(cuisines: String) {
        viewModel.getSubCuisinesList(cuisines)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}