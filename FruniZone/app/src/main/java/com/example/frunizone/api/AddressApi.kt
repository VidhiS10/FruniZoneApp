package com.example.frunizone.api

import android.app.Activity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.model.AddressModel
import com.example.frunizone.model.AddressOutputModel
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson

class AddressApi {

    fun addAddress(model: AddressModel, activity: Activity, callback: (Boolean) -> Unit) {

//        val url = ConstantData.SERVER_ADDRESS + "address_add.php"
        val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_ADD
        val queue: RequestQueue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                val gson = Gson()
                val output = gson.fromJson(response, AddressOutputModel::class.java)

                Toast.makeText(activity, output.message, Toast.LENGTH_SHORT).show()
                callback(output.status)
            },
            { error ->
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        ) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["user_id"] = model.user_id ?: ""
                map["full_name"] = model.full_name?: ""
                map["phone"] = model.phone?: ""
                map["house_no"] = model.house_no?: ""
                map["area"] = model.area?: ""
                map["landmark"] = model.landmark?: ""
                map["city"] = model.city?: ""
                map["state"] = model.state?: ""
                map["pincode"] = model.pincode?: ""
                map["address_type"] = model.address_type?: ""
                map["is_default"] = model.is_default?: ""
                return map
            }
        }

        queue.add(request)
    }

}
