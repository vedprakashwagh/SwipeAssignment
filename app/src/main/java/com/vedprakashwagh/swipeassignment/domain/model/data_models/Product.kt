package com.vedprakashwagh.swipeassignment.domain.model.data_models

import androidx.recyclerview.widget.DiffUtil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("image")
    var image: String? = null,
    @SerialName("price")
    var price: Float? = null,
    @SerialName("product_name")
    var productName: String? = null,
    @SerialName("product_type")
    var productType: String? = null,
    @SerialName("tax")
    var tax: Float? = null
) {

    /**
     * Doesn't cover all the cases like Product type being different than Spinner items and other checks
     */
    fun isValid(): Boolean {
        return productName?.isNotBlank() == true && productType?.isNotBlank() == true
    }

    class DiffUtilsCallback(
        private val oldList: List<Product>,
        private val newList: List<Product>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].productName == newList[newItemPosition].productName)
                    && (oldList[oldItemPosition].productType == newList[newItemPosition].productType)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList[oldItemPosition].productName == newList[newItemPosition].productName)
                    && (oldList[oldItemPosition].productType == newList[newItemPosition].productType)
                    && oldList[oldItemPosition].price == newList[newItemPosition].price
        }

    }

}
