package com.eCommerce.shopify.ui.orderDetails.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.OrdersDetailsFragmentBinding
import com.eCommerce.shopify.ui.orderDetails.viewModel.OrdersDetailsViewModel

class OrdersDetailsFragment : Fragment() {

    private lateinit var viewModel: OrdersDetailsViewModel
    private lateinit var binding:OrdersDetailsFragmentBinding
    private lateinit var myView: View
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter
    private val ordersDetailsFragmentArgs by navArgs<OrdersDetailsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrdersDetailsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myView = view
        init()

    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(OrdersDetailsViewModel::class.java)
        binding.orderCreatedDate.text = ordersDetailsFragmentArgs.createdAt.split("T")[0]
        binding.userShippingToName.text = ordersDetailsFragmentArgs.shippingTo
        orderDetailsAdapter = OrderDetailsAdapter(myView.context, ordersDetailsFragmentArgs.items)
        var layoutMan = LinearLayoutManager(activity)
        getString(R.string.OrderDetails).also { binding.appBar.toolbar.title = it }
        binding.orderDetailsRecyclerView.apply {
            setHasFixedSize(true)
            layoutMan.orientation = RecyclerView.VERTICAL
            layoutManager = layoutMan
            adapter = orderDetailsAdapter
        }
        binding.appBar.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}