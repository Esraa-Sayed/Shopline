package com.eCommerce.shopify.ui.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.model.SmartCollection
import com.eCommerce.shopify.utils.AppConstants.playAnimation

class HomeBrandAdapter(
    var context: Context,
    private var brandList: List<SmartCollection>,
    private var onBrandClickListener: OnBrandClickListener
) : RecyclerView.Adapter<HomeBrandAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_brand, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        playAnimation(holder.itemView, context, R.anim.open_view_zoom_in_fade_in)

        holder.imgViewBrand?.let {
            Glide
                .with(context)
                .load(brandList[position].image.src)
                .into(it)
        }

        val words = brandList[position].title.lowercase().split(" ")
        var companyName = ""
        words.forEach { word ->
            companyName += word.replaceFirstChar { it.uppercase() } + " "
        }

        holder.txtViewBrand?.text = companyName.trim()

        holder.itemView.setOnClickListener {
            onBrandClickListener.onBrandClick(brandList[position])
        }
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(brandList: List<SmartCollection>) {
        this.brandList = brandList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewBrand: ImageView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.imgViewBrand)
                }
                return field
            }
            private set
        var txtViewBrand: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.txtViewBrand)
                }
                return field
            }
            private set
    }
}