package com.eCommerce.shopify.ui.search.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.SearchFragmentBinding
import com.eCommerce.shopify.model.Product
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.product.view.OnCategoryProductClickListener
import com.eCommerce.shopify.ui.product.view.ProductAdapter
import com.eCommerce.shopify.ui.search.repo.SearchRepo
import com.eCommerce.shopify.ui.search.view_model.SearchViewModel
import com.eCommerce.shopify.ui.search.view_model.SearchViewModelFactory
import com.eCommerce.shopify.utils.AppConstants

class SearchFragment : Fragment(), OnCategoryProductClickListener {

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var productAdapter: ProductAdapter

    private val searchFragmentArgs by navArgs<SearchFragmentArgs>()

    private val navController by lazy{
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }

    private var viewModel: SearchViewModel? = null
    private lateinit var binding: SearchFragmentBinding
    private var searchingName: MutableLiveData<String> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        listenToBackBtn()
        gettingViewModelReady()
        initProductRecyclerView()
        listenToSearchText()
        listenToFavoriteBtn()
        observeOnSearchingDataToCallViewModelSearch()
    }


    private fun setupToolbar() {
        binding.appBarHome.toolbar.title = "Search"
    }

    private fun gettingViewModelReady(){
        val factory = searchFragmentArgs.allProduct?.toList()?.let {
            searchFragmentArgs.searchType?.let { it1 ->
                SearchViewModelFactory(it,
                    it1, SearchRepo.getInstance(APIClient.getInstance()))
            }
        }

        viewModel = factory?.let { ViewModelProvider(this, it)[SearchViewModel::class.java] }

        viewModel?.errorMsgResponse?.observe(viewLifecycleOwner, {
            AppConstants.showAlert(
                requireContext(),
                R.string.error,
                it,
                R.drawable.ic_error
            )
        })
        viewModel?.showProgressBar?.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel?.resultProduct?.observe(viewLifecycleOwner, {
            productAdapter.setDataToAdapter(it)
        })
        viewModel?.noResult?.observe(viewLifecycleOwner,{
            if(it){
                binding.noMatchResult.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
            else{
                binding.noMatchResult.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        })
    }

    private fun listenToSearchText(){
        binding.appBarHome.txtInputEditTextSearch.addTextChangedListener{
            searchingName.postValue(binding.appBarHome.txtInputEditTextSearch.text.toString())
        }
    }
    private fun observeOnSearchingDataToCallViewModelSearch(){
        searchingName.observe(viewLifecycleOwner, {
            if(it.isNullOrEmpty()){
                Log.i("if null or empty", searchingName.value.toString())
                viewModel?.search("")
            }
            else{
                Log.i("if not null or empty", searchingName.value.toString())
                viewModel?.search(searchingName.value.toString())
            }

        })
    }

    private fun initProductRecyclerView(){
        productAdapter = ProductAdapter(requireContext(), emptyList(), this)
        gridLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        binding.recyclerView.apply {
            adapter = productAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun listenToBackBtn(){
        binding.appBarHome.backArrow.setOnClickListener{
           navController.popBackStack()
        }
    }

    private fun listenToFavoriteBtn(){
        binding.appBarHome.cardViewFavorite.setOnClickListener {
            navController.navigate(R.id.action_searchFragment_to_favoriteFragment)
        }
    }

    override fun onCategoryProductClick(product: Product) {
        navController.navigate(SearchFragmentDirections.actionSearchFragmentToProductDetailsFragment(product.id))
    }

}