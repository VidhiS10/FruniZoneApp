package com.example.frunizone

import android.app.Activity
import android.content.Context
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
import androidx.core.widget.addTextChangedListener
import com.android.volley.Request
import javax.security.auth.callback.Callback
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import com.example.frunizone.api.AddressApi
import com.example.frunizone.model.AddressModel
import com.example.frunizone.util.ConstantData
import org.json.JSONArray



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPincode: EditText
    private lateinit var etHouseNo: EditText
    private lateinit var etArea: EditText
    private lateinit var etLandmark: EditText
    private lateinit var etCity: EditText
    private lateinit var etState: EditText
    private lateinit var rgAddressType: RadioGroup
    private lateinit var chkDefault: CheckBox
    private lateinit var btnSave: Button
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_address, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val back = view.findViewById<ImageView>(R.id.ivBack)
        val title = view.findViewById<TextView>(R.id.tvTitle)

        title.text = "Add Address"

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }

//        (requireActivity() as? CartActivity)?.updateSteps(2)
        etFullName = view.findViewById(R.id.etFullName)
        etPhone = view.findViewById(R.id.etPhone)
        etPincode = view.findViewById(R.id.etPincode)
        etHouseNo = view.findViewById(R.id.etHouseNo)
        etArea = view.findViewById(R.id.etArea)
        etLandmark = view.findViewById(R.id.etLandmark)
        etCity = view.findViewById(R.id.etCity)
        etState = view.findViewById(R.id.etState)
        rgAddressType = view.findViewById(R.id.rgAddressType)
        chkDefault = view.findViewById(R.id.chkDefault)
        btnSave = view.findViewById(R.id.btnAdsSave)



        val sp = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
        val savedName = sp.getString(ConstantData.KEY_USERNAME, "")
        val savedPhone = sp.getString(ConstantData.KEY_PHONE, "")
//        Toast.makeText(requireContext(), "name:"+savedName+"phone"+savedPhone, Toast.LENGTH_SHORT).show()

       val userId = sp.getString(ConstantData.KEY_ID, "") ?: ""
//        Toast.makeText(requireContext(), "User"+ userId, Toast.LENGTH_SHORT).show()

        etFullName.setText(savedName)
        etPhone.setText(savedPhone)

        btnSave.setOnClickListener {
            saveAddress()
        }


        etPincode.addTextChangedListener {
            if (it.toString().length == 6) {
                checkPincode(it.toString())
            }
        }


    }
    private fun saveAddress() {

        // ---- VALIDATION ---- //

        if (etFullName.text.isNullOrEmpty()) {
            etFullName.error = "Enter full name"
            etFullName.requestFocus()
            return
        }

        if (etPhone.text.isNullOrEmpty() || etPhone.text.length != 10) {
            etPhone.error = "Enter valid 10-digit phone"
            etPhone.requestFocus()
            return
        }

        if (etPincode.text.isNullOrEmpty() || etPincode.text.length != 6) {
            etPincode.error = "Enter valid 6-digit pincode"
            etPincode.requestFocus()
            return
        }

        if (etHouseNo.text.isNullOrEmpty()) {
            etHouseNo.error = "Enter house number"
            etHouseNo.requestFocus()
            return
        }

        if (etArea.text.isNullOrEmpty()) {
            etArea.error = "Enter area"
            etArea.requestFocus()
            return
        }

        if (etLandmark.text.isNullOrEmpty()) {
            etLandmark.error = "Enter landmark"
            etLandmark.requestFocus()
            return
        }

        if (etCity.text.isNullOrEmpty()) {
            etCity.error = "Enter city"
            etCity.requestFocus()
            return
        }

        if (etState.text.isNullOrEmpty()) {
            etState.error = "Enter state"
            etState.requestFocus()
            return
        }

        if (rgAddressType.checkedRadioButtonId == -1) {
            Toast.makeText(requireContext(), "Select address type", Toast.LENGTH_SHORT).show()
            return
        }

        // ---- GET ADDRESS TYPE ---- //
        val selectedType = when (rgAddressType.checkedRadioButtonId) {
            R.id.rbWork -> "Work"
            R.id.rbOther -> "Other"
            else -> "Home"
        }

        // ---- GET USER ID ---- //

        val sp = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
        val userId = sp.getString(ConstantData.KEY_ID, "") ?: ""
        Toast.makeText(requireContext(), "User"+ userId, Toast.LENGTH_SHORT).show()


        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        // ---- CREATE MODEL ---- //
        val model = AddressModel(
            user_id = userId,
            full_name = etFullName.text.toString().trim(),
            phone = etPhone.text.toString().trim(),
            house_no = etHouseNo.text.toString().trim(),
            area = etArea.text.toString().trim(),
            landmark = etLandmark.text.toString().trim(),
            city = etCity.text.toString().trim(),
            state = etState.text.toString().trim(),
            pincode = etPincode.text.toString().trim(),
            address_type = selectedType,
            is_default = if (chkDefault.isChecked) "1" else "0"
        )

        // ---- CALL API ---- //
        AddressApi().addAddress(model, requireActivity()) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Address Saved Successfully!", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Failed to save address!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun checkPincode(pincode: String) {
        val url = "https://api.postalpincode.in/pincode/$pincode"

        val request = StringRequest(
            Request.Method.GET, url,
            { response ->

                val jsonArray = JSONArray(response)
                val obj = jsonArray.getJSONObject(0)

                if (obj.getString("Status") == "Success") {
                    val postOffice = obj.getJSONArray("PostOffice").getJSONObject(0)

                    val city = postOffice.getString("District")
                    val state = postOffice.getString("State")

                    etCity.setText(city)
                    etState.setText(state)
                }
            },
            { }
        )

        Volley.newRequestQueue(requireContext()).add(request)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}