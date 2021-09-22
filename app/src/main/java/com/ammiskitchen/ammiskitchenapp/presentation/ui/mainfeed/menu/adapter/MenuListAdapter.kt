package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ammiskitchen.ammiskitchenapp.data.models.entities.MenuList
import com.ammiskitchen.ammiskitchenapp.databinding.ListMenuBinding

class MenuListAdapter: PagingDataAdapter<MenuList, MenuListAdapter.MenuListAdapterViewHolder>(MenuDiffUtilCallback) {

    private var onMenuClickListener: ((MenuList) -> Unit) ?= null

    inner class MenuListAdapterViewHolder(
        val binding: ListMenuBinding
    ): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(menuList: MenuList) {
            binding.apply {
                root.setOnClickListener {
                    onMenuClickListener?.let {
                        it(menuList)
                    }
                }
                textCuisinename.text = menuList.name.capitalize()
                textCuisinecategory.text = "${menuList.category.capitalize()}, ${menuList.subCategory.capitalize()}"
                textPrice.text = "₹${menuList.amount.toString()} for half Kg"
            }
        }
    }

    override fun onBindViewHolder(holder: MenuListAdapterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListAdapterViewHolder {
        val binding = ListMenuBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuListAdapterViewHolder(binding)
    }

    fun setOnMenuClickListener(listener: (MenuList) -> Unit) {
        onMenuClickListener = listener
    }

}

object MenuDiffUtilCallback: DiffUtil.ItemCallback<MenuList>() {
    override fun areItemsTheSame(oldItem: MenuList, newItem: MenuList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MenuList, newItem: MenuList): Boolean {
        return oldItem.id == newItem.id
    }

}