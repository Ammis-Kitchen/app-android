package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ammiskitchen.ammiskitchenapp.databinding.ListCuisinesBinding

class CuisinesListAdapter: ListAdapter<String, CuisinesListAdapter.CuisinesListViewHolder>(CuisineDiffUtilCallback) {

    private var onCuisineClickListener: ((String) -> Unit) ?= null

    inner class CuisinesListViewHolder(
        val binding: ListCuisinesBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(cuisine: String) {
            binding.apply {
                buttonCategory.text = cuisine

                buttonCategory.setOnClickListener {
                    onCuisineClickListener?.let {
                        it(cuisine)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisinesListViewHolder {
        val binding = ListCuisinesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CuisinesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CuisinesListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun setOnCuisineClickListener(listener: (String) -> Unit) {
        onCuisineClickListener = listener
    }
}

object CuisineDiffUtilCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}