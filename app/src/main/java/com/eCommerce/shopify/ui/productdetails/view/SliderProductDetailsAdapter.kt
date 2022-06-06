package com.eCommerce.shopify.ui.productdetails.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.model.ImageProduct
import com.eCommerce.shopify.model.SliderItem
import com.makeramen.roundedimageview.RoundedImageView

class SliderProductDetailsAdapter(
    private var context: Context,
    private var sliderItems: List<ImageProduct>
) : RecyclerView.Adapter<SliderProductDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.slide_product_details_item_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgView?.let {
            Glide
                .with(context)
                .load(sliderItems[position].src)
                .into(it)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgView: RoundedImageView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.imageSlider)
                }
                return field
            }
            private set
    }
}