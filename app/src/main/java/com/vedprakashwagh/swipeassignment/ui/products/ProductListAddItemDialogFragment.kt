package com.vedprakashwagh.swipeassignment.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.vedprakashwagh.swipeassignment.databinding.FragmentAddProductBinding
import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product
import com.vedprakashwagh.swipeassignment.utils.ResourceState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ProductListAddItemDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val productListViewmodel: ProductListViewmodel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productListViewmodel.stateAddProduct.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResourceState.Failed -> {
                    binding.cpiAddProduct.hide()
                    Snackbar.make(
                        view,
                        state.error?.message ?: "Task failed!",
                        Snackbar.LENGTH_LONG
                    ).setAction("Retry?") {
                        addProduct()
                    }.show()
                }

                is ResourceState.Loading -> {
                    binding.cpiAddProduct.show()
                }

                is ResourceState.Success -> {
                    binding.cpiAddProduct.hide()
                    dismiss()
                    productListViewmodel.onEvent(ProductEvents.FetchProducts)
                    Toast.makeText(
                        requireContext().applicationContext,
                        "Successfully added the item!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.buttonAddProduct.setOnClickListener {
            addProduct()
        }
    }

    fun addProduct() {
        productListViewmodel.onEvent(
            ProductEvents.AddProduct(
                Product(
                    image = null,
                    price = binding.productPrice.text?.toString()?.toFloatOrNull(),
                    productName = binding.productName.text?.toString(),
                    productType = binding.spinnerProductTypes?.selectedItem?.toString(),
                    tax = binding.productTaxRate.text?.toString()?.toFloatOrNull()
                )
            )
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        productListViewmodel.stateAddProduct.removeObservers(viewLifecycleOwner)
        productListViewmodel.reInitialiseAddProductState()
    }

}