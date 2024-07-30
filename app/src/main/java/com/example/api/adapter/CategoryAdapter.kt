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
import com.example.api.model.CategoryData
import com.example.api.model.Data

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        private val productTitle: TextView = itemView.findViewById(R.id.text_type)

        fun bind(data: CategoryData) {
            productTitle.text = data.name
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(data)
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CategoryData>(){
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this ,diffCallback)


    private var onItemClickListener: ((CategoryData) -> Unit)? =null

    fun setOnItemClickListener(listener: (CategoryData)->Unit){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tab,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = differ.currentList[position]

        holder.bind(category)
    }

}