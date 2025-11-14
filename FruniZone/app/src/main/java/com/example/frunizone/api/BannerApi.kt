package com.example.frunizone.api

import android.app.ProgressDialog
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.HomeFragment
import com.example.frunizone.model.BannerOutputModel
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson

class BannerApi {

    fun getBanner(homeFragment: HomeFragment) {
        val progressDialog = ProgressDialog(homeFragment.activity)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val requestQueue: RequestQueue = Volley.newRequestQueue(homeFragment.activity)
        val url = ConstantData.SERVER_ADDRESS + ConstantData.BANNER_URL

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                progressDialog.dismiss()
                val gson = Gson()
                val bannerOutput = gson.fromJson(response, BannerOutputModel::class.java)
                if (bannerOutput.status == true) {
                    homeFragment.setBanner(bannerOutput)
                }
            },
            Response.ErrorListener { error ->
                progressDialog.dismiss()
                Toast.makeText(homeFragment.activity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(stringRequest)
    }


}