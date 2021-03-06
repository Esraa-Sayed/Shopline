package com.eCommerce.shopify.ui.AddressAndCheckoutAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.AddressRowBinding
import com.eCommerce.shopify.model.Addresse

class AddressesAdapter(val context: Context, var addresses:List<Addresse>, var onRowClicked: ((Addresse)->Unit)?):
    RecyclerView.Adapter<AddressesAdapter.AddressViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
       val binding = AddressRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return  AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
       val address = addresses[position]
        holder.binding.countryName.text = address.country
        holder.binding.fullAddress.text = address.address1.toString()
        holder.binding.cityAddress.text = address.city
        holder.itemView.setOnClickListener {
            //onRowClicked?.onRowClickedListenerAddress(address)
            onRowClicked?.let { it1 -> it1(address) }
        }
    }

    override fun getItemCount() =  addresses.size
    fun updateData(addresses:List<Addresse>){
        this.addresses = addresses
        notifyDataSetChanged()
    }
    class AddressViewHolder(val binding: AddressRowBinding):RecyclerView.ViewHolder(binding.root)
}