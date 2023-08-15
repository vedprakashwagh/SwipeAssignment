package com.vedprakashwagh.swipeassignment.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vedprakashwagh.swipeassignment.databinding.FragmentProductListBinding
import com.vedprakashwagh.swipeassignment.utils.ResourceState
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * The fragment which shows list of products. This is the first screen of the app.
 */
class ProductList : Fragment() {

    private var _binding: FragmentProductListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val productListViewmodel: ProductListViewmodel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProductList()
    }

    private fun initProductList() {
        productListViewmodel.onEvent(ProductEvents.FetchProducts)
        binding.rvProductList.layoutManager = GridLayoutManager(requireContext(), 2)
        val productListAdapter = ProductListRecyclerViewAdapter()
        binding.rvProductList.adapter = productListAdapter

        binding.refreshProductList.setOnRefreshListener {
            productListViewmodel.onEvent(ProductEvents.FetchProducts)
        }

        productListViewmodel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResourceState.Failed -> {
                    hideProgressView()
                    showErrorContainer(state.error?.message)
                }

                is ResourceState.Loading -> {
                    showProgressView()
                    hideErrorContainer()
                }

                is ResourceState.Success -> {
                    hideProgressView()
                    hideErrorContainer()
                    productListAdapter.setProducts(state.data)
                }
            }
        }
    }

    private fun showErrorContainer(errorMessage: String?) {
        binding.tvProductListError.text = errorMessage ?: "Some error occurred!"
        binding.containerProductListError.visibility = View.VISIBLE
        binding.refetchProductList.setOnClickListener {
            productListViewmodel.onEvent(ProductEvents.FetchProducts)
        }
    }

    private fun hideErrorContainer() {
        binding.tvProductListError.text = ""
        binding.containerProductListError.visibility = View.GONE
        binding.refetchProductList.setOnClickListener(null)
    }

    private fun hideProgressView() {
        binding.cpiProductList.visibility = View.GONE
        binding.refreshProductList.isRefreshing = false
    }

    private fun showProgressView() {
        binding.cpiProductList.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        productListViewmodel.state.removeObservers(viewLifecycleOwner)
    }

}