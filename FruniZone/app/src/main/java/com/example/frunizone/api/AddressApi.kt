package com.example.frunizone.api

import android.app.Activity
import android.content.Context
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
import org.json.JSONObject

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
//
//    fun getDefaultAddress(
//        context: Context,
//        userId: String,
//        callback: (JSONObject?) -> Unit
//    ) {
//        val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_GET_DEFAULT
//
//        val req = object : StringRequest(Method.POST, url,
//            { response ->
//                val obj = JSONObject(response)
//                if (obj.getBoolean("status")) {
//                    callback(obj.getJSONObject("data"))
//                } else {
//                    callback(null)
//                }
//            },
//            { callback(null) }
//        ) {
//            override fun getParams(): MutableMap<String, String> {
//                val map = HashMap<String, String>()
//                map["user_id"] = userId
//                return map
//            }
//        }
//
//        Volley.newRequestQueue(context).add(req)
//    }
//fun getDefaultAddress(userId: String, activity: Activity, callback: (AddressOutputModel?) -> Unit) {
//
//    val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_GET_DEFAULT
//    val queue = Volley.newRequestQueue(activity)
//
//    val request = object : StringRequest(Method.POST, url,
//        { response ->
//            val output = Gson().fromJson(response, AddressOutputModel::class.java)
//            callback(output)
//        },
//        { error ->
//            Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
//            callback(null)
//        }
//    ) {
//        override fun getParams(): Map<String, String> {
//            val map = HashMap<String, String>()
//            map["user_id"] = userId
//            return map
//        }
//    }
//
//    queue.add(request)
//}
//

    fun getDefaultAddress(
        userId: String,
        context: Context,
        callback: (AddressOutputModel?) -> Unit
    ) {

        val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_GET_DEFAULT
        val queue = Volley.newRequestQueue(context)

        val request = object : StringRequest(Method.POST, url,
            { response ->
                val output = Gson().fromJson(response, AddressOutputModel::class.java)
                callback(output)
            },
            { error ->
                Toast.makeText(context, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                callback(null)
            }
        ) {
            override fun getParams(): Map<String, String> {
                return mapOf("user_id" to userId)
            }
        }

        queue.add(request)
    }

}
