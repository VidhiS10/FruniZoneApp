package com.example.frunizone


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.frunizone.api.AddressApi
import com.example.frunizone.util.ConstantData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddressFragment : Fragment() {
    private lateinit var tvName: TextView
    private lateinit var tvHome: TextView
    private lateinit var tvDefault: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvMobile: TextView
    private lateinit var btnChange: Button
    // TODO: Rename and change types of parameters
    private var param1: String? = null
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
        return inflater.inflate(R.layout.fragment_address, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = view.findViewById<ImageView>(R.id.ivBack)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        title.text = "Address"
        back.setOnClickListener {
            val intent = Intent(requireContext(), CartActivity::class.java)
            startActivity(intent)
            requireActivity().finish()   // optional → closes current activity
        }

        // Initialize Views
        tvName = view.findViewById(R.id.tvName)
        tvHome = view.findViewById(R.id.tvHome)
        tvDefault = view.findViewById(R.id.tvDefault)
        tvAddress = view.findViewById(R.id.tvAddress)
        tvMobile = view.findViewById(R.id.tvMobile)
        btnChange = view.findViewById(R.id.btnChange)

        btnChange.setOnClickListener {
            // Since openFragment is in HomeActivity
            (activity as? AddressActivity)?.openFragmentAds(SelectAddressFragment())
        }
        // Activate "Address" step
        (requireActivity() as? AddressActivity)?.updateSteps(2)

        val sp: SharedPreferences =
            requireContext().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
        val userId = sp.getString(ConstantData.KEY_ID, "") ?: ""

        Toast.makeText(requireContext(), "User $userId", Toast.LENGTH_SHORT).show()

        loadAddress(userId)
    }

    private fun loadAddress(userId: String) {
        AddressApi().getDefaultAddress(userId, requireContext()) { output ->

            if (output == null || output.addresses.isNullOrEmpty()) {
                tvName.text = "No Address Found"
                tvHome.text = "Type"
                tvDefault.text = "( Default )"
                tvAddress.text = "Full address here..."
                tvMobile.text = "0000000000"
                return@getDefaultAddress
            }

            val a = output.addresses!![0]

            tvName.text = a.full_name
            tvHome.text = a.address_type
            tvDefault.text = if (a.is_default == "1") "( Default )" else ""
            tvAddress.text =
                "${a.house_no}, ${a.area}, ${a.landmark}, ${a.city}, ${a.state} - ${a.pincode}"
            tvMobile.text = a.phone
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}