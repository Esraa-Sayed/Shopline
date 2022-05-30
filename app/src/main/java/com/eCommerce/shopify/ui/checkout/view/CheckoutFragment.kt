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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.CheckoutFragmentBinding
import com.eCommerce.shopify.databinding.ChooseAddressDialogCheckoutScreenBinding
import com.eCommerce.shopify.ui.checkout.viewModel.CheckoutViewModel

class CheckoutFragment : Fragment() {


    private lateinit var viewModel: CheckoutViewModel
    private lateinit var binding: CheckoutFragmentBinding
    private lateinit var myView: View
    private lateinit var navController: NavController
    private lateinit var couponCode:String
    private lateinit var dialog: Dialog
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

    }
    fun init(){
        this.navController = findNavController()
        dialog = Dialog(myView.context)
        dialog.setContentView(R.layout.choose_address_dialog_checkout_screen)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRecyclerView = dialog.findViewById(R.id.checkoutScreenChooseAddressRecyclerView)
        viewModel = ViewModelProvider(this).get(CheckoutViewModel::class.java)
        binding.appBar.toolbar.title = "Checkout"
        buttonsListener()

    }

    private fun buttonsListener() {
        binding.backButton.setOnClickListener {
            navController.navigateUp()
        }
        binding.goToChooseAddress.setOnClickListener {
            Log.e("TAG", "buttonsListener:goToChooseAddress " )
            dialog.show()
        }
        binding.goToPaymentMethod.setOnClickListener {
            Log.e("TAG", "buttonsListener: goToPaymentMethod" )
        }
        binding.checkValidate.setOnClickListener {
                if(binding.couponCode.text.isEmpty()){
                    getString(R.string.cant_be_empty).also { binding.couponCode.error = it }
                }else{
                    couponCode = binding.couponCode.text.toString()
                    Log.e("TAG", "buttonsListener: checkValidate : $couponCode" )
                }
        }
        binding.placeOrder.setOnClickListener {
            Log.e("TAG", "buttonsListener: placeOrder" )
            navController.navigate(R.id.action_checkoutFragment_to_successFragment2)
        }

    }
}