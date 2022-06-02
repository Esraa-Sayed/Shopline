package com.eCommerce.shopify.ui.profile.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.databinding.ProfileWishlistRowBinding
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.Product


class WishlistAdapter(val listener: OnProductListner) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    var wishlist: List<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val itemBinding = ProfileWishlistRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WishlistViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        holder.bind(wishlist[position].image, wishlist[position].title, wishlist[position].variants[0].price,
                wishlist[position].isFavorite)
//        holder.bind("https://media.istockphoto.com/photos/elegance-peach-vintage-dress-isolated-on-white-background-picture-id1217970962?k=20&m=1217970962&s=612x612&w=0&h=XJeWZiOrycXuEawk2SnjXqCFpjZ9mMUPNKJqnx82ziU=", "My item",
//            145.5, true)
        holder._bindView.pWishlistRow.setOnClickListener{
            listener.onProductClicked(wishlist[position])
        }
        holder._bindView.favoriteBtn.setOnClickListener{
            listener.onFavBtnClick(wishlist[position])
        }

    }
    override fun getItemCount(): Int {
        if(wishlist.isEmpty())
            return 0
        else if(wishlist.size > 4)
            return 4
        return wishlist.size
    }
    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setWishlist1")
    fun setWishlist(wishlist: List<Product>){
        this.wishlist = wishlist
        notifyDataSetChanged()
    }

    class WishlistViewHolder(val _bindView: ProfileWishlistRowBinding)
        : RecyclerView.ViewHolder(_bindView.root) {

        fun bind(imageUrl: ImageProduct?, name: String, price: String, isFavourite: Boolean){
            if (imageUrl !== null) {
                Glide.with(_bindView.root.context)
                    .load(imageUrl.src)
                    .into(_bindView.pWishlistRowImage)
            } else {
                _bindView.pWishlistRowImage.setImageResource(R.drawable.ic_launcher_background)
            }
            _bindView.pWishlistRowName.text = name
            _bindView.pWishlistRowPrice.text = price
        }
    }
}