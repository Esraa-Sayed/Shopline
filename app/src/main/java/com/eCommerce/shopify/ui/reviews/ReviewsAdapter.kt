package com.eCommerce.shopify.ui.reviews

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eCommerce.shopify.databinding.ReviewsItemLayoutBinding

class ReviewsAdapter:RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private lateinit var context:Context
    private lateinit var reviewsList:List<Review>

    constructor(context:Context,reviewsList:List<Review>){
        this.context = context
        this.reviewsList = reviewsList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewsAdapter.ReviewsViewHolder {
        val itemBinding = ReviewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ReviewsAdapter.ReviewsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    inner class ReviewsViewHolder(private val itemBinding: ReviewsItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {

    }
}