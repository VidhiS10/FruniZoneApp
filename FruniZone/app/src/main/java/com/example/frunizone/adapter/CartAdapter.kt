package com.example.frunizone.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frunizone.R
import com.example.frunizone.model.OrderModel
import com.example.frunizone.util.ConstantData

class CartAdapter(
    private val orderModels: List<OrderModel>,
    private val clickListener: OnClickListener,
    private val context: Context
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int = orderModels.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val orderModel = orderModels[position]

        holder.tvCartName.text = orderModel.pname
        holder.qty.text = orderModel.quantity
        holder.tvCartPrice.text = orderModel.amount
        holder.tvTotal.text = orderModel.totalAmount

        Glide.with(context)
            .load(ConstantData.SERVER_IMAGE_ADDRESS + orderModel.ppic)
            .into(holder.imgCart)

        // Remove item
        holder.tvRemove.setOnClickListener {
            clickListener.removeClick(orderModel)
        }

        // Increase quantity
        holder.qty_add.setOnClickListener {
            var count = orderModel.quantity.toInt()

            if (count == 10) {
                Toast.makeText(context, "Quantity cannot exceed 10", Toast.LENGTH_SHORT).show()
            } else {
                count++
                orderModel.quantity = count.toString()

                val total = count * orderModel.amount.toDouble()
                orderModel.totalAmount = total.toString()

                holder.tvTotal.text = total.toString()
                holder.qty.text = count.toString()

                clickListener.onClickPlus(orderModel)
            }
        }

        // Decrease quantity
        holder.qty_less.setOnClickListener {
            var count = orderModel.quantity.toInt()

            if (count == 1) {
                Toast.makeText(context, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show()
            } else {
                count--
                orderModel.quantity = count.toString()

                val total = count * orderModel.amount.toDouble()
                orderModel.totalAmount = total.toString()

                holder.tvTotal.text = total.toString()
                holder.qty.text = count.toString()

                clickListener.onClickMinus(orderModel)
            }
        }
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCart: ImageView = itemView.findViewById(R.id.imgCart)
        val tvCartName: TextView = itemView.findViewById(R.id.tvCartName)
        val tvCartPrice: TextView = itemView.findViewById(R.id.tvCartPrice)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
        val qty_less: TextView = itemView.findViewById(R.id.qty_less)
        val qty_add: TextView = itemView.findViewById(R.id.qty_add)
        val qty: TextView = itemView.findViewById(R.id.qty)
        val tvRemove: TextView = itemView.findViewById(R.id.tvRemove)
    }

    interface OnClickListener {
        fun onClickPlus(om: OrderModel)
        fun onClickMinus(om: OrderModel)
        fun removeClick(om: OrderModel)
    }
}
