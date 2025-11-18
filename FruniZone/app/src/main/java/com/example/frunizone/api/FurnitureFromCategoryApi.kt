package com.example.frunizone.api

import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.ProductFragment
import com.example.frunizone.model.FurnitureOutputModel
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson

class FurnitureFromCategoryApi {
    fun getFurnitureFromCat(fragment: ProductFragment, id: String) {

        val context = fragment.requireActivity()
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)

        val url = ConstantData.SERVER_ADDRESS + ConstantData.FURNITURE_URL1

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                val gson = Gson()
                val output = gson.fromJson(response, FurnitureOutputModel::class.java)

                if (output.status) {
                    fragment.setFurniture(output)
                }
            },
            Response.ErrorListener { error: VolleyError ->
                Toast.makeText(context, "Error: ${error}", Toast.LENGTH_SHORT).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["cid"] = id
                return map
            }
        }

        requestQueue.add(stringRequest)
    }
}