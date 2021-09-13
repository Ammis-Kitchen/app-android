package com.ammiskitchen.ammiskitchenapp.presentation.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.activityViewModels
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.FragmentCustomizeItemBottomsheetDialogBinding
import com.ammiskitchen.ammiskitchenapp.domain.model.CartItem
import com.ammiskitchen.ammiskitchenapp.domain.model.MenuItem
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.CartEvent
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.CartViewModel
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.MenuViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomizeItemBottomSheetDialogFragment: BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = CustomizeItemBottomSheetDialogFragment()
    }

    val menuViewModel: MenuViewModel by activityViewModels()

    val cartViewModel: CartViewModel by activityViewModels()

    private var _binding: FragmentCustomizeItemBottomsheetDialogBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var menuItem: MenuItem
    private var screenHeight: Int = 0
    private val peekHeightPercent: Float = 0.7F
    private val expandedOffsetPercent: Float = 0.3F

    private val ITEM_QUANTITY_INCREMENT_STEP: Float = 0.5F

    private var totalQuantity: Double = 0.0
    private var totalAmount: Double = 0.0

    private lateinit var displayMetrics: DisplayMetrics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomizeItemBottomsheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        screenHeight = requireActivity().resources.displayMetrics.heightPixels

        val layoutParams = binding.bottomSheetDialogParentLayout.layoutParams
        layoutParams.height = (screenHeight * peekHeightPercent).toInt()
        binding.bottomSheetDialogParentLayout.layoutParams = layoutParams

        setInitialState()
        setMenuItem()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        bottomSheet.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val sheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            BottomSheetBehavior.from(sheet!!).isFitToContents = false
//            BottomSheetBehavior.from(sheet!!).state = STATE_EXPANDED
            BottomSheetBehavior.from(sheet!!).peekHeight = (screenHeight * peekHeightPercent).toInt()
            BottomSheetBehavior.from(sheet!!).expandedOffset = (screenHeight * expandedOffsetPercent).toInt()
        }
        return bottomSheet
    }


    private fun setMenuItem() {

        menuItem = menuViewModel.getSelectedMenuItem()
        binding.bottomSheetItemName.text = menuItem.name

        val addString = "Add ₹${menuItem.amount}"
        binding.bottomSheetAddToCart.text = addString

        Glide
            .with(this)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
            )
            .load(menuItem.imageUrl)
            .into(binding.bottomSheetItemImage)

    }

    private fun setListeners() {

        binding.itemAdd.setOnClickListener {

            val oldQty = binding.itemCount.text.trim().toString()

            totalQuantity = oldQty.toDouble() + ITEM_QUANTITY_INCREMENT_STEP

            totalAmount = menuItem.amount * totalQuantity

            binding.itemCount.text = totalQuantity.toString()

            val addString = "Add ₹${totalAmount}"
            binding.bottomSheetAddToCart.text = addString


        }

        binding.itemRemove.setOnClickListener {

            val oldQty = binding.itemCount.text.trim().toString()

            if(oldQty.toFloat() >= (ITEM_QUANTITY_INCREMENT_STEP * 2)) {

                totalQuantity = oldQty.toDouble() - ITEM_QUANTITY_INCREMENT_STEP

                totalAmount = menuItem.amount * totalQuantity

                binding.itemCount.text = totalQuantity.toString()

                val addString = "Add ₹${totalAmount}"
                binding.bottomSheetAddToCart.text = addString

            }

        }


        binding.bottomSheetAddToCart.setOnClickListener {

            val cartItem = CartItem(
                name = menuItem.name,
                category = menuItem.category,
                imageUrl = menuItem.imageUrl,
                quantity = menuItem.quantity,
                amount = menuItem.amount,
                description = menuItem.description,
                totalQuantity = totalQuantity,
                totalAmount = totalAmount
            )

            cartViewModel.setStateEvent(CartEvent.AddToCartEvent(cartItem))

//            close bottomsheet
            dismiss()
        }
    }

    private fun setInitialState() {
        binding.apply {
            bottomSheetAddToCart.text = ""
            itemCount.text = "1"
        }
    }

}