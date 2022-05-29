package com.eCommerce.shopify.ui.reviews

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.databinding.ReviewsItemLayoutBinding
import com.eCommerce.shopify.ui.favorite.model.Product
import org.w3c.dom.Text

class ReviewsAdapter:RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private var context:Context
    private var reviewsList:List<Review>

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
        holder.reviewerName.text = reviewsList[position].reviewerName
        holder.reviewerRate.rating = reviewsList[position].reviewerRate.toFloat()
        holder.reviewerComment.text = reviewsList[position].reviewerComment
        Glide.with(context)
            .load(reviewsList[position].reviewerImg)
            .into(holder.reviewerImg)
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    fun setReviewsList(reviewsList:List<Review>){
        this.reviewsList = reviewsList
    }

    inner class ReviewsViewHolder(private val itemBinding: ReviewsItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val reviewerName = itemBinding.reviewerName
        val reviewerRate = itemBinding.reviewRate
        val reviewerImg = itemBinding.reviewerImg
        val reviewerComment = itemBinding.reviewerComment
    }
}