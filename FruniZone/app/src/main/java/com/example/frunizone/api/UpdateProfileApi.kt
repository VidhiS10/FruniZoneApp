package com.example.frunizone.api

import android.app.Activity
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.model.UpdateResponse
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson

class UpdateProfileApi {

    fun updateProfile(name: String, email: String, activity: Activity, callback: (Boolean) -> Unit) {

        val url = ConstantData.SERVER_ADDRESS + ConstantData.UPDATE_PROFILE_URL

        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val gson = Gson()
                val output = gson.fromJson(response, UpdateResponse::class.java)

                if (output.success) {
                    callback(true)
                } else {
                    callback(false)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["user_name"] = name
                map["user_email"] = email
                return map
            }
        }

        queue.add(request)
    }
}
