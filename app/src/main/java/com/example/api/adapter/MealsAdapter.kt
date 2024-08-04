package com.example.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.api.R
import com.example.api.model.Data
import com.example.api.model.ModelProduct

class MealsAdapter() : RecyclerView.Adapter<MealsAdapter.MealViewHolder>(){

    inner class MealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        private val productTitle: TextView = itemView.findViewById(R.id.item_name)
        private val productImage: ImageView = itemView.findViewById(R.id.item_image)
        private val productSaveImage: ImageView = itemView.findViewById(R.id.bookmark_icon)
        private val productRatingBar: RatingBar = itemView.findViewById(R.id.item_rating)
        private val productPrice: TextView = itemView.findViewById(R.id.item_discounted_price)
        private val productOriginalPrice: TextView = itemView.findViewById(R.id.item_original_price)
        var isChecked:Boolean=false
        fun bind(data: Data) {
            val price =  data.price.toFloat() - data.discount.toFloat()

            productTitle.text = data.name
            productOriginalPrice.text = data.price
            productPrice.text = price.toString()
            productRatingBar.rating = data.rate.toFloat()
            Glide.with(itemView)
                .load(data.product_image)
                .into(productImage)

            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(data)
                }
            }
            productSaveImage.setOnClickListener {
                onSaveClickListener?.let {
                    if (!isChecked){
                        productSaveImage.setImageResource(R.drawable.ic_icon_book_mark_fill)
                        isChecked = true
                    }
                    else{
                        productSaveImage.setImageResource(R.drawable.ic_icon_book_burble)
                        isChecked = false
                    }

                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this ,diffCallback)


    private var onItemClickListener: ((Data) -> Unit)? =null

    fun setOnItemClickListener(listener: (Data)->Unit){
        onItemClickListener = listener
    }

 private var onSaveClickListener: ((Data) -> Unit)? =null

    fun setOnSaveClickListener(listener: (Data)->Unit){
        onSaveClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = differ.currentList[position]

        holder.bind(meal)
    }


}