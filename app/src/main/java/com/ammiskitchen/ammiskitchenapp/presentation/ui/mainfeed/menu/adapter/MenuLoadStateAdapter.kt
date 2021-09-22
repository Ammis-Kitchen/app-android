package com.ammiskitchen.ammiskitchenapp.presentation.ui.mainfeed.menu.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ammiskitchen.ammiskitchenapp.databinding.ListMenuLoadingBinding

class MenuLoadStateAdapter: LoadStateAdapter<MenuLoadStateAdapter.MenuLoadStateViewHolder>() {

    private var onMenuRetryClickListener: ((String) -> Unit)? = null

    inner class MenuLoadStateViewHolder(val binding: ListMenuLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.apply {
                loadStateRetry.isVisible = loadState !is LoadState.Loading
                loadStateErrorMessage.isVisible = loadState !is LoadState.Loading
                loadStateProgress.isVisible = loadState is LoadState.Loading

                if (loadState is LoadState.Error && loadState.error.localizedMessage.equals("No more cuisines available.")) {
                    loadStateErrorMessage.text = loadState.error.localizedMessage
                    loadStateRetry.isVisible = false
                } else if (loadState is LoadState.Error){
                    loadStateErrorMessage.text = loadState.error.localizedMessage
                }

                loadStateRetry.setOnClickListener {
                    onMenuRetryClickListener?.let {
                        it("Hello")
                    }
                }
            }
        }
    }

    fun setOnMenuRetryClickListener(listener: (String) -> Unit) {
        onMenuRetryClickListener = listener
    }

    override fun onBindViewHolder(holder: MenuLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MenuLoadStateViewHolder {
        val binding = ListMenuLoadingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MenuLoadStateViewHolder(binding)
    }
}