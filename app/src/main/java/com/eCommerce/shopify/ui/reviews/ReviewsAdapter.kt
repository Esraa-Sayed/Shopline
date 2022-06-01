package com.eCommerce.shopify.ui.reviews

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eCommerce.shopify.R
import com.eCommerce.shopify.model.Product

class ReviewsAdapter(
    private var context: Context,
    private var reviewsList: List<Review>
    ): RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_review_item, parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.txtViewReviewerName?.text = reviewsList[position].reviewerName
        holder.reviewRate?.rating = reviewsList[position].reviewerRate.toFloat()
        holder.reviewerComment?.text = reviewsList[position].reviewerComment
        Glide.with(context)
            .load(reviewsList[position].reviewerImg)
            .into(holder.imgViewCategory!!)
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataToAdapter(reviewsList: List<Review>) {
        this.reviewsList = reviewsList
        notifyDataSetChanged()
    }

    class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewCategory: ImageView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.imgViewCategory)
                }
                return field
            }
            private set
        var txtViewReviewerName: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.txtViewReviewerName)
                }
                return field
            }
            private set
        var reviewRate: RatingBar? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.reviewRate)
                }
                return field
            }
            private set
        var reviewerComment: TextView? = null
            get() {
                if (field == null) {
                    field = itemView.findViewById(R.id.reviewerComment)
                }
                return field
            }
            private set
    }
}