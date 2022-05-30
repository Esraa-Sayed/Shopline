package com.eCommerce.shopify.ui.profile.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.ProfileWishlistRowBinding

class WishlistAdapter : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    //var wishlist: MutableList<Wishlist> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val itemBinding = ProfileWishlistRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishlistViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
//        holder.bind(wishlist[position].imageUrl, wishlist[position].name, wishlist[position].price,
//                wishlist[position].isFavourite)
        holder.bind("kkj", "My item",
            145.5, true)

    }
    override fun getItemCount(): Int {
        //return wishlist.size
        return 4
    }

    class WishlistViewHolder(val _bindView: ProfileWishlistRowBinding)
        : RecyclerView.ViewHolder(_bindView.root) {

        fun bind(imageUrl: String, name: String, price: Double, isFavourite: Boolean){
            if (imageUrl !== null) {
                Glide.with(_bindView.root.context)
                    .load(imageUrl)
                    .into(_bindView.pWishlistRowImage)
            } else {
                _bindView.pWishlistRowImage.setImageResource(R.drawable.ic_launcher_background)
            }
            _bindView.pWishlistRowName.text = name
            _bindView.pWishlistRowPrice.text = price.toString()
        }
    }
}