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
import com.eCommerce.shopify.model.payPalResponse.payPalResponse
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.AddressAndCheckoutAdapter.AddressesAdapter
import com.eCommerce.shopify.ui.checkout.repo.CheckoutRepo
import com.eCommerce.shopify.ui.checkout.repo.LineItemAdapter
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModel
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModelFactory
import com.eCommerce.shopify.utils.AppConstants
import com.google.gson.Gson
import com.paypal.android.sdk.payments.*
import org.json.JSONException
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class CheckoutFragment : Fragment(){


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
        discountCodes = DiscountCodes(listOf(com.eCommerce.shopify.model.discount.DiscountCode("","",1234,1233,"",0)))
       //just to try it
        getDiscountCodes()
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

    private fun getDiscountCodes() {
        var discountCode = com.eCommerce.shopify.model.discount.DiscountCode("ESA_10","24/6/2022",1027349774508,1027349774508,"24/6/2022",0)
        var discountCodesTest: MutableList<com.eCommerce.shopify.model.discount.DiscountCode> = emptyList<com.eCommerce.shopify.model.discount.DiscountCode>().toMutableList()

        discountCodesTest.add(discountCode)
        discountCode = com.eCommerce.shopify.model.discount.DiscountCode("TRADE_10","24/6/2022",1027349774509,1027349774508,"24/6/2022",0)
        discountCodesTest.add(discountCode)
        discountCode = com.eCommerce.shopify.model.discount.DiscountCode("SALE_10","24/6/2022",1027349774500,1027349774508,"24/6/2022",0)
        discountCodesTest.add(discountCode)
        discountCode = com.eCommerce.shopify.model.discount.DiscountCode("SHOPIFY_10","24/6/2022",1027349774520,1027349774508,"24/6/2022",0)
        discountCodesTest.add(discountCode)
        discountCodes = DiscountCodes(discountCodesTest)
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
        }
    }



    private fun setupAddressDialog() {
        dialogAddress.setContentView(R.layout.choose_address_dialog_checkout_screen)
        dialogAddress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRecyclerView = dialogAddress.findViewById(R.id.checkoutScreenChooseAddressRecyclerView)
        getUserAddresses()

        addressesAdapter = AddressesAdapter(myView.context, emptyList()){
            dialogAddress.dismiss()
            binding.countryName.text = it.country
            binding.city.text = it.city
            this.address = it
        }
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
            if (!it.addresses.isNullOrEmpty()) {
                addressesAdapter.updateData(it.addresses)
                binding.countryName.text = it.addresses[0].country
                binding.city.text = it.addresses[0].city
                address = it.addresses[0]
            }
            else{
                binding.countryName.text = getString(R.string.NoCountryFound)
                binding.city.text = getString(R.string.NoCityFound)
                var addresse = Addresse(getString(R.string.NoAddressFound),getString(R.string.NoAddressFound),getString(R.string.NoCityFound), country = getString(R.string.NoCountryFound))
                address = addresse
                addressesAdapter.updateData(listOf(addresse))
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
        //viewModel.getDiscountCodes()

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
                        binding.discountValueFromApi.text = "10 $currency"
                        binding.couponCodeTextAfterValidation.visibility = View.VISIBLE
                        binding.couponCodeTextAfterValidation.text = couponCode
                        totalPrice = "${checkoutFragmentArgs.totalPrice.minus(10)} $currency"
                        binding.totalAmountTextView.text = totalPrice
                    }else{
                        showAlert(getString(R.string.NotAValidCode))
                        binding.couponCode.setText("")
                    }
                }
        }
        binding.placeOrder.setOnClickListener {
            if(paymentMethod == getString(R.string.paypal))
               payPalPaymentMethod()
            placeOrder()
        }

    }

    private fun placeOrder() {
        if(getString(R.string.NoAddressFound) == address.address1){
            AppConstants.showAlert(myView.context,
                R.string.error,
                "There's no address found",
                R.drawable.ic_error)
        }
        else {
            val lineItems =
                LineItemAdapter.convertProductDetailIntoLineItem(checkoutFragmentArgs.productsCheckout)
            val toDayDate = getTodayDate()
            var amount:String? = null
            if (couponCode.isNullOrEmpty()){
                amount = "0"
            }
            else{
                amount = "10"
            }
            val discountCode = listOf(DiscountCode(amount = amount, code = couponCode ?: "Not found"))
            if (currency == "$")
                currency = "USD"
            val order = Order(
                line_items = lineItems, shipping_address = address,
                billing_address = address, created_at = toDayDate,
                processed_at = toDayDate, currency = currency,
                current_total_price = totalPrice,total_price = (totalPrice.split(" ")[0]).toFloat(), user_id = viewModel.getUserId(myView.context),
                discount_codes = discountCode, payment_gateway_names = listOf(paymentMethod),
                presentment_currency = currency
            )
            viewModel.postOrderWithUserIdAndEmail(order, myView.context)
        }
    }

    private fun checkCouponCodeValidation(couponCode: String): Boolean {

        for (item in   discountCodes.discount_codes){
            if (item.code == couponCode)
                return true
        }
        return false
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
        var payment = PayPalPayment(BigDecimal(totalPrice.split(" ")[0]),"USD","Shopify",PayPalPayment.PAYMENT_INTENT_SALE)
        val intent = Intent(activity,PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
        requestPaymentMethod.launch(intent)
    }
    private val requestPaymentMethod =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { data ->
            if (data.resultCode == Activity.RESULT_OK) {

                val auth = data?.data?.getParcelableExtra<PayPalAuthorization>(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION)
                val confirm = data?.data?.getParcelableExtra<PaymentConfirmation>(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                if (confirm != null) {
                    try {
                        Log.e("TAG", ": auth ${auth}" )
                        val resultConfirm = confirm.toJSONObject().toString(4)
                        val gson = Gson()
                        val resultConfirmData = gson.fromJson(resultConfirm, payPalResponse::class.java)
                        Log.e("TAG", "data result ${resultConfirmData.response.id}")

                    }catch (e: JSONException) {
                        Log.e("TAG", "an extremely unlikely failure occurred: ", e)
                        Toast.makeText(context,
                            "Payment failed please try Again!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }else if (data.resultCode == Activity.RESULT_CANCELED) {
                Log.i("TAG", "The user canceled.")
                Toast.makeText(context, "Payment Canceled!", Toast.LENGTH_SHORT).show()

            } else if (data.resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                    "TAG",
                    "An invalid Payment or PayPalConfiguration was submitted. Please see the docs."
                )
                Toast.makeText(context, "Invalid Payment Data!", Toast.LENGTH_SHORT).show()
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