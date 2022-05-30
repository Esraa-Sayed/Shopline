package com.eCommerce.shopify.ui.home.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.eCommerce.shopify.R
import com.eCommerce.shopify.model.SliderItem
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdapter(
    private var sliderItems: MutableList<SliderItem>,
    private var viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.slide_item_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgView?.setImageResource(sliderItems[position].image)
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