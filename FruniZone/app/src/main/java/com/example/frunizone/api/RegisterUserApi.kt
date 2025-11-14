package com.example.frunizone.api

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.HomeActivity
import com.example.frunizone.LoginActivity
import com.example.frunizone.model.PersonOutputModel
import com.example.frunizone.model.PersonModel
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson

class RegisterUserApi {

    fun registerUser(model: PersonModel, activity: Activity) {
        /* val progressDialog = ProgressDialog(activity).apply {
             setMessage("Its loading....")
             setTitle("Fetching Data")
             show()
         }*/

        val url = ConstantData.SERVER_ADDRESS + ConstantData.REGISTER_URL
        val requestQueue: RequestQueue = Volley.newRequestQueue(activity)

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                // progressDialog.dismiss()
                val gson = Gson()
                val personOutputModel = gson.fromJson(response, PersonOutputModel::class.java)
                if (personOutputModel.status) {
                    val sp: SharedPreferences =
                        activity.getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
                    val ed = sp.edit()

                    personOutputModel.user?.let {
                        ed.putString(ConstantData.KEY_ID, it.user_id)
                        ed.putString(ConstantData.KEY_USERNAME, it.user_name)
                        ed.putString(ConstantData.KEY_EMAIL, it.user_email)
                        ed.putString(ConstantData.KEY_PHONE, it.user_phone)
                        ed.putString(ConstantData.KEY_PIC, it.user_pic)
                        ed.putString(ConstantData.KEY_PWD, it.user_password)
                    }

                    ed.apply()
                    Toast.makeText(activity, personOutputModel.message, Toast.LENGTH_SHORT).show()
                    activity.startActivity(Intent(activity, LoginActivity::class.java))
                } else {
                    Toast.makeText(activity, personOutputModel.message, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                //  progressDialog.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["user_name"] = model.user_name ?: ""
                map["user_password"] = model.user_password ?: ""
                map["user_email"] = model.user_email ?: ""
                map["user_phone"] = model.user_phone ?: ""
                return map
            }
        }
        requestQueue.add(stringRequest)
    }


//    fun login(model: PersonModel, activity: Activity) {
//        val progressDialog = ProgressDialog(activity).apply {
//            setMessage("Its loading....")
//            setTitle("Fetching Data")
//            show()
//        }
//
//        val url = ConstantData.SERVER_ADDRESS + ConstantData.LOGIN_URL
//        val requestQueue: RequestQueue = Volley.newRequestQueue(activity)
//
//        val stringRequest = object : StringRequest(
//            Method.POST, url,
//            Response.Listener { response ->
//                progressDialog.dismiss()
//                val gson = Gson()
//                val personOutputModel = gson.fromJson(response, PersonOutputModel::class.java)
////                if (personOutputModel.status) {
////                    val sp: SharedPreferences =
////                        activity.getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
////                    val ed = sp.edit()
////
////                    personOutputModel.user?.let {
////                        ed.putString(ConstantData.KEY_ID, it.user_id)
////                        ed.putString(ConstantData.KEY_USERNAME, it.user_name)
////                        ed.putString(ConstantData.KEY_EMAIL, it.user_email)
////                        ed.putString(ConstantData.KEY_PHONE, it.user_phone)
////                        ed.putString(ConstantData.KEY_PIC, it.user_pic)
////                        ed.putBoolean("isLoggedIn", true)
////                    }
////
////                    ed.apply()
////                   // Toast.makeText(activity, personOutputModel.message, Toast.LENGTH_SHORT).show()
////                    activity.startActivity(Intent(activity, HomeActivity::class.java))
////                }
////                else {
////                    Toast.makeText(activity,"Invalid Phone or Password", Toast.LENGTH_SHORT).show()
////                }
//                if (personOutputModel.status) {
//
//                    // fetch full user details BEFORE returning control to activity
//                    fetchUserDetails(model.user_phone ?: "", activity) {
//                        // Callback only; NO redirect here
//                        loginCallback?.invoke(true)
//                    }
//
//                } else {
//                    Toast.makeText(activity,"Invalid Phone or Password", Toast.LENGTH_SHORT).show()
//                    loginCallback?.invoke(false)
//                }
//            },
//            Response.ErrorListener { error: VolleyError ->
//                progressDialog.dismiss()
//                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
//            }) {
//
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String> {
//                val map = HashMap<String, String>()
//                map["user_password"] = model.user_password ?: ""
//                map["user_phone"] = model.user_phone ?: ""
//                return map
//            }
//        }
//
//        requestQueue.add(stringRequest)
//    }

    fun login(model: PersonModel, activity: Activity, loginCallback: (Boolean) -> Unit) {
        val progressDialog = ProgressDialog(activity).apply {
            setMessage("Its loading....")
            setTitle("Fetching Data")
            show()
        }

        val url = ConstantData.SERVER_ADDRESS + ConstantData.LOGIN_URL
        val requestQueue: RequestQueue = Volley.newRequestQueue(activity)

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                progressDialog.dismiss()

                val gson = Gson()
                val personOutputModel = gson.fromJson(response, PersonOutputModel::class.java)

                if (personOutputModel.status) {

                    fetchUserDetails(model.user_phone ?: "", activity) {
                        loginCallback(true)
                    }

                } else {
                    Toast.makeText(activity, "Invalid Phone or Password", Toast.LENGTH_SHORT).show()
                    loginCallback(false)
                }
            },
            Response.ErrorListener { error: VolleyError ->
                progressDialog.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()

                loginCallback(false)
            }) {

            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["user_password"] = model.user_password ?: ""
                map["user_phone"] = model.user_phone ?: ""
                return map
            }
        }

        requestQueue.add(stringRequest)
    }

    fun fetchUserDetails(phone: String, activity: Activity, onComplete: () -> Unit) {
        val url = ConstantData.SERVER_ADDRESS + "furniture/api/get_user_details.php"

        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val gson = Gson()
                val output = gson.fromJson(response, PersonOutputModel::class.java)

                if (output.status && output.user != null) {

                    val sp = activity.getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
                    val ed = sp.edit()

                    ed.putString(ConstantData.KEY_ID, output.user?.user_id)
                    ed.putString(ConstantData.KEY_USERNAME, output.user?.user_name)
                    ed.putString(ConstantData.KEY_EMAIL, output.user?.user_email)
                    ed.putString(ConstantData.KEY_PHONE, output.user?.user_phone)
                    ed.putString(ConstantData.KEY_PIC, output.user?.user_pic)
                    ed.putBoolean("isLoggedIn", true)

                    ed.apply()
                }

                onComplete()
            },
            Response.ErrorListener {
                Toast.makeText(activity, "Failed to fetch full details", Toast.LENGTH_SHORT).show()
                onComplete()
            }
        ) {
            override fun getParams(): Map<String, String> {
                return hashMapOf("user_phone" to phone)
            }
        }

        queue.add(request)
    }

}