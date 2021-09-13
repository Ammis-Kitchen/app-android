package com.ammiskitchen.ammiskitchenapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.ItemMenuBinding
import com.ammiskitchen.ammiskitchenapp.domain.model.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MenuCategoryRecyclerViewAdapter(
    private val onMenuItemListener: OnMenuItemListener
) : RecyclerView.Adapter<MenuCategoryRecyclerViewAdapter.MenuCategoryViewHolder>() {

    private var items: List<MenuItem> = emptyList()


    fun submitList(items: List<MenuItem>) {
//        if (!items.isEmpty()) {
//            Log.d("check", items[0].category)
//        }

        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryViewHolder =
        MenuCategoryViewHolder(
            ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MenuCategoryViewHolder, position: Int) {

        holder.bind(items[position])

    }

    override fun getItemCount(): Int = items.size


    inner class MenuCategoryViewHolder(
        private val binding: ItemMenuBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnAddToCart.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onMenuItemListener.addItem(adapterPosition)
                }
            }
        }

        fun bind(menuItem: MenuItem) {

            binding.apply {
                menuItemName.text = menuItem.name
                menuItemAmount.text = menuItem.amount.toString()
            }

            Glide
                .with(binding.root.context)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                )
                .load(menuItem.imageUrl)
                .into(binding.menuItemImage)

        }

    }



    interface OnMenuItemListener {
        fun addItem(position: Int)
    }

}