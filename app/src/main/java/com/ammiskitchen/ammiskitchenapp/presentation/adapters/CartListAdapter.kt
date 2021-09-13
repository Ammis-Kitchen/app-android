package com.ammiskitchen.ammiskitchenapp.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.ItemCartBinding
import com.ammiskitchen.ammiskitchenapp.domain.model.CartItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CartListAdapter: ListAdapter<CartItem, CartListAdapter.CartItemViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CartItemViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        Log.d("bindpecheck", "onBindViewHolder: ${currentItem.imageUrl}")
        holder.bind(currentItem)
    }


    inner class CartItemViewHolder(
        private val binding: ItemCartBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            binding.apply {

                cartItemName.text = cartItem.name
                cartItemAmount.text = cartItem.totalAmount.toString()
                itemCount.text = cartItem.totalQuantity.toString()

                Glide
                    .with(root.context)
                    .applyDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                    )
                    .load(cartItem.imageUrl)
                    .into(cartItemImage)

            }

        }

    }


    class DiffUtilCallback: DiffUtil.ItemCallback<CartItem>() {

        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) =
            oldItem == newItem

    }

}