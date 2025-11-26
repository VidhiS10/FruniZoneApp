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
fun getAddressList(userId: String, activity: Activity, callback: (AddressOutputModel?) -> Unit) {

    val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_GET
    val queue = Volley.newRequestQueue(activity)

    val request = object : StringRequest(
        Method.POST, url,
        { response ->
            val output = Gson().fromJson(response, AddressOutputModel::class.java)
            callback(output)
        },
        { error ->
            callback(null)
        }
    ) {
        override fun getParams(): Map<String, String> {
            return hashMapOf("user_id" to userId)
        }
    }

    queue.add(request)
}
    fun deleteAddress(addressId: String, activity: Activity, callback: (Boolean) -> Unit) {
        val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_DELETE
        val req = object : StringRequest(Method.POST, url,
            { res -> callback(true) },
            { callback(false) }
        ) {
            override fun getParams() = mapOf("id" to addressId)
        }
        Volley.newRequestQueue(activity).add(req)
    }
    fun updateAddress(model: AddressModel, activity: Activity, callback:(Boolean)->Unit){
        val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_UPDATE

        val req = object : StringRequest(Method.POST,url,
            { response ->
                val obj = JSONObject(response)
                callback(obj.getBoolean("status"))   // now correctly checks
            },
            { callback(false) }
        ){
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    "id" to model.id.toString(),           // <- must send correct ID
                    "full_name" to model.full_name!!,
                    "phone" to model.phone!!,
                    "house_no" to model.house_no!!,
                    "area" to model.area!!,
                    "landmark" to model.landmark!!,
                    "city" to model.city!!,
                    "state" to model.state!!,
                    "pincode" to model.pincode!!,
                    "address_type" to model.address_type!!,
                    "is_default" to model.is_default.toString()
                )
            }
        }
        Volley.newRequestQueue(activity).add(req)
    }

    fun setDefaultAddress(userId: String, addressId: String, activity: Activity, callback: (Boolean) -> Unit) {
        val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_SET_DEFAULT
        val req = object : StringRequest(Method.POST, url,
            { res -> callback(true) },
            { callback(false) }
        ) {
            override fun getParams() = mapOf("user_id" to userId, "id" to addressId)
        }
        Volley.newRequestQueue(activity).add(req)
    }


//    fun deleteAddress(addressId:String, activity:Activity, callback:(Boolean)->Unit){
//
//        val url = ConstantData.SERVER_ADDRESS + "address_delete.php"
//        val queue = Volley.newRequestQueue(activity)
//
//        val req = object : StringRequest(Method.POST,url,
//            { res ->
//                val obj = JSONObject(res)
//                callback(obj.getBoolean("status"))
//            },
//            { callback(false) }
//        ){
//            override fun getParams(): MutableMap<String, String> {
//                return hashMapOf("id" to addressId)
//            }
//        }
//        queue.add(req)
//    }
//    fun setDefaultAddress(userId:String, addressId:String, activity:Activity, callback:(Boolean)->Unit){
//
//        val url = ConstantData.SERVER_ADDRESS + "address_set_default.php"
//        val queue = Volley.newRequestQueue(activity)
//
//        val req = object : StringRequest(Method.POST,url,
//            { res ->
//                val obj = JSONObject(res)
//                callback(obj.getBoolean("status"))
//            },
//            { callback(false) }
//        ){
//            override fun getParams(): MutableMap<String, String> {
//                return hashMapOf(
//                    "user_id" to userId,
//                    "id" to addressId
//                )
//            }
//        }
//        queue.add(req)
//    }

    fun getSingleAddress(addressId: String, activity: Activity, callback: (AddressOutputModel?) -> Unit) {

        val url = ConstantData.SERVER_ADDRESS + ConstantData.ADDRESS_GET_SINGLE
        val queue = Volley.newRequestQueue(activity)

        val req = object : StringRequest(Method.POST, url,
            { res ->
                val output = Gson().fromJson(res, AddressOutputModel::class.java)
                callback(output)
            },
            {
                callback(null)
            }
        ) {
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["id"] = addressId
                return map
            }
        }

        queue.add(req)
    }


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
