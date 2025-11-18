package com.example.frunizone.adapter



import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frunizone.R
import com.example.frunizone.model.CouponModel
import com.example.frunizone.util.ConstantData

class CouponAdapter(
    private var context: Context,
    private var list: ArrayList<CouponModel>,
    private var listener: ItemClickListener
) : RecyclerView.Adapter<CouponAdapter.CouponViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_coupon, parent, false)
        return CouponViewHolder(view)
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        val model = list[position]

        holder.couponCode.text = model.coupen_code
        holder.couponDesc.text = model.coupen_description
        holder.couponSavers.text = model.coupen_discount

        holder.btnCopyCode.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", model.coupen_code)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "Code Copied", Toast.LENGTH_SHORT).show()
        }

        Glide.with(context)
            .load(ConstantData.SERVER_IMAGE_ADDRESS + model.coupen_img?.substring(1))
            .into(holder.couponImage)
    }

    override fun getItemCount(): Int = list.size

    inner class CouponViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val couponImage: ImageView = itemView.findViewById(R.id.coupon_img)
        val couponCode: TextView = itemView.findViewById(R.id.coupon_code)
        val couponDesc: TextView = itemView.findViewById(R.id.coupon_desc)
        val couponSavers: TextView = itemView.findViewById(R.id.coupon_savers)
        val btnCopyCode: Button = itemView.findViewById(R.id.btncopycode)
    }

    interface ItemClickListener {
        fun onClick(position: Int, model: CouponModel)
    }
}
