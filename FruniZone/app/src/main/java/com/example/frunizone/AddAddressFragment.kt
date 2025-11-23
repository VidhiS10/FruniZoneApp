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
import androidx.core.widget.addTextChangedListener
import com.android.volley.Request
import javax.security.auth.callback.Callback
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
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

        (requireActivity() as? CartActivity)?.updateSteps(2)
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
        btnSave = view.findViewById(R.id.btnSaveAddress)

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
        val selectedType = when (rgAddressType.checkedRadioButtonId) {
            R.id.rbWork -> "Work"
            R.id.rbOther -> "Other"
            else -> "Home"
        }

        val data = HashMap<String, String>()
        data["full_name"] = etFullName.text.toString()
        data["phone"] = etPhone.text.toString()
        data["pincode"] = etPincode.text.toString()
        data["house_no"] = etHouseNo.text.toString()
        data["area"] = etArea.text.toString()
        data["landmark"] = etLandmark.text.toString()
        data["city"] = etCity.text.toString()
        data["state"] = etState.text.toString()
        data["address_type"] = selectedType
        data["is_default"] = if (chkDefault.isChecked) "1" else "0"

        // TODO: Send to API with Retrofit/Volley
        Toast.makeText(requireContext(), "Saving...", Toast.LENGTH_SHORT).show()
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