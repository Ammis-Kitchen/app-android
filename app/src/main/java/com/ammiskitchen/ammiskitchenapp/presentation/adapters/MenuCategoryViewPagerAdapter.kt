package com.ammiskitchen.ammiskitchenapp.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ammiskitchen.ammiskitchenapp.presentation.fragments.MenuCategoryListFragment


class MenuCategoryViewPagerAdapter(
    fragment: Fragment,
    private val items: List<String>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        MenuCategoryListFragment.getInstance(items[position])
}