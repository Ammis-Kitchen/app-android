package com.ammiskitchen.ammiskitchenapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ammiskitchen.ammiskitchenapp.domain.model.CartItem
import com.ammiskitchen.ammiskitchenapp.domain.usecases.AddToCartUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecases.GetCartCountUseCase
import com.ammiskitchen.ammiskitchenapp.domain.usecases.GetCartUseCase
import com.ammiskitchen.ammiskitchenapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel
@Inject
constructor(
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartCountUseCase: GetCartCountUseCase,
    private val getCartUseCase: GetCartUseCase
) : ViewModel() {

    lateinit var cartCountState: LiveData<Double>
    lateinit var getCartState: LiveData<DataState<List<CartItem>>>

    private fun addToCart(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            addToCartUseCase.execute(cartItem)
        }
    }

    private fun getCartCount() {
        cartCountState = getCartCountUseCase.execute()
    }

    private fun getCart() {
        getCartState = getCartUseCase.execute().asLiveData()
    }

    fun setStateEvent(cartEvent: CartEvent) {
        when (cartEvent) {
            is CartEvent.AddToCartEvent -> {
                addToCart(cartEvent.cartItem)
            }
            is CartEvent.GetCartCountEvent -> {
                getCartCount()
            }
            is CartEvent.GetCartEvent -> {
                getCart()
            }
        }
    }

}

sealed class CartEvent {
    data class AddToCartEvent(val cartItem: CartItem) : CartEvent()
    object GetCartCountEvent : CartEvent()
    object GetCartEvent: CartEvent()
}