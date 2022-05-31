package com.eCommerce.shopify.ui.category.view

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
import com.eCommerce.shopify.model.CustomCollection
import com.eCommerce.shopify.utils.AppConstants.playAnimation

class CategoryAdapter(
    var context: Context,
    private var categoryList: List<CustomCollection>,
    private var onCategoryClickListener: OnCategoryClickListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        playAnimation(holder.itemView, context, R.anim.row_slide_in)

        holder.imgViewCategory?.let {
            Glide
                .with(context)
                .load(categoryList[position].image?.src)
                .error(R.drawable.no_category)
                .placeholder(R.drawable.no_category)
                .into(it)
        }

        val words = categoryList[position].title.lowercase().split(" ")
        var categoryName = ""
        words.forEach { word ->
            categoryName += word.replaceFirstChar { it.uppercase() } + " "
        }

        holder.txtViewCategory?.text = categoryName.trim()

        holder.itemView.setOnClickListener {
            onCategoryClickListener.onCategoryClick(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(categoryList: List<CustomCollection>) {
        this.categoryList = categoryList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewCategory: ImageView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.imgViewCategory)
                }
                return field
            }
            private set
        var txtViewCategory: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.txtViewCategory)
                }
                return field
            }
            private set
    }
}