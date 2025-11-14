package com.example.frunizone.api
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.CategoriesFragment
import com.example.frunizone.HomeFragment
import com.example.frunizone.model.CategoryOutputModel
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson
class CategoryApi {
    fun getCategory(homeFragment: HomeFragment) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(homeFragment.requireActivity())
        val url = ConstantData.SERVER_ADDRESS + ConstantData.CATEGORY_URL

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val categoryOutputModel = gson.fromJson(response, CategoryOutputModel::class.java)
                if (categoryOutputModel.status ==true) {
                    homeFragment.setCategory(categoryOutputModel)
                }
            },
            { error ->
                Toast.makeText(homeFragment.requireActivity(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(stringRequest)
    }

    fun getCategoryAll(categoriesFragment: CategoriesFragment) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(categoriesFragment.requireActivity())
        val url = ConstantData.SERVER_ADDRESS + ConstantData.CATEGORY_URL

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val gson = Gson()
                val categoryOutputModel = gson.fromJson(response, CategoryOutputModel::class.java)
                if (categoryOutputModel.status==true) {
                    categoriesFragment.setCategory(categoryOutputModel)
                }
            },
            { error ->
                Toast.makeText(categoriesFragment.requireActivity(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(stringRequest)
    }
}