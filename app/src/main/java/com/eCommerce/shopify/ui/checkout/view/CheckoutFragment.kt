package com.eCommerce.shopify.ui.checkout.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.CheckoutFragmentBinding
import com.eCommerce.shopify.model.Addresse
import com.eCommerce.shopify.model.orderDetails.LineItem
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.AddressAndCheckoutAdapter.AddressesAdapter
import com.eCommerce.shopify.ui.AddressAndCheckoutAdapter.OnRowClicked
import com.eCommerce.shopify.ui.category.repo.CategoryRepo
import com.eCommerce.shopify.ui.category.viewmodel.CategoryViewModel
import com.eCommerce.shopify.ui.category.viewmodel.CategoryViewModelFactory
import com.eCommerce.shopify.ui.checkout.repo.CheckoutRepo
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModel
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModelFactory

class CheckoutFragment : Fragment(), OnRowClicked {


    private lateinit var viewModel: CheckoutViewModel
    private lateinit var  checkoutViewModelFactory: CheckoutViewModelFactory

    private lateinit var binding: CheckoutFragmentBinding
    private lateinit var myView: View
    private lateinit var navController: NavController
    private lateinit var couponCode:String
    private lateinit var dialogAddress: Dialog
    private lateinit var dialogPayment: Dialog
    private lateinit var addressesAdapter: AddressesAdapter
    private lateinit var dialogRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CheckoutFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myView = view
        init()
        buttonsListener()
        setupAddressDialog()
        setupPaymentMethodDialog()
    }

    private fun setupPaymentMethodDialog() {
        dialogPayment.setContentView(R.layout.choose_payment_method_dialog)
        dialogPayment.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogPayment.setCancelable(true)
        var cashOnDelivery:RadioButton = dialogPayment.findViewById(R.id.cash_on_delivery_cod)
        var paypal:RadioButton = dialogPayment.findViewById(R.id.paypal)
        cashOnDelivery.setOnClickListener {
            cashOnDelivery.isChecked = true
            paypal.isChecked = false
            updateCheckoutPaymentMethod(true)
            dialogPayment.dismiss()
        }
        paypal.setOnClickListener {
            cashOnDelivery.isChecked = false
            paypal.isChecked = true
            updateCheckoutPaymentMethod(false)
            dialogPayment.dismiss()
        }
    }

    private fun updateCheckoutPaymentMethod(cashOnDelivery:Boolean) {
        if (cashOnDelivery){
            binding.paymentMethodText.text = getString(R.string.Cash_on_delivery)
        }else{
            binding.paymentMethodText.text = getString(R.string.paypal)
        }
    }

    private fun setupAddressDialog() {
        dialogAddress.setContentView(R.layout.choose_address_dialog_checkout_screen)
        dialogAddress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRecyclerView = dialogAddress.findViewById(R.id.checkoutScreenChooseAddressRecyclerView)
        addressesAdapter = AddressesAdapter(myView.context, emptyList(),this)
        val layoutManag = LinearLayoutManager(activity)
        dialogRecyclerView.apply {
            setHasFixedSize(true)
            layoutManag.orientation = RecyclerView.VERTICAL
            layoutManager = layoutManag
            adapter = addressesAdapter
        }
    }

    fun init(){
        checkoutViewModelFactory = CheckoutViewModelFactory(
            CheckoutRepo.getInstance(
                APIClient.getInstance()
            )
        )
        viewModel = ViewModelProvider(this, checkoutViewModelFactory)[CheckoutViewModel::class.java]
       /* {"order":
            {"line_items":[{"variant_id":42851028271339,"quantity":1,"price": "90.00"}]*/
        var lineItems = listOf<LineItem>(LineItem(variant_id = 42851028271339, quantity = 1, price = "90.00"))
        val order = Order(line_items = lineItems)
        viewModel.postOrderWithUserIdAndEmail(order,myView.context)
        viewModel.postOrderResponse.observe(viewLifecycleOwner, Observer {
            Log.e("TAG", "init: ${it.checkout_id}" )
        })
        viewModel.errorMsgResponse.observe(viewLifecycleOwner, Observer {
            Log.e("TAG", "init: $it" )
        })
        this.navController = findNavController()
        dialogAddress = Dialog(myView.context)
        dialogPayment = Dialog(myView.context)
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        binding.appBar.toolbar.title = "Checkout"
    }

    private fun buttonsListener() {
        binding.backButton.setOnClickListener {
            navController.navigateUp()
        }
        binding.goToChooseAddress.setOnClickListener {
            dialogAddress.show()
        }
        binding.goToPaymentMethod.setOnClickListener {
            dialogPayment.show()
        }
        binding.checkValidate.setOnClickListener {
                if(binding.couponCode.text.isEmpty()){
                    getString(R.string.cant_be_empty).also { binding.couponCode.error = it }
                }else{
                    couponCode = binding.couponCode.text.toString()
                }
        }
        binding.placeOrder.setOnClickListener {
            navController.navigate(R.id.action_checkoutFragment_to_successFragment2)
        }

    }

    override fun onRowClickedListenerAddress(address: Addresse) {
        dialogAddress.dismiss()
        binding.countryName.text = address.country
        binding.fullAddress.text = address.address1.toString()
    }
}