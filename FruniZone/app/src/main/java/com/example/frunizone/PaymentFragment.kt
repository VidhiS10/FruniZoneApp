package com.example.frunizone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.example.frunizone.api.OrderApi
import com.example.frunizone.util.ConstantData
import com.razorpay.Checkout
import org.json.JSONException
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentFragment : Fragment() {
    private var amount = "0"
    private var gst = "0"
    private var total = "0"
    private var coupon = "0"

    private var address = ""
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amount = it.getString("ITEM_AMOUNT","")
            gst = it.getString("GST","")
            total = it.getString("TOTAL_AMOUNT","")
            coupon = it.getString("COUPON","")
            address = it.getString("ADDRESS","")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Activate "Address" step
        (requireActivity() as? AddressActivity)?.updateSteps(3)
        val back = view.findViewById<ImageView>(R.id.ivBack)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        // Get views
        val rbCOD = view.findViewById<RadioButton>(R.id.rbCOD)
        val rbOnline = view.findViewById<RadioButton>(R.id.rbOnline)
        val btnContinue = view.findViewById<Button>(R.id.btnContinue)

        val tvAmount = view.findViewById<TextView>(R.id.tvItemAmount)
        val tvCoupon = view.findViewById<TextView>(R.id.tvCoupon)
        val tvGST = view.findViewById<TextView>(R.id.tvGst)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
        title.text = "Select Payment"

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        // Display values
        tvAmount.text = "₹$amount"
        tvCoupon.text = coupon
        tvGST.text = "₹$gst"
        tvTotal.text = "₹$total"
        btnContinue.setOnClickListener {
            if (rbCOD.isChecked) placeCOD()
            else if (rbOnline.isChecked) makeOnlinePayment()
            else Toast.makeText(requireContext(),"Select Payment Method",Toast.LENGTH_SHORT).show()
        }
    }
    private fun placeCOD() {
        val sp = requireContext().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, android.content.Context.MODE_PRIVATE)
        val uid = sp.getString(ConstantData.KEY_ID,"0")!!
        OrderApi().confirmOrder(uid, address, "1", requireActivity())
        startActivity(Intent(requireContext(), HurrayActivity::class.java))
    }

    private fun makeOnlinePayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_NE1WFCw0DgsblV")

        val obj = JSONObject()
        obj.put("name", "FurniZone")
        obj.put("description", "Order Payment")
        obj.put("currency", "INR")
        obj.put("amount",(total.toDouble()*100).toInt())

        checkout.open(requireActivity(),obj)
    }

    override fun onPaymentSuccess(p0: String?) {
        val sp = requireContext().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, android.content.Context.MODE_PRIVATE)
        val uid = sp.getString(ConstantData.KEY_ID,"0")!!
        OrderApi().confirmOrder(uid,address,"1",requireActivity())
        startActivity(Intent(requireContext(), HurrayActivity::class.java))
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(requireContext(),"Payment Failed",Toast.LENGTH_SHORT).show()
    }

    fun done() {
//        startActivity(Intent(this, HurrayActivity::class.java))
    }*/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PaymentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}