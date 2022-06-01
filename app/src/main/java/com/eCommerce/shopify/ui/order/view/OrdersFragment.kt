package com.eCommerce.shopify.ui.order.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.OrdersFragmentBinding
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.order.repo.OrdersRepo
import com.eCommerce.shopify.ui.order.viewModel.OrdersViewModel
import com.eCommerce.shopify.ui.order.viewModel.OrdersViewModelFactory
import com.eCommerce.shopify.utils.AppConstants

class OrdersFragment : Fragment(),OnOrderRowClicked {
    private lateinit var bindingFragment: OrdersFragmentBinding
    private lateinit var viewModel: OrdersViewModel
    private lateinit var ordersViewModelFactory: OrdersViewModelFactory
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
    fun noDataFound(){
        bindingFragment.noOrdersFound.visibility = View.VISIBLE
        bindingFragment.noOrdersFoundText.visibility = View.VISIBLE
        bindingFragment.ordersRecyclerView.visibility = View.GONE
    }
    fun dataFound(){
        bindingFragment.noOrdersFound.visibility = View.GONE
        bindingFragment.noOrdersFoundText.visibility = View.GONE
        bindingFragment.ordersRecyclerView.visibility = View.VISIBLE
    }
    private fun init() {
        ordersViewModelFactory = OrdersViewModelFactory(
            OrdersRepo.getInstance(
                APIClient.getInstance()
            )
        )
        viewModel = ViewModelProvider(this, ordersViewModelFactory)[OrdersViewModel::class.java]

        getString(R.string.orders).also { bindingFragment.appBar.toolbar.title = it }

        ordersAdapter = OrdersAdapter(myView.context, emptyList(),this)
        var linearManager = LinearLayoutManager(activity)
        bindingFragment.ordersRecyclerView.apply {
            setHasFixedSize(true)
            linearManager.orientation = RecyclerView.VERTICAL
            layoutManager = linearManager
            adapter = ordersAdapter
        }
        getUserOrders()
    }

    private fun getUserOrders() {
        viewModel.getUserOrders(myView.context)
        viewModel.UserOrders.observe(viewLifecycleOwner, Observer {
            if (it.orders.isNotEmpty()){
                dataFound()
                ordersAdapter.updateData(it.orders)
            }else{
                noDataFound()
            }
            Log.e("TAG", "getUserOrders: ${it.orders.size}" )
        })
        viewModel.errorMsgResponse.observe(viewLifecycleOwner, Observer {
            AppConstants.showAlert(
                myView.context,
                R.string.error,
                it,
                R.drawable.ic_error
            )
        })
    }
    override fun onRowClickedListener(order: Order) {
        val action = OrdersFragmentDirections.actionOrdersFragmentToOrdersDetailsFragment(order.id)
        findNavController().navigate(action)
    }
}