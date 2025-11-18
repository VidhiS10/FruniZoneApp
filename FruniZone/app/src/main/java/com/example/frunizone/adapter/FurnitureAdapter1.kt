package com.example.frunizone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frunizone.R
import com.example.frunizone.model.FurnitureModel
import com.example.frunizone.util.ConstantData

class FurnitureAdapter1(
    var context: Context,
    var list: ArrayList<FurnitureModel>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<FurnitureAdapter1.FurnitureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_furniture, parent, false)
        return FurnitureViewHolder(view)
    }
/*
    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val model = list[position]

        holder.proName.text = model.sub_cat_name

        holder.proId.text = model.sub_cat_id
        holder.proPrice.text = model.sub_cat_price

        holder.proImg.setOnClickListener {
            listener.onClick(position, model)
        }

        Glide.with(context)
            .load(ConstantData.SERVER_IMAGE_ADDRESS + model.sub_cat_pic1.substring(1))
            .into(holder.proImg)
    }

override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
    val model = list[position]

    holder.proName.text = model.sub_cat_name ?: ""
    holder.proId.text = model.sub_cat_id ?: ""
    holder.proPrice.text = model.sub_cat_price ?: ""

    val imagePath = model.sub_cat_pic1?.substring(1) ?: ""

    Glide.with(context)
        .load(ConstantData.SERVER_IMAGE_ADDRESS + imagePath)
        .into(holder.proImg)

    holder.card.setOnClickListener { listener.onClick(model) }
    holder.image.setOnClickListener { listener.onClick(model) }
}
*/
override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
    val model = list[position]

    holder.proName.text = model.sub_cat_name ?: ""
    holder.proId.text = model.sub_cat_id ?: ""
    holder.proPrice.text = model.sub_cat_price ?: ""

    // safe image
    val imagePath = model.sub_cat_pic1?.removePrefix("/") ?: ""

    Glide.with(context)
        .load(ConstantData.SERVER_IMAGE_ADDRESS + imagePath)
        .into(holder.proImg)

    // Click listeners
    holder.proImg.setOnClickListener { listener.onClick(position, model) }
    holder.itemView.setOnClickListener { listener.onClick(position, model) }

}

    override fun getItemCount(): Int = list.size

    class FurnitureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val proImg: ImageView = itemView.findViewById(R.id.pro_img)
        val proName: TextView = itemView.findViewById(R.id.pro_name)
        val proPrice: TextView = itemView.findViewById(R.id.pro_price)
        val proId: TextView = itemView.findViewById(R.id.pro_id)

//
//        val proImg: ImageView = itemView.findViewById(R.id.pro_img)
//        val proName: TextView = itemView.findViewById(R.id.pro_name)
//        val proPrice: TextView = itemView.findViewById(R.id.pro_price)
//        val proId: TextView = itemView.findViewById(R.id.pro_id)
//        val card: CardView = itemView.findViewById(R.id.card)
//        val image: ImageView = itemView.findViewById(R.id.image)
    }

    interface ItemClickListener {
        fun onClick(position: Int, model: FurnitureModel)
    }
}
