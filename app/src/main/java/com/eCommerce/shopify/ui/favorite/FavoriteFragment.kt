package com.eCommerce.shopify.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.eCommerce.shopify.databinding.FragmentFavoriteBinding
import com.eCommerce.shopify.ui.OnProductClickListener
import com.eCommerce.shopify.ui.favorite.model.Product

class FavoriteFragment : Fragment() ,OnProductClickListener{

    private lateinit var binding:FragmentFavoriteBinding
    private lateinit var favAdapter: FavoriteAdapter
    private lateinit var gridManager:GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFavRecycler()

        val favList = listOf(
            Product("shirt",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("shoes",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg"),
            Product("bag",35.00,2.5,"https://image.shutterstock.com/image-photo/beautiful-brown-leather-female-bag-260nw-1079711900.jpg"),
            Product("dress",35.00,2.5,"https://image.shutterstock.com/image-vector/black-dress-icon-vector-260nw-224236432.jpg")
        )

        favAdapter.setFavProductList(favList)
        favAdapter.notifyDataSetChanged()
    }

    fun setupFavRecycler(){
        favAdapter = FavoriteAdapter(requireContext(), emptyList(),this)
        gridManager = GridLayoutManager(requireContext(),2)
        binding.favRecycler.adapter = favAdapter
        binding.favRecycler.layoutManager = gridManager
    }

    override fun onProductItemClick() {

    }

    override fun onFavBtnClick() {

    }
}