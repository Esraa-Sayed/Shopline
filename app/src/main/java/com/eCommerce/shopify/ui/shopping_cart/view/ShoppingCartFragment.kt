package com.eCommerce.shopify.ui.shopping_cart.view

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
import android.view.Window
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.eCommerce.shopify.R
import com.eCommerce.shopify.database.shoppingcart.ShoppingCartLocalSource
import com.eCommerce.shopify.databinding.ConfirmDialogBinding
import com.eCommerce.shopify.databinding.ShoppingCartFragmentBinding
import com.eCommerce.shopify.model.ProductDetail
import com.eCommerce.shopify.ui.shopping_cart.repo.ShoppingCartRepo
import com.eCommerce.shopify.ui.shopping_cart.view_model.ShoppingCartViewModel
import com.eCommerce.shopify.ui.shopping_cart.view_model.ShoppingCartViewModelFactory

class ShoppingCartFragment : Fragment(), Listener {
    private var _binding: ShoppingCartFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ShoppingCartViewModel

    private val shoppingCartFragmentArgs by navArgs<ShoppingCartFragmentArgs>()

    private val navController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
    }
    lateinit var adapter: ShoppingCartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ShoppingCartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "Shopping Cart".also { binding.appBar.toolbar.title = it }
        binding.appBar.backArrow.setOnClickListener {
            navController.popBackStack()
        }
        if (shoppingCartFragmentArgs.productDetail.isEmpty()){
            binding.checkoutBtn.visibility = View.GONE
        }
        var productDetails = mutableListOf<ProductDetail>()
        if(shoppingCartFragmentArgs.productDetail.toList().isNotEmpty()){
            productDetails = shoppingCartFragmentArgs.productDetail.toList().toMutableList()
        }

        val factory =
            ShoppingCartViewModelFactory(requireContext(), ShoppingCartRepo.getInstance(ShoppingCartLocalSource.getInstance(
                requireContext())), productDetails)

        viewModel = ViewModelProvider(this, factory)[ShoppingCartViewModel::class.java]
        if (checkIfUserLoginAndInitInfo()) {
            initShoppingCartRecyclerView()
            addCheckoutListener()
            setTotalPrice()
        }
        listenToLoginAndRegisterBtn()
    }

    private fun listenToLoginAndRegisterBtn() {
        listenToLoginBtn()
        listenToRegisterBtn()
    }

    private fun listenToLoginBtn() {
        binding.loginBtn.setOnClickListener {
            navController.navigate(R.id.action_shoppingCartFragment_to_loginFragment)
        }
    }

    private fun listenToRegisterBtn() {
        binding.registerBtn.setOnClickListener {
            navController.navigate(R.id.action_shoppingCartFragment_to_registerFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalPrice() {
        viewModel.totalPrice.observe(viewLifecycleOwner, {
            binding.scTotalPrice.text = viewModel.getPriceWithCurrencyAsString(it)
            if(viewModel.products.size == 0){
                binding.checkoutBtn.visibility = View.GONE
            }
        })
    }

    private fun checkIfUserLoginAndInitInfo(): Boolean {
        val isLogin: Boolean = viewModel.getIsLogin()
        if (isLogin) {
            binding.scNoLogin.visibility = View.GONE
            binding.scLogin.visibility = View.VISIBLE
            binding.shoppingCartPage.setBackgroundResource(R.color.titan_white)

        } else {
            binding.scNoLogin.visibility = View.VISIBLE
            binding.scLogin.visibility = View.GONE
            binding.shoppingCartPage.setBackgroundResource(R.color.white)
        }
        return isLogin
    }

    private fun addCheckoutListener() {
        binding.checkoutBtn.setOnClickListener {
            navController.navigate( ShoppingCartFragmentDirections.actionShoppingCartFragmentToCheckoutFragment(
                productsCheckout = viewModel.products.toTypedArray(), viewModel.totalPrice.value?.toFloat()?: 0F
            ))
            }
        }

    private fun initShoppingCartRecyclerView() {
        if(shoppingCartFragmentArgs.productDetail.isEmpty()){
            binding.scLogin.visibility = View.GONE
            binding.scNoCart.visibility = View.VISIBLE
            binding.shoppingCartPage.setBackgroundResource(R.color.white)
        }
        adapter = ShoppingCartAdapter(this, shoppingCartFragmentArgs.productDetail.toList(), viewModel.getCurrency())
        _binding?.scRecyclerView?.layoutManager = LinearLayoutManager(this.requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        _binding?.scRecyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun update(productDetail: ProductDetail) {
        viewModel.updateProductInShoppingCart(productDetail)
    }

    override fun checkToDelete(productDetail: ProductDetail) {
        confirmDialog().observe(viewLifecycleOwner, {
            if(it == true){
                viewModel.deleteProductFromShoppingCart(productDetail)
                viewModel.deleteProduct(productDetail)
                adapter.setData(viewModel.products)
                if (shoppingCartFragmentArgs.productDetail.isEmpty()){
                    binding.checkoutBtn.visibility = View.GONE
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun decrementTotalPrice(product: ProductDetail) {
        if(product.amount >= 1){
            viewModel.updateTotalPrice(amountOfPrice = product.variants[0].price.toDouble(), "-")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun incrementTotalPrice(product: ProductDetail) {
        viewModel.updateTotalPrice(amountOfPrice = product.variants[0].price.toDouble(), "+")
    }

    private fun confirmDialog(): LiveData<Boolean> {
        val isOk: MutableLiveData<Boolean> = MutableLiveData()
        val inflater = requireActivity().layoutInflater
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bind : ConfirmDialogBinding = ConfirmDialogBinding .inflate(inflater)
        dialog.setContentView(bind.root)
        dialog.setTitle("Confirmation")
        bind.confirmText.text = getString(R.string.sure_for_delete)
        bind.okBtn.setOnClickListener {
            with(isOk) { postValue(true) }
            Log.i("Confirm","in ok button")
            dialog.dismiss()
        }
        bind.cancelBtn.setOnClickListener{
            with(isOk) { postValue(false) }
            Log.i("Confirm","in cancel button")
            dialog.dismiss()
        }

        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        return isOk
    }
}