package com.eCommerce.shopify.ui.checkout.view

import android.annotation.SuppressLint
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
import com.eCommerce.shopify.model.orderDetails.LineItem
import com.eCommerce.shopify.model.orderDetails.Order
import com.eCommerce.shopify.network.APIClient
import com.eCommerce.shopify.ui.AddressAndCheckoutAdapter.AddressesAdapter
import com.eCommerce.shopify.ui.AddressAndCheckoutAdapter.OnRowClicked
import com.eCommerce.shopify.ui.checkout.repo.CheckoutRepo
import com.eCommerce.shopify.ui.checkout.repo.LineItemAdapter
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModel
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModelFactory
import com.eCommerce.shopify.ui.shopping_cart.view.ShoppingCartFragmentDirections
import com.eCommerce.shopify.utils.AppConstants
import java.text.SimpleDateFormat
import java.util.*

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
    private lateinit var discountCodes: DiscountCodes
    private val checkoutFragmentArgs by navArgs<CheckoutFragmentArgs>()
    private lateinit var currency:String
    private lateinit var totalPrice:String
    private lateinit var paymentMethod:String
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
        setupPaymentMethodDialog()
    }
    @SuppressLint("SetTextI18n")
    fun init(){
        setUpViewModel()
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
        addressesAdapter = AddressesAdapter(myView.context, emptyList(),this)
        val layoutManag = LinearLayoutManager(activity)
        dialogRecyclerView.apply {
            setHasFixedSize(true)
            layoutManag.orientation = RecyclerView.VERTICAL
            layoutManager = layoutManag
            adapter = addressesAdapter
        }
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
                    if (checkCouponCodeValidation(couponCode)){
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
        //******************************** لحد ما ثريا تخلص ال address ******************************
        val address = Addresse(address1 = "Helwan,Arab elwalda")
        val toDayDate = getTodayDate()
        val discountCode = listOf(DiscountCode(code = couponCode))
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
        viewModel.deleteCheckOutList(checkoutFragmentArgs.productsCheckout)
        navController.navigate(R.id.action_checkoutFragment_to_successFragment2)
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
        binding.fullAddress.text = address.address1.toString()
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

}