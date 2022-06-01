package com.eCommerce.shopify.ui.search.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.FragmentHomeBinding
import com.eCommerce.shopify.databinding.ProfileFragmentBinding
import com.eCommerce.shopify.databinding.SearchFragmentBinding
import com.eCommerce.shopify.model.SmartCollection
import com.eCommerce.shopify.ui.MainFragmentDirections
import com.eCommerce.shopify.ui.favorite.model.Product
import com.eCommerce.shopify.ui.home.view.HomeBrandAdapter
import com.eCommerce.shopify.ui.home.view.OnBrandClickListener
import com.eCommerce.shopify.ui.search.view_model.SearchViewModel
import com.eCommerce.shopify.ui.search.view_model.SearchViewModelFactory

class SearchFragment : Fragment(), OnBrandClickListener {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var homeBrandAdapter: HomeBrandAdapter
    private val navController by lazy{
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    lateinit var fragmentName: String
    lateinit var brandList: List<SmartCollection>
    lateinit var productList: List<Product>
    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: SearchFragmentBinding
    private lateinit var searchingName: MutableLiveData<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, SearchViewModelFactory(allBrands = brandList, allProduct = productList)).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenToBackBtn()
        initBrandRecyclerView()
        initProductRecyclerView()
        listenToSearchText()
        observeOnSearchingDataToCallViewModelSearch()
    }

    private fun listenToSearchText(){
        binding.txtInputEditTextSearch.addTextChangedListener{
            searchingName.value = binding.txtInputEditTextSearch.text.toString()
        }
    }
    private fun observeOnSearchingDataToCallViewModelSearch(){
        if(searchingName != null){
            if(fragmentName == "BRAND_FRAGMENT"){
                viewModel.resultSmartCollection.observe(viewLifecycleOwner, {
                    homeBrandAdapter.setDataToAdapter(it)
                })
            }
            else{
                viewModel.resultProduct.observe(viewLifecycleOwner, {

                })
            }
        }

        else{

        }
    }

    private fun initBrandRecyclerView(){
        homeBrandAdapter = HomeBrandAdapter(requireContext(), emptyList(), this)
        gridLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false)
        binding.recyclerView.apply {
            adapter = homeBrandAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun initProductRecyclerView(){

    }

    private fun listenToBackBtn(){
        binding.backBtn.setOnClickListener{
           navController.navigate(R.id.navigation_home)
        }
    }

    override fun onBrandClick(smartCollection: SmartCollection) {
        val action = SearchFragmentDirections.actionSearchFragmentToBrandProductsFragment(smartCollection.title)
        navController.navigate(action)
    }

}