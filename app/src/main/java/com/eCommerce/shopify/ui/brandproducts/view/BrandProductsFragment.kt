package com.eCommerce.shopify.ui.brandproducts.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.databinding.FragmentBrandProductsBinding
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.OnProductClickListener
import com.eCommerce.shopify.ui.brandproducts.repo.BrandProductsRepository
import com.eCommerce.shopify.ui.brandproducts.view.BrandProductsAdapter
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModel
import com.eCommerce.shopify.ui.brandproducts.viewmodel.BrandProductsViewModelFactory

class BrandProductsFragment : Fragment() ,OnProductClickListener{

    private lateinit var binding:FragmentBrandProductsBinding
    private lateinit var brandProductsAdapter: BrandProductsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var brandProductsViewModel: BrandProductsViewModel
    private lateinit var brandProductsViewModelFactory: BrandProductsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBrandProductsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*brandProductsViewModelFactory = BrandProductsViewModelFactory(
            BrandProductsRepository.getInstance(APIClient.getInstance())
        )
        brandProductsViewModel = ViewModelProvider(this,brandProductsViewModelFactory).get(BrandProductsViewModel::class.java)
        setupBrandProductsRecycler()

        brandProductsViewModel.getBrandProductsCollectionList(400220946667)
        brandProductsViewModel.brandProductsCollectionResponse.observe(viewLifecycleOwner){
            brandProductsAdapter.setBrandProductsList(it.products)
            brandProductsAdapter.notifyDataSetChanged()
        }*/

        /*val productsList = listOf(
            Product("shirt",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("shoes",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg"),
            Product("bag",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("dress",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg"),
            Product("bag",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("dress",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg")
        )*/
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