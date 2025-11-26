package com.example.frunizone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.frunizone.R
import com.example.frunizone.model.AddressModel

class AddressAdapter(
    private val list: ArrayList<AddressModel>,
    private val onEdit: (AddressModel) -> Unit,
    private val onRemove: (AddressModel) -> Unit,
    private val onSelect: (AddressModel) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddViewHolder>() {

    private var selectedId = ""

    inner class AddViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val radio = view.findViewById<RadioButton>(R.id.radio_select)
        val name = view.findViewById<TextView>(R.id.txt_name)
        val type = view.findViewById<TextView>(R.id.txt_type)
        val address = view.findViewById<TextView>(R.id.txt_address)
        val mobile = view.findViewById<TextView>(R.id.tvMobile)
        val btnEdit = view.findViewById<Button>(R.id.btn_edit)
        val btnRemove = view.findViewById<Button>(R.id.btn_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_address, parent, false)
        return AddViewHolder(v)
    }

    override fun onBindViewHolder(h: AddViewHolder, position: Int) {
        val a = list[position]

        h.name.text = a.full_name
        h.type.text = a.address_type
        h.mobile.text = a.phone

        h.address.text = "${a.house_no}, ${a.area}, ${a.landmark}, ${a.city} - ${a.pincode}"

        //  Set default selected from API/database
        if (selectedId.isEmpty() && a.is_default == "1") {
            selectedId = a.id ?: ""
        }
        h.radio.isChecked = (a.id == selectedId)

        h.radio.setOnClickListener {
            selectedId = a.id ?: ""
            notifyDataSetChanged()
            onSelect(a)
        }

        h.btnEdit.setOnClickListener { onEdit(a) }
        h.btnRemove.setOnClickListener { onRemove(a) }
    }

    override fun getItemCount() = list.size
}
