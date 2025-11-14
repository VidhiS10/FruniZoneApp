package com.example.frunizone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frunizone.R
import com.example.frunizone.model.CategoryModel
import com.example.frunizone.util.ConstantData
import de.hdodenhof.circleimageview.CircleImageView

class CategoryAdapter(
    private val context: Context,
    private var list: ArrayList<CategoryModel>,
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_cat, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = list[position]
        holder.tvCat.text = category.cat_name
        holder.tvCatId.text = category.cat_id
        holder.imgCat.setOnClickListener {
            itemClickListener.onClick(position, category)
        }
//        Glide.with(context)
//            .load(ConstantData.SERVER_IMAGE_ADDRESS + category.cat_pic1.substring(1))
//            .into(holder.imgCat)
        val imagePath = category.cat_pic1?.substring(1) ?: ""
        Glide.with(context)
            .load(ConstantData.SERVER_IMAGE_ADDRESS + imagePath)
            .into(holder.imgCat)
    }

    override fun getItemCount(): Int = list.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCat: CircleImageView = itemView.findViewById(R.id.cat1)
        val tvCat: TextView = itemView.findViewById(R.id.tvCat)
        val tvCatId: TextView = itemView.findViewById(R.id.tvCatId)
    }

    interface ItemClickListener {
        fun onClick(position: Int, model: CategoryModel)
    }
}
