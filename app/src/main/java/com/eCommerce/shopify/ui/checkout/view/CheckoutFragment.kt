package com.eCommerce.shopify.ui.checkout.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.databinding.CheckoutFragmentBinding
import com.eCommerce.shopify.model.Addresse
import com.eCommerce.shopify.model.discount.DiscountCodes
import com.eCommerce.shopify.model.orderDetails.DiscountCode
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.AddressAndCheckoutAdapter.AddressesAdapter
import com.eCommerce.shopify.ui.AddressAndCheckoutAdapter.OnRowClicked
import com.eCommerce.shopify.ui.checkout.repo.CheckoutRepo
import com.eCommerce.shopify.ui.checkout.repo.LineItemAdapter
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModel
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModelFactory
import com.eCommerce.shopify.utils.AppConstants
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class CheckoutFragment : Fragment(), OnRowClicked {


    private lateinit var viewModel: CheckoutViewModel
    private lateinit var  checkoutViewModelFactory: CheckoutViewModelFactory

    private lateinit var binding: CheckoutFragmentBinding
    private lateinit var myView: View
    private lateinit var navController: NavController
    private var couponCode:String? = null
    private lateinit var dialogAddress: Dialog
    private lateinit var dialogPayment: Dialog
    private lateinit var addressesAdapter: AddressesAdapter
    private lateinit var dialogRecyclerView: RecyclerView
    private lateinit var discountCodes: DiscountCodes
    private val checkoutFragmentArgs by navArgs<CheckoutFragmentArgs>()
    private lateinit var currency:String
    private lateinit var totalPrice:String
    private lateinit var paymentMethod:String
    private lateinit var address:Addresse
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CheckoutFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated:productsCheckout.size ${checkoutFragmentArgs.productsCheckout.size}" )
        Log.e("TAG", "onViewCreated:checkoutFragmentArgs.totalPrice ${checkoutFragmentArgs.totalPrice}" )
        myView = view

        init()
        buttonsListener()
        setupAddressDialog()
        getUserAddresses()
        setupPaymentMethodDialog()
    }
    @SuppressLint("SetTextI18n")
    fun init(){
        setUpViewModel()
        setUpPayPal()
        address = Addresse(address1 = "Helwan,Arab elwalda")
        paymentMethod = getString(R.string.Cash_on_delivery)
        currency = viewModel.getCurrency(myView.context)
        binding.discountValueFromApi.text = "0.0 $currency"
        totalPrice = "${checkoutFragmentArgs.totalPrice} $currency"
        binding.totalAmountTextView.text = totalPrice
        binding.subTitle.text = "${checkoutFragmentArgs.totalPrice} $currency"
        this.navController = findNavController()
        dialogAddress = Dialog(myView.context)
        dialogPayment = Dialog(myView.context)
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        binding.appBar.toolbar.title = "Checkout"


    }



    private fun setupPaymentMethodDialog() {
        dialogPayment.setContentView(R.layout.choose_payment_method_dialog)
        dialogPayment.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogPayment.setCancelable(true)
        val cashOnDelivery:RadioButton = dialogPayment.findViewById(R.id.cash_on_delivery_cod)
        val paypal:RadioButton = dialogPayment.findViewById(R.id.paypal)
        cashOnDelivery.setOnClickListener {
            cashOnDelivery.isChecked = true
            paypal.isChecked = false
            updateCheckoutPaymentMethod(true)
            binding.paymentMethodIcon.setImageResource(R.drawable.ic_mastercard)
            dialogPayment.dismiss()
        }
        paypal.setOnClickListener {
            cashOnDelivery.isChecked = false
            paypal.isChecked = true
            binding.paymentMethodIcon.setImageResource(R.drawable.paypal)
            updateCheckoutPaymentMethod(false)
            dialogPayment.dismiss()
        }
    }

    private fun updateCheckoutPaymentMethod(cashOnDelivery:Boolean) {
        if (cashOnDelivery){
            binding.paymentMethodText.text = getString(R.string.Cash_on_delivery)
            paymentMethod = getString(R.string.Cash_on_delivery)
        }else{
            binding.paymentMethodText.text = getString(R.string.paypal)
            paymentMethod = getString(R.string.paypal)
            payPalPaymentMethod()
        }
    }



    private fun setupAddressDialog() {
        dialogAddress.setContentView(R.layout.choose_address_dialog_checkout_screen)
        dialogAddress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRecyclerView = dialogAddress.findViewById(R.id.checkoutScreenChooseAddressRecyclerView)
        getUserAddresses()
        addressesAdapter = AddressesAdapter(myView.context, emptyList(),this)
        val layoutManag = LinearLayoutManager(activity)
        dialogRecyclerView.apply {
            setHasFixedSize(true)
            layoutManag.orientation = RecyclerView.VERTICAL
            layoutManager = layoutManag
            adapter = addressesAdapter
        }
    }

    private fun getUserAddresses() {
        viewModel.getUserAddresses(myView.context)
        viewModel.userAddresses.observe(viewLifecycleOwner,{
            if (it != null) {
               addressesAdapter.updateData(it.addresses)
                binding.countryName.text = it.addresses[0].country
                binding.city.text = it.addresses[0].city
                address = it.addresses[0]
            }
        })
    }


    private fun setUpViewModel() {
        checkoutViewModelFactory = CheckoutViewModelFactory(
            CheckoutRepo.getInstance(
                APIClient.getInstance(), ShoppingCartLocalSource(myView.context)
            )
        )
        viewModel = ViewModelProvider(this, checkoutViewModelFactory)[CheckoutViewModel::class.java]
        viewModel.getDiscountCodes()

        viewModel.postOrderResponse.observe(viewLifecycleOwner, {
            Log.e("postOrder", "init POST ORDER I'm here************ : ${it.order.created_at}" )
            viewModel.deleteCheckOutList(checkoutFragmentArgs.productsCheckout)
            navController.navigate(R.id.action_checkoutFragment_to_successFragment2)
        })
        viewModel.errorMsgResponse.observe(viewLifecycleOwner, {
            Log.e("TAG", "initldsjflsk: $it" )
        })
        viewModel.getDiscountCodesResponse.observe(viewLifecycleOwner, {
            discountCodes = it
            Log.e("TAG", "init: $discountCodes" )
        })
    }

    @SuppressLint("SetTextI18n")
    private fun buttonsListener() {
        binding.appBar.backArrow.setOnClickListener {
            var action =  CheckoutFragmentDirections.actionCheckoutFragmentToShoppingCartFragment(checkoutFragmentArgs.productsCheckout)
            navController.navigate(action)
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
                    if (checkCouponCodeValidation(couponCode!!)){
                        showAlert(getString(R.string.The_code_has_been_added_successfully))
                        binding.couponCode.visibility = View.GONE
                        binding.checkValidate.isEnabled = false
                        binding.discountValueFromApi.text = "50 $currency"
                        binding.couponCodeTextAfterValidation.visibility = View.VISIBLE
                        binding.couponCodeTextAfterValidation.text = couponCode
                        totalPrice = "${checkoutFragmentArgs.totalPrice.minus(50)} $currency"
                        binding.totalAmountTextView.text = totalPrice
                    }else{
                        showAlert(getString(R.string.NotAValidCode))
                        binding.couponCode.setText("")
                    }
                }
        }
        binding.placeOrder.setOnClickListener {
            placeOrder()
        }

    }

    private fun placeOrder() {
        val lineItems = LineItemAdapter.convertProductDetailIntoLineItem(checkoutFragmentArgs.productsCheckout)
        val toDayDate = getTodayDate()
        val discountCode = listOf(DiscountCode(amount = "50", code = couponCode ?: "Not found"))
        if (currency == "$")
            currency = "USD"
        val order = Order(line_items = lineItems, shipping_address = address,
                            billing_address = address, created_at = toDayDate,
                            processed_at = toDayDate, currency = currency,
                            current_total_price = totalPrice,user_id = viewModel.getUserId(myView.context),
                            discount_codes = discountCode , payment_gateway_names = listOf(paymentMethod),
                            presentment_currency = currency
                        )
        viewModel.postOrderWithUserIdAndEmail(order,myView.context)
    }

    private fun checkCouponCodeValidation(couponCode: String): Boolean {
        for (item in   discountCodes.discount_codes){
            if (item.code == couponCode)
                return true
        }
        return false
    }

    override fun onRowClickedListenerAddress(address: Addresse) {
        dialogAddress.dismiss()
        binding.countryName.text = address.country
        binding.city.text = address.city
        this.address = address
    }
    private fun showAlert(message:String){
        AppConstants.showAlert(
            myView.context,
            R.string.inforamtion,
            message,
            R.drawable.information
        )
    }
    private fun getTodayDate():String{
        val c: Date = Calendar.getInstance().getTime()

        val df = SimpleDateFormat("yyyy/MMM/dd", Locale.getDefault())
        return df.format(c)
    }
    //*************************************** payPalPaymentMethod **************************************************************
    private fun payPalPaymentMethod() {
        var payment = PayPalPayment(BigDecimal(100),"USD","Shopify",PayPalPayment.PAYMENT_INTENT_SALE)
        val intent = Intent(activity,PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
        requestPaymentMethod.launch(intent)
    }
    private val requestPaymentMethod =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                showAlert("Payment made successfully")
                Log.e("TAG", ": *************requestPaymentMethod****************************" )
            }
        }
    private val payPalConfiguration = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
        .clientId(AppConstants.PAY_PAL_KEY)
    private fun setUpPayPal() {
        var intent = Intent(activity,PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration)
        myView.context.startService(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        myView.context.stopService(Intent(activity,PayPalService::class.java))
    }

}