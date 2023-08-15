package com.vedprakashwagh.swipeassignment.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vedprakashwagh.swipeassignment.databinding.ListItemProductBinding
import com.vedprakashwagh.swipeassignment.domain.model.data_models.Product
import com.vedprakashwagh.swipeassignment.utils.Constants

class ProductListRecyclerViewAdapter :
    RecyclerView.Adapter<ProductListRecyclerViewAdapter.ProductsViewholder>() {

    private var products: List<Product> = emptyList()


    fun setProducts(products: List<Product>) {
        val diffCallback = Product.DiffUtilsCallback(this.products, products)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.products = products
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewholder {
        return ProductsViewholder(
            ListItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductsViewholder, position: Int) {
        holder.bind(products[position])
    }

    inner class ProductsViewholder(private val binding: ListItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvProductName.text = product.productName
            Glide.with(binding.root.context)
                .load(if (product.image?.isBlank() == true) Constants.Endpoints.ENDPOINT_RANDOM_IMAGE + product.productName + product.productType + product.image + product.price + product.tax else product.image)
                .into(binding.ivProduct)

            binding.tvProductType.text = product.productType
            binding.tvProductPrice.text = "₹" + (product.price?.toInt() ?: 0)?.toString()
            binding.tvProductTax.text = "Taxable at ${product.tax}%"
        }

    }


}