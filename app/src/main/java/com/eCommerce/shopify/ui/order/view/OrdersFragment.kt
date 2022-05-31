package com.eCommerce.shopify.ui.order.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.OrdersFragmentBinding
import com.eCommerce.shopify.model.OrderModel
import com.eCommerce.shopify.ui.order.viewModel.OrdersViewModel

class OrdersFragment : Fragment() {
    private lateinit var bindingFragment: OrdersFragmentBinding
    private lateinit var viewModel: OrdersViewModel
    private lateinit var myView:View

    private lateinit var ordersAdapter:OrdersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment = OrdersFragmentBinding.inflate(inflater,container,false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myView = view
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(OrdersViewModel::class.java)
        getString(R.string.orders).also { bindingFragment.appBar.toolbar.title = it }
        val orders: List<OrderModel> = listOf(OrderModel("10/12/2020","1290$"),OrderModel("24/11/2302","2020$"),OrderModel("10/12/2020","1290$"),OrderModel("10/12/2020","1290$"))
        ordersAdapter = OrdersAdapter(myView.context,orders)
        var linearManager = LinearLayoutManager(activity)
        bindingFragment.ordersRecyclerView.apply {
            setHasFixedSize(true)
            linearManager.orientation = RecyclerView.VERTICAL
            layoutManager = linearManager
            adapter = ordersAdapter
        }
    }

}