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

class FurnitureAdapter(
    private val context: Context,
    private val list: ArrayList<FurnitureModel>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<FurnitureAdapter.FurnitureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_top_selling_product, parent, false)
        return FurnitureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val model = list[position]

        holder.proName.text = model.subCatName
        holder.proId.text = model.subCatId
        holder.proPrice.text = model.subCatPrice

        // ----------------------------
        //   FIX: SAFE IMAGE HANDLING
        // ----------------------------
        val rawImg = model.subCatPic1 ?: ""          // Null-safe

        val finalImg = if (rawImg.startsWith("/")) { // Avoid substring crash
            rawImg.substring(1)
        } else rawImg

        Glide.with(context)
            .load(ConstantData.SERVER_IMAGE_ADDRESS + finalImg)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.proImg)

        // ----------------------------
        //   CLICK LISTENERS
        // ----------------------------
        holder.card.setOnClickListener { listener.onClick(model) }
        holder.image.setOnClickListener { listener.onClick(model) }
    }

    override fun getItemCount(): Int = list.size

    class FurnitureViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val proImg: ImageView = item.findViewById(R.id.pro_img)
        val proName: TextView = item.findViewById(R.id.pro_name)
        val proPrice: TextView = item.findViewById(R.id.pro_price)
        val proId: TextView = item.findViewById(R.id.pro_id)
        val card: CardView = item.findViewById(R.id.card)

        // If your layout has this extra image
        val image: ImageView = item.findViewById(R.id.image)
    }

    interface ItemClickListener {
        fun onClick(model: FurnitureModel)
    }
}
