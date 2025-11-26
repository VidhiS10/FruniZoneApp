package com.example.frunizone

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.frunizone.adapter.AddressAdapter
import com.example.frunizone.api.AddressApi
import com.example.frunizone.model.AddressModel
import com.example.frunizone.util.ConstantData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectAddressFragment : Fragment() {
    private lateinit var userId: String

    private lateinit var recycler: RecyclerView
    private lateinit var addressAdapter: AddressAdapter
    private var addressList = ArrayList<AddressModel>()
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
        return inflater.inflate(R.layout.fragment_select_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        val btnAdd = view.findViewById<Button>(R.id.btnaddAds)
        val btnConfirm = view.findViewById<Button>(R.id.btn_Confirm)

//  OPEN AddAddressFragment
        btnAdd.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, AddAddressFragment())
                .addToBackStack(null)
                .commit()
        }

//  OPEN AddressFragment
        btnConfirm.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, AddressFragment())
                .addToBackStack(null)
                .commit()
        }
        val back = view.findViewById<ImageView>(R.id.ivBack)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        title.text = "Select Address"

        back.setOnClickListener { requireActivity().onBackPressed() }

        recycler = view.findViewById(R.id.rcyl_address)

        val sp = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
        userId = sp.getString(ConstantData.KEY_ID, "") ?: ""

        if (userId.isEmpty()) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        loadAddressesAgain()

//        title.text = "Address"
//
//        back.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
//        (requireActivity() as? CartActivity)?.updateSteps(2)
//        val recycler = view.findViewById<RecyclerView>(R.id.rcyl_address)
//
//        val sp = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
//        val userId = sp.getString(ConstantData.KEY_ID, "") ?: ""
//
//        AddressApi().getAddressList(userId, requireActivity()) { output ->
//
//            if (output != null && output.addresses != null) {
//
//                val adapter = AddressAdapter(
//                    output.addresses!!,
//                    onEdit = { selected ->
//
//                            val editFrag = EditAddressFragment()
//
//                            val b = Bundle()
//                            b.putString("id", selected.id)  // pass id
//                            editFrag.arguments = b
//
//                            parentFragmentManager.beginTransaction()
//                                .replace(R.id.main, editFrag)
//                                .addToBackStack(null)
//                                .commit()
//
//                    },
//                    onRemove = { selected ->
//                        AddressApi().deleteAddress(selected.id, requireActivity()){ success ->
//                            if(success){
//                                Toast.makeText(requireContext(),"Deleted",Toast.LENGTH_SHORT).show()
//                                loadAddressesAgain()   //refresh list (create function)
//                            }
//                        }
//                    },
//                    onSelect = { selected ->
//                        // user selected an address
//                        AddressApi().setDefaultAddress(userId, selected.id, requireActivity()){ success ->
//                            if(success){
//                                Toast.makeText(requireContext(),"Default Updated",Toast.LENGTH_SHORT).show()
//                                loadAddressesAgain()
//                            }
//                        }
//                    }
//                )
//
//                recycler.adapter = adapter
//            }
//        }

    }
    private fun loadAddressesAgain() {

        AddressApi().getAddressList(userId, requireActivity()) { output ->

            if (output == null || output.addresses.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "No Address Found", Toast.LENGTH_SHORT).show()
                return@getAddressList
            }

            addressList = output.addresses!!
            addressAdapter = AddressAdapter(
                addressList,

                // ------- EDIT BUTTON -------
                onEdit = { selected ->
                    val frag = EditAddressFragment()
                    val b = Bundle()
                    b.putString("id", selected.id)
                    frag.arguments = b

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main, frag)
                        .addToBackStack(null)
                        .commit()
                },

                // ------- REMOVE BUTTON -------
                onRemove = { selected ->
                    AddressApi().deleteAddress(selected.id ?: "", requireActivity()) { ok ->
                        if (ok) loadAddressesAgain() // refresh list
                    }
                },

                // ------- SELECT RADIO -------
                onSelect = { selected ->
                    AddressApi().setDefaultAddress(userId, selected.id!!, requireActivity()) { ok ->
                        if (ok) loadAddressesAgain() // refresh list
                    }
                }
            )
            recycler.adapter = addressAdapter
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}