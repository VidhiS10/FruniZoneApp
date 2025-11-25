package com.example.frunizone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frunizone.adapter.YourOrdersAdapter
import com.example.frunizone.api.OrderApi
import com.example.frunizone.model.OrderModel
import com.example.frunizone.model.OrderOutputModel
import com.example.frunizone.util.ConstantData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YourOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YourOrderFragment : Fragment() {


    private lateinit var rcylOrder: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var llytCart: LinearLayout
    private lateinit var empty: LinearLayout
    private lateinit var btnShop: Button

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
        return inflater.inflate(R.layout.fragment_your_order, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcylOrder = view.findViewById(R.id.rcylOrder)
//        toolbar = view.findViewById(R.id.toolbar)
        llytCart = view.findViewById(R.id.llytCart)
        empty = view.findViewById(R.id.empty)
        btnShop = view.findViewById(R.id.btnshop)
        val back = view.findViewById<ImageView>(R.id.ivBack)
        val title = view.findViewById<TextView>(R.id.tvTitle)

        title.text = "Your Order"

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        btnShop.setOnClickListener {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
        }
//
//        toolbar.setNavigationOnClickListener {
//            (activity as HomeActivity).openFragment(AccountFragment())
//        }

        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)

        val uid = sharedPreferences.getString(ConstantData.KEY_ID, "0") ?: "0"

        OrderApi().getOrderCompleted(uid, this)
    }

    fun getOrderHistory(orderOutputModel: OrderOutputModel) {
        if (orderOutputModel.order.isEmpty()) {
            llytCart.visibility = View.GONE
            empty.visibility = View.VISIBLE
        } else {
            llytCart.visibility = View.VISIBLE
            empty.visibility = View.GONE

            val adapter = YourOrdersAdapter(
                orderOutputModel.order,
                object : YourOrdersAdapter.OnClickListener {
                    override fun onClickPlus(om: OrderModel) {
                        // no action as per original
                    }

                    override fun onClickMinus(om: OrderModel) {
                        // no action as per original
                    }

                    override fun removeClick(om: OrderModel) {
                        OrderApi().cancelOrder(om.id, requireActivity())
                    }
                },
                requireActivity()
            )

            rcylOrder.layoutManager = LinearLayoutManager(requireActivity())
            rcylOrder.adapter = adapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YourOrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YourOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}