package com.example.frunizone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frunizone.R
import com.example.frunizone.model.OrderModel
import com.example.frunizone.util.ConstantData
import com.transferwise.sequencelayout.SequenceLayout
import com.transferwise.sequencelayout.SequenceStep

class YourOrdersAdapter(
    private val orderModels: List<OrderModel>,
    private val clickListener: OnClickListener,
    private val context: Context
) : RecyclerView.Adapter<YourOrdersAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_yourorder, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val orderModel = orderModels[position]

        holder.tvCartName.text = orderModel.pname
        holder.tvCartPrice.text = orderModel.amount
        holder.qty.text = orderModel.quantity
        holder.tvTotal.text = orderModel.total_amount

        Glide.with(context)
            .load(ConstantData.SERVER_IMAGE_ADDRESS + orderModel.ppic.substring(1))
            .into(holder.couponImg)

        val status = orderModel.status.toInt()
        val state = orderModel.state.toInt()
        holder.tracker.visibility = View.GONE

        if (status == 4) {   // order cancelled
            holder.cancel.text = "Order is cancelled"
            holder.cancel.setTextColor(context.getColor(R.color.darkblue))
            holder.cancel.isEnabled = false
            holder.tracker.visibility = View.GONE

        }
        else {

            if(state==4||state==5){
                holder.cancel.isEnabled = false
                holder.cancel.visibility=View.GONE
            }

            holder.llytMyOrders.setOnClickListener {
                holder.tracker.visibility = View.VISIBLE
            }

            holder.tracker.setOnClickListener {
                holder.tracker.visibility = View.GONE
            }

            when (orderModel.state) {
                "0" -> holder.step1.setActive(true)
                "1" -> holder.step2.setActive(true)
                "2" -> holder.step3.setActive(true)
                "3" -> holder.step4.setActive(true)
                else -> holder.step5.setActive(true)
            }

            holder.step1.setSubtitle("Order Received from Medico")
            holder.step2.setSubtitle("Order dispatched from Warehouse")
            holder.step3.setSubtitle("Order is being Shipped")
            holder.step4.setSubtitle("Order is Out for delivery")
            holder.step5.setSubtitle("Delivered order")
        }

        holder.cancel.setOnClickListener {
            clickListener.removeClick(orderModel)
        }
    }

    override fun getItemCount(): Int = orderModels.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val couponImg: ImageView = itemView.findViewById(R.id.imgCart)
        val tvCartName: TextView = itemView.findViewById(R.id.tvCartName)
        val tvCartPrice: TextView = itemView.findViewById(R.id.tvCartPrice)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
        val qty: TextView = itemView.findViewById(R.id.qty)
        val cancel: TextView = itemView.findViewById(R.id.cancel)

        val tracker: SequenceLayout = itemView.findViewById(R.id.tracker)
        val step1: SequenceStep = itemView.findViewById(R.id.step1)
        val step2: SequenceStep = itemView.findViewById(R.id.step2)
        val step3: SequenceStep = itemView.findViewById(R.id.step3)
        val step4: SequenceStep = itemView.findViewById(R.id.step4)
        val step5: SequenceStep = itemView.findViewById(R.id.step5)

        val llytMyOrders: LinearLayout = itemView.findViewById(R.id.llytMyOrders)
    }

    interface OnClickListener {
        fun onClickPlus(om: OrderModel)
        fun onClickMinus(om: OrderModel)
        fun removeClick(om: OrderModel)
    }
}
