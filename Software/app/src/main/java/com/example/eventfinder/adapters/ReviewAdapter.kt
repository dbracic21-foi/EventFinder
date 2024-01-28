package com.example.eventfinder.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventfinder.R
import com.example.eventfinder.entities.Review

class ReviewAdapter(private var reviews: List<Review>) :

class ReviewAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val textComment: TextView = itemView.findViewById(R.id.textComment)

        fun bind(review: Review) {
            ratingBar.rating = review.rating
            textComment.text = review.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }
    fun setData(reviews: List<Review>) {
        this.reviews = reviews
        notifyDataSetChanged()
    }
    fun clearData() {
        reviews = emptyList()
        notifyDataSetChanged()
        Log.d("ReviewAdapter", "Podaci o recenzijama su obrisani.")

    }
}
