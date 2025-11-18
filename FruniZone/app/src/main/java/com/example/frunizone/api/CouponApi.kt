package com.example.frunizone.api



import android.app.ProgressDialog
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.CartActivity
import com.example.frunizone.OffersFragment
import com.example.frunizone.model.CouponOutputModel
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson

class CouponApi {

    fun getCoupon(fragment: OffersFragment) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(fragment.activity)
        val url = ConstantData.SERVER_ADDRESS + ConstantData.COUPON_URL

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val model = Gson().fromJson(response, CouponOutputModel::class.java)
                if (model.status == true) {
                    fragment.setCoupon(model)
                }
            },
            { error ->
                Toast.makeText(fragment.activity, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(stringRequest)
    }
/*
    fun getCouponDiscount(activity: CartActivity, ccode: String) {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val requestQueue = Volley.newRequestQueue(activity)
        val url = ConstantData.SERVER_ADDRESS + ConstantData.COUPON_DISCOUNT_URL

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                progressDialog.dismiss()

                val model = Gson().fromJson(response, CouponOutputModel::class.java)
                if (model.status == true) {
                    activity.calculateAmount(model)
                }
            },
            { error ->
                progressDialog.dismiss()
                Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["ccode"] = ccode
                return map
            }
        }

        requestQueue.add(stringRequest)
    }*/
}
