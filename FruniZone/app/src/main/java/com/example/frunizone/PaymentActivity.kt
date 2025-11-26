package com.example.frunizone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.frunizone.api.OrderApi
import com.example.frunizone.util.ConstantData
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    private var amount = "0"
    private var gst = "0"
    private var total = "0"
    private var coupon = "0"
    private var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        updateSteps(3)
        val back = findViewById<ImageView>(R.id.ivBack)
        val title = findViewById<TextView>(R.id.tvTitle)

        title.text = "Payment"

        back.setOnClickListener {
            onBackPressed()
        }

        // Receive data from AddressFragment
        amount = intent.getStringExtra("ITEM_AMOUNT") ?: "0"
        gst = intent.getStringExtra("GST") ?: "0"
        total = intent.getStringExtra("TOTAL_AMOUNT") ?: "0"
        coupon = intent.getStringExtra("COUPON") ?: "0"
        address = intent.getStringExtra("ADDRESS") ?: ""

        val rbCOD = findViewById<RadioButton>(R.id.rbCOD)
        val rbOnline = findViewById<RadioButton>(R.id.rbOnline)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        findViewById<TextView>(R.id.tvItemAmount).text = "₹$amount"
        findViewById<TextView>(R.id.tvGst).text = "₹$gst"
        findViewById<TextView>(R.id.tvCoupon).text = coupon
        findViewById<TextView>(R.id.tvTotal).text = total

        btnContinue.setOnClickListener {
            when {
                rbCOD.isChecked -> placeCOD()
                rbOnline.isChecked -> makeOnlinePayment()
                else -> Toast.makeText(this,"Select Payment Method",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun placeCOD() {
        val uid = getSharedPreferences(ConstantData.SP_LOGIN_PREFS,MODE_PRIVATE)
            .getString(ConstantData.KEY_ID,"0")!!

        OrderApi().confirmOrder(uid,address,"1",this)
        startActivity(Intent(this,HurrayActivity::class.java))
    }
    fun updateSteps(step: Int) {
        val bag = findViewById<View>(R.id.stepBagCircle)
        val address = findViewById<View>(R.id.stepAddressCircle)
        val payment = findViewById<View>(R.id.stepPaymentCircle)

        when (step) {
            1 -> {
                bag.setBackgroundResource(R.drawable.progress_selected_circle)
                address.setBackgroundResource(R.drawable.progress_unselected_circle)
                payment.setBackgroundResource(R.drawable.progress_unselected_circle)
            }
            2 -> {
                bag.setBackgroundResource(R.drawable.progress_selected_circle)
                address.setBackgroundResource(R.drawable.progress_selected_circle)
                payment.setBackgroundResource(R.drawable.progress_unselected_circle)
            }
            3 -> {
                bag.setBackgroundResource(R.drawable.progress_selected_circle)
                address.setBackgroundResource(R.drawable.progress_selected_circle)
                payment.setBackgroundResource(R.drawable.progress_selected_circle)
            }
        }
    }


    private fun makeOnlinePayment() {
        Checkout.preload(this)
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_NE1WFCw0DgsblV")

        try {
            val options = JSONObject().apply {
                put("name", "FurniZone")
                put("description", "Order Payment")
                put("currency", "INR")
                put("amount", (total.toDouble() * 100).toInt())  // Amount in paise

                // Enable UPI, Card, Wallets, Netbanking
                put("method", JSONObject().apply {
                    put("upi", true)          // UPI (Google Pay / PhonePe / Paytm UPI)
                    put("card", true)         // Cards
                    put("netbanking", true)   // Netbanking
                    put("wallet", true)       // Wallets
                })

                // Theme customization (Optional)
                put("theme", JSONObject().apply {
                    put("color", "#EC6B46")
                })

                // Prefilled User Info
                put("prefill", JSONObject().apply {
                    put("contact", "9157140988")
                    put("email", "sahildharani890@gmail.com")
                })
            }

            checkout.open(this,options)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
        override fun onPaymentSuccess(paymentId: String?) {
            Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()

            val uid = getSharedPreferences(ConstantData.SP_LOGIN_PREFS, MODE_PRIVATE)
                .getString(ConstantData.KEY_ID, "0")!!

            OrderApi().confirmOrder(uid, address, "1", this)
            startActivity(Intent(this, HurrayActivity::class.java))
        }


    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Failed ",Toast.LENGTH_SHORT).show()
    }
}
