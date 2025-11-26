package com.example.frunizone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.frunizone.api.AddressApi
import com.example.frunizone.model.AddressModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditAddressFragment : Fragment() {
    private var addressId: String? = null
    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etHouseNo: EditText
    private lateinit var etArea: EditText
    private lateinit var etLandmark: EditText
    private lateinit var etPincode: EditText
    private lateinit var etCity: EditText
    private lateinit var etState: EditText
    private lateinit var rgAddressType: RadioGroup
    private lateinit var chkDefault: CheckBox
    private lateinit var btnSave: Button

    private lateinit var btnCancel: Button


    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addressId = arguments?.getString("id")
        Toast.makeText(requireContext(), addressId, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val back = view.findViewById<ImageView>(R.id.ivBack)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        btnSave=view.findViewById(R.id.btnSave)
        btnCancel=view.findViewById(R.id.btnCancel)

        title.text = "Edit Address"
        btnSave.setOnClickListener {
            updateNow()
        }
        btnCancel.setOnClickListener {
            requireActivity().onBackPressed()   // Go back
        }


        back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        etFullName = view.findViewById(R.id.etFullName)
        etPhone = view.findViewById(R.id.etPhone)
        etHouseNo = view.findViewById(R.id.etHouseNo)
        etArea = view.findViewById(R.id.etArea)
        etLandmark = view.findViewById(R.id.etLandmark)
        etPincode = view.findViewById(R.id.etPincode)
        etCity = view.findViewById(R.id.etCity)
        etState = view.findViewById(R.id.etState)
        rgAddressType = view.findViewById(R.id.rgAddressType)
        chkDefault = view.findViewById(R.id.chkDefault)
        btnSave = view.findViewById(R.id.btnSave)

        if (addressId.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "No address id found", Toast.LENGTH_SHORT).show()
            return
        }

        AddressApi().getSingleAddress(addressId!!, requireActivity()) { output ->

            if (output == null || output.addresses.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
                return@getSingleAddress
            }

            val a = output.addresses!![0]

            etFullName.setText(a.full_name)
            etPhone.setText(a.phone)
            etHouseNo.setText(a.house_no)
            etArea.setText(a.area)
            etLandmark.setText(a.landmark)
            etPincode.setText(a.pincode)
            etCity.setText(a.city)
            etState.setText(a.state)

            // ---- Address Type ----
            when (a.address_type) {
                "Home" -> rgAddressType.check(R.id.rbHome)
                "Work" -> rgAddressType.check(R.id.rbWork)
                else -> rgAddressType.check(R.id.rbOther)
            }

            // ---- Default ----
            chkDefault.isChecked = a.is_default == "1"
        }



    }

    private fun updateNow(){

        if(addressId.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Missing ID",Toast.LENGTH_SHORT).show()
            return
        }
        val isDefault = if (chkDefault.isChecked) "1" else "0"

        val model = AddressModel(
            id = addressId!!,
            full_name = etFullName.text.toString(),
            phone = etPhone.text.toString(),
            house_no = etHouseNo.text.toString(),
            area = etArea.text.toString(),
            landmark = etLandmark.text.toString(),
            pincode = etPincode.text.toString(),
            city = etCity.text.toString(),
            state = etState.text.toString(),
            address_type =
                when (rgAddressType.checkedRadioButtonId) {
                    R.id.rbHome -> "Home"
                    R.id.rbWork -> "Work"
                    else -> "Other"
                },
            is_default = isDefault
        )

        AddressApi().updateAddress(model,requireActivity()){ ok ->
            if(ok){
                Toast.makeText(requireContext(),"Updated!",Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main, SelectAddressFragment())
                    .addToBackStack(null)
                    .commit()
            }else{
                Toast.makeText(requireContext(),"Update Failed!",Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

