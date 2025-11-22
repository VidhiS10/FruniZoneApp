package com.example.frunizone.api


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.frunizone.CartActivity
import com.example.frunizone.HomeActivity
import com.example.frunizone.YourOrderFragment
import com.example.frunizone.model.OrderModel
import com.example.frunizone.model.OrderOutputModel
import com.example.frunizone.util.ConstantData
import com.google.gson.Gson

class OrderApi {

    fun addOrder(model: OrderModel, activity: Activity) {

        val progress = ProgressDialog(activity)
        progress.setMessage("Its loading....")
        progress.setTitle("Fetching Data")
        progress.show()

        val url = ConstantData.SERVER_ADDRESS + ConstantData.ORDER_URL
        val queue: RequestQueue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                progress.dismiss()
                val output = Gson().fromJson(response, OrderOutputModel::class.java)
                Toast.makeText(activity, output.message, Toast.LENGTH_SHORT).show()
            },
            { error ->
                progress.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["uid"] = model.uid
                map["pid"] = model.pid
                map["pname"] = model.pname
                map["ppic"] = model.ppic
                map["date"] = model.date
                map["time"] = model.time
                map["amount"] = model.amount
                map["total_amount"] = model.total_amount
                map["quantity"] = model.quantity + "1"
                map["status"] = model.status
                map["is_wishlist"] = model.is_wishlist
                return map
            }
        }

        queue.add(request)
    }

    fun removeOrder(id: String, activity: Activity) {

        val progress = ProgressDialog(activity)
        progress.setMessage("Its loading....")
        progress.setTitle("Fetching Data")
        progress.show()

        val url = ConstantData.SERVER_ADDRESS + ConstantData.REMOVE_ORDER
        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                progress.dismiss()
                val output = Gson().fromJson(response, OrderOutputModel::class.java)
                Toast.makeText(activity, output.message, Toast.LENGTH_SHORT).show()

                if (output.status) {
                    (activity as CartActivity).getCartData()
                }
            },
            { error ->
                progress.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): Map<String, String> {
                return hashMapOf("id" to id)
            }
        }

        queue.add(request)
    }

    fun cancelOrder(id: String, activity: Activity) {

        val progress = ProgressDialog(activity)
        progress.setMessage("Its loading....")
        progress.setTitle("Fetching Data")
        progress.show()

        val url = ConstantData.SERVER_ADDRESS + ConstantData.CANCLE_ORDER
        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                progress.dismiss()
                val output = Gson().fromJson(response, OrderOutputModel::class.java)
                Toast.makeText(activity, output.message, Toast.LENGTH_SHORT).show()

                if (output.status) {
                    val intent = Intent(activity, HomeActivity::class.java)
                    activity.startActivity(intent)
                }
            },
            { error ->
                progress.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): Map<String, String> {
                return hashMapOf("id" to id)
            }
        }

        queue.add(request)
    }

    fun confirmOrder(uid: String, address: String, status: String, activity: Activity) {

        val progress = ProgressDialog(activity)
        progress.setMessage("Its loading....")
        progress.setTitle("Fetching Data")
        progress.show()

        val url = ConstantData.SERVER_ADDRESS + ConstantData.CONFIRM_ORDER
        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                progress.dismiss()
                val output = Gson().fromJson(response, OrderOutputModel::class.java)
                Toast.makeText(activity, output.message, Toast.LENGTH_SHORT).show()

                if (output.status) {
                    val cart = activity as CartActivity
                    cart.getCartData()
                    cart.done()
                }
            },
            { error ->
                progress.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map["uid"] = uid
                map["address"] = address
                map["status"] = status
                return map
            }
        }

        queue.add(request)
    }

    fun updateOrder(id: String, qty: String, amount: String?, activity: Activity) {

        val progress = ProgressDialog(activity)
        progress.setMessage("Its loading....")
        progress.setTitle("Fetching Data")
        progress.show()

        val url = ConstantData.SERVER_ADDRESS + ConstantData.UPDATE_ORDER
        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                progress.dismiss()
                val output = Gson().fromJson(response, OrderOutputModel::class.java)

                if (output.status) {
                    (activity as CartActivity).getCartData()
                }
                Toast.makeText(activity, output.message, Toast.LENGTH_SHORT).show()
            },
            { error ->
                progress.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): Map<String, String> {
                return hashMapOf(
                    "id" to id,
//                    "amount" to amount,
                    "amount" to (amount ?: "0"),

                    "quantity" to qty
                )
            }
        }

        queue.add(request)
    }

    fun getOrderPending(uid: String, activity: Activity) {

        val progress = ProgressDialog(activity)
        progress.setMessage("Its loading....")
        progress.setTitle("Fetching Data")
        progress.show()

        val url = ConstantData.SERVER_ADDRESS + ConstantData.GET_PENDING_ORDER_URL
        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                progress.dismiss()
                val output = Gson().fromJson(response, OrderOutputModel::class.java)
//                Toast.makeText(activity, output.message, Toast.LENGTH_SHORT).show()

                if (output.status) {
                    (activity as CartActivity).setCart(output)
                }
            },
            { error ->
                progress.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): Map<String, String> {
                return hashMapOf("uid" to uid)
            }
        }

        queue.add(request)
    }

    /*
    fun getOrderCompleted(uid: String, fragment: YourOrderFragment) {

        val activity = fragment.requireActivity()
        val progress = ProgressDialog(activity)
        progress.setMessage("Its loading....")
        progress.setTitle("Fetching Data")
        progress.show()

        val url = ConstantData.SERVER_ADDRESS + ConstantData.COMPLETED_ORDER
        val queue = Volley.newRequestQueue(activity)

        val request = object : StringRequest(Request.Method.POST, url,
            { response ->
                progress.dismiss()
                val output = Gson().fromJson(response, OrderOutputModel::class.java)

                Toast.makeText(activity, output.message, Toast.LENGTH_SHORT).show()

                if (output.status) {
                    fragment.getOrderHistory(output)
                }
            },
            { error ->
                progress.dismiss()
                Toast.makeText(activity, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }) {

            override fun getParams(): Map<String, String> {
                return hashMapOf("uid" to uid)
            }
        }

        queue.add(request)
    }*/
}
