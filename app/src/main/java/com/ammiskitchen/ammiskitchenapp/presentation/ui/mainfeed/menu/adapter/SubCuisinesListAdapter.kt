package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ammiskitchen.ammiskitchenapp.databinding.ListCuisinesBinding

class SubCuisinesListAdapter: ListAdapter<String, SubCuisinesListAdapter.SubCuisinesListViewHolder>(SubCuisineDiffUtilCallback) {

    private var onSubCuisineClickListener: ((String) -> Unit)? = null

    inner class SubCuisinesListViewHolder(
        val binding: ListCuisinesBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(cuisine: String) {
            binding.apply {
                buttonCategory.text = cuisine.capitalize()

                buttonCategory.setOnClickListener {
                    onSubCuisineClickListener?.let {
                        it(cuisine)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCuisinesListViewHolder {
        val binding = ListCuisinesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SubCuisinesListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubCuisinesListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun setOnSubCuisineClickListener(listener: (String) -> Unit) {
        onSubCuisineClickListener = listener
    }
}

object SubCuisineDiffUtilCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}