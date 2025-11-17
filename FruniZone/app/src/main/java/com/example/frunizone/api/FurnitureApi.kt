package com.example.frunizone.api


import android.app.Activity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.AllProductFragment
import com.example.frunizone.HomeFragment
import com.example.frunizone.SearchActivity
import com.example.frunizone.model.FurnitureOutputModel
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson

class FurnitureApi {

    fun getFurniture(homeFragment: HomeFragment) {
        val queue: RequestQueue = Volley.newRequestQueue(homeFragment.activity)
        val url = ConstantData.SERVER_ADDRESS + ConstantData.FURNITURE_URL

        val request = StringRequest(Request.Method.GET, url, { response ->
            val gson = Gson()
            val output = gson.fromJson(response, FurnitureOutputModel::class.java)

            if (output.status) {
                homeFragment.setFurniture(output)
            }
        }, { error ->
            Toast.makeText(homeFragment.activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)
    }

    fun getFurnitureAll(fragment: AllProductFragment) {
        val queue = Volley.newRequestQueue(fragment.activity)
        val url = ConstantData.SERVER_ADDRESS + ConstantData.FURNITURE_URL

        val request = StringRequest(Request.Method.GET, url, { response ->
            val gson = Gson()
            val output = gson.fromJson(response, FurnitureOutputModel::class.java)

            if (output.status) {
                fragment.setFurniture(output)
            }
        }, { error ->
            Toast.makeText(fragment.activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)
    }

    fun getFurnitureBasedOnSearch(activity: Activity, search: String) {
        val queue = Volley.newRequestQueue(activity)
        val url = ConstantData.SERVER_ADDRESS + ConstantData.SEARCH_API

        val request = object : StringRequest(
            Method.POST, url,
            { response ->
                val gson = Gson()
                val output = gson.fromJson(response, FurnitureOutputModel::class.java)

                if (output.status) {
                    (activity as SearchActivity).setFurniture(output)
                }
            },
            { error ->
                Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map["search"] = search
                return map
            }
        }

        queue.add(request)
    }

}