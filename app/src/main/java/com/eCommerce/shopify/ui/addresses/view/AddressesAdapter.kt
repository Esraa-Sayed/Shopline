package com.eCommerce.shopify.ui.addresses.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.AddressRowBinding
import com.eCommerce.shopify.model.address

class AddressesAdapter(val context: Context, var addresses:List<address>):
    RecyclerView.Adapter<AddressesAdapter.AddressViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
       val binding = AddressRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return  AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
       val address = addresses[position]
        holder.binding.countryName.text = address.country
        holder.binding.fullAddress.text = address.fullAddress
    }

    override fun getItemCount(): Int {
        return addresses.size
    }
    fun updateData(addresses:List<address>){
        this.addresses = addresses
    }
    class AddressViewHolder(val binding: AddressRowBinding):RecyclerView.ViewHolder(binding.root) {

    }
}