package com.eCommerce.shopify.ui.brandproducts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentBrandProductsBinding
import com.eCommerce.shopify.ui.OnProductClickListener
import com.eCommerce.shopify.ui.favorite.FavoriteAdapter
import com.eCommerce.shopify.ui.favorite.model.Product

class BrandProductsFragment : Fragment() ,OnProductClickListener{

    private lateinit var binding:FragmentBrandProductsBinding
    private lateinit var brandProductsAdapter: BrandProductsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private val args by navArgs<BrandProductsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBrandProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("HelloFromAhmed:", "Brand Id => ${args.brandTitle}")

        setupBrandProductsRecycler()

        val productsList = listOf(
            Product("shirt",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("shoes",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg"),
            Product("bag",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("dress",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg"),
            Product("bag",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("dress",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg")

        )

        brandProductsAdapter.setBrandProductsList(productsList)
        brandProductsAdapter.notifyDataSetChanged()

    }

    fun setupBrandProductsRecycler(){
        brandProductsAdapter = BrandProductsAdapter(requireContext(), emptyList(),this)
        gridLayoutManager = GridLayoutManager(requireContext(),2)
        binding.brandProductsRecycler.adapter = brandProductsAdapter
        binding.brandProductsRecycler.layoutManager = gridLayoutManager
    }

    override fun onProductItemClick() {

    }

    override fun onFavBtnClick() {

    }
}