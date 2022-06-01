package com.eCommerce.shopify.ui.profile.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.ProfileWishlistRowBinding
import com.eCommerce.shopify.model.Product

class WishlistAdapter : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    var wishlist: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val itemBinding = ProfileWishlistRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishlistViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
//        holder.bind(wishlist[position].imageUrl, wishlist[position].name, wishlist[position].price,
//                wishlist[position].isFavourite)
        holder.bind("https://media.istockphoto.com/photos/elegance-peach-vintage-dress-isolated-on-white-background-picture-id1217970962?k=20&m=1217970962&s=612x612&w=0&h=XJeWZiOrycXuEawk2SnjXqCFpjZ9mMUPNKJqnx82ziU=", "My item",
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