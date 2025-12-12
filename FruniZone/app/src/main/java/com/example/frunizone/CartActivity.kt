package com.example.frunizone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frunizone.adapter.CartAdapter
import com.example.frunizone.api.CouponApi
import com.example.frunizone.api.OrderApi
import com.example.frunizone.model.CouponOutputModel
import com.example.frunizone.model.OrderModel
import com.example.frunizone.model.OrderOutputModel
import com.example.frunizone.util.ConstantData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONException
import org.json.JSONObject

//class CartActivity : AppCompatActivity() {
class CartActivity : AppCompatActivity(){
    private lateinit var prod_total_amount: TextView
    private lateinit var prod_gst: TextView
    private lateinit var prod_amount: TextView
    private lateinit var back: TextView
    private lateinit var tvcopoun: TextView
    private lateinit var rcyl_cart: RecyclerView
//    private lateinit var RadioCOD: RadioButton
//    private lateinit var RadioOnline: RadioButton
    private lateinit var btnConfirm: Button
    private lateinit var btnContinue: Button
    private lateinit var btnApply: Button
    private lateinit var btnshop: Button
    private lateinit var etcode: EditText
    private lateinit var llytCart: ScrollView
    private lateinit var empty: LinearLayout
//    private lateinit var toolbar: Toolbar

    private lateinit var Address1: EditText
    private lateinit var Address2: EditText
    private lateinit var pincode: EditText
    private var orderList = ArrayList<OrderModel>()

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private var uid: String = "0"
    private var Address: String = ""

    private var amt = 0.0
    private var tot = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val back = findViewById<ImageView>(R.id.ivBack)
        val title = findViewById<TextView>(R.id.tvTitle)

        title.text = "Shopping Bag"

        back.setOnClickListener {
            onBackPressed()
        }
        initViews()
        updateSteps(1)


        val sp: SharedPreferences = getSharedPreferences(ConstantData.SP_LOGIN_PREFS, MODE_PRIVATE)
        uid = sp.getString(ConstantData.KEY_ID, "0")!!
/*
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }*/

        btnshop.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btnApply.setOnClickListener {
            val code = etcode.text.toString().trim()
            if (code.isEmpty()) {
                tvcopoun.text = "0%"
                Toast.makeText(this, "Please Enter Code", Toast.LENGTH_SHORT).show()
            } else {
                CouponApi().getCouponDiscount(this, code)
            }
        }
//        back.setOnClickListener {
//            startActivity(Intent(this, HomeActivity::class.java))
//        }
//        openDialog()
//        btnContinue.setOnClickListener {
//            bottomSheetDialog.show()
//        }
        btnContinue.setOnClickListener {
            for (item in orderList) {
                OrderApi().updateOrder(
                    item.id,
                    item.quantity,
                    prod_total_amount.text.toString(),
                    this@CartActivity
                )
            }

            val intent = Intent(this, AddressActivity::class.java)

            intent.putExtra("TOTAL_AMOUNT", prod_total_amount.text.toString())
            intent.putExtra("GST", prod_gst.text.toString())
            intent.putExtra("ITEM_AMOUNT", prod_amount.text.toString())

            intent.putExtra("Coupon", tvcopoun.text.toString())



            startActivity(intent)
        }


        getCartData()
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

    private fun initViews() {
        prod_total_amount = findViewById(R.id.prod_total_amount)
        prod_gst = findViewById(R.id.prod_gst)
        prod_amount = findViewById(R.id.prod_amount)
        rcyl_cart = findViewById(R.id.rcyl_cart)
//        RadioCOD = findViewById(R.id.RadioCOD)
//        RadioOnline = findViewById(R.id.RadioOnline)
        btnContinue = findViewById(R.id.btnContinue)
        etcode = findViewById(R.id.etcode)
        btnApply = findViewById(R.id.btnApply)
        tvcopoun = findViewById(R.id.tvcopoun)
        llytCart = findViewById(R.id.llytCart)
        empty = findViewById(R.id.empty)
        btnshop = findViewById(R.id.btnshop)
//        toolbar = findViewById(R.id.toolbar)
//        back = findViewById(R.id.back)
    }

   /* fun calculateAmount(model: CouponOutputModel) {
//        if (model.coupen.isNotEmpty()) {
        if (!model.coupen.isNullOrEmpty()) {

            val discount = model.coupen[0].coupen_discount?.toDouble()
            tvcopoun.text = "${discount.toInt()}%"
            val calc = tot * (discount / 100)
            val finalAmt = tot - calc
            prod_total_amount.text = Math.floor(finalAmt).toString()
        }
    }*/
   fun calculateAmount(model: CouponOutputModel) {

       val list = model.coupen ?: return   // if null → exit function

       if (list.isNotEmpty()) {

           // Convert discount safely
           val discount = list[0].coupen_discount?.toDoubleOrNull() ?: 0.0

           tvcopoun.text = "${discount.toInt()}%"

           val calc = tot * (discount / 100)
           val finalAmt = tot - calc

           prod_total_amount.text = Math.floor(finalAmt).toString()
       }
   }


//    private fun openDialog() {
//        bottomSheetDialog = BottomSheetDialog(this)
//        val view = layoutInflater.inflate(R.layout.layout_address, null)
//
//        Address1 = view.findViewById(R.id.etAddress1)
//        Address2 = view.findViewById(R.id.etAddress2)
//        pincode = view.findViewById(R.id.pincode)
//        btnConfirm = view.findViewById(R.id.btnConfirm)
//
//       /* btnConfirm.setOnClickListener {
//            val ad1 = Address1.text.toString().trim()
//            val ad2 = Address2.text.toString().trim()
//            val pin = pincode.text.toString().trim()
//
//            when {
//                ad1.isEmpty() -> Toast.makeText(this, "Please enter", Toast.LENGTH_SHORT).show()
//                ad2.isEmpty() -> Toast.makeText(this, "Please enter", Toast.LENGTH_SHORT).show()
//                pin.isEmpty() -> Toast.makeText(this, "Please enter", Toast.LENGTH_SHORT).show()
//                else -> {
//                    Address = ad1 + ad2 + pin
//
//                   /* if (RadioCOD.isChecked) {
//                        OrderApi().confirmOrder(uid, Address, "1", this)
//                    } else {
//                        makePayment()
//                    }*/
//                }
//            }
//        }*/
//
//        bottomSheetDialog.setContentView(view)
//    }

    public fun setCart(orderModel: OrderOutputModel) {
        if (orderModel.order.isEmpty()) {
            llytCart.visibility = View.GONE
            empty.visibility = View.VISIBLE
        } else {
            llytCart.visibility = View.VISIBLE
            empty.visibility = View.GONE

            amt = 0.0
            for (item in orderModel.order) {
//                amt += item.totalAmount.toDouble()
//                amt += item.totalAmount.toDoubleOrNull() ?: 0.0
                amt += (item.total_amount?.toDoubleOrNull() ?: 0.0)

            }

            tot = amt + amt * 0.18

            prod_amount.text = amt.toString()
            prod_gst.text = Math.floor(amt * 0.18).toString()
            prod_total_amount.text = Math.floor(tot).toString()

            val adapter = CartAdapter(
                orderModel.order,
                object : CartAdapter.OnClickListener {
                    override fun onClickPlus(om: OrderModel) {
                        OrderApi().updateOrder(om.id, om.quantity, prod_total_amount.text.toString(), this@CartActivity)
                    }

                    override fun onClickMinus(om: OrderModel) {
                        OrderApi().updateOrder(om.id, om.quantity, prod_total_amount.text.toString(), this@CartActivity)
                    }

                    override fun removeClick(om: OrderModel) {
                        OrderApi().removeOrder(om.id, this@CartActivity)
                    }
                },
                this
            )
            orderList = orderModel.order

            rcyl_cart.layoutManager = LinearLayoutManager(this)
            rcyl_cart.adapter = adapter
        }
    }

    public fun getCartData() {
        if (uid != "0") {
            OrderApi().getOrderPending(uid, this)
        }
    }
//
//    override fun onPaymentSuccess(s: String?) {
//        OrderApi().confirmOrder(uid, Address, "2", this)
//    }

//    override fun onPaymentError(i: Int, s: String?) {
//        Toast.makeText(this, "Payment error", Toast.LENGTH_SHORT).show()
//    }

//    private fun makePayment() {
//        val amount = Math.round(tot.toFloat() * 100)
//
//        val checkout = Checkout()
//        checkout.setKeyID("rzp_test_NE1WFCw0DgsblV")
//        checkout.setImage(R.drawable.logo_landscape)
//
//        val obj = JSONObject()
//        try {
//            obj.put("name", "FURNIZONE")
//            obj.put("description", "Test payment")
//            obj.put("theme.color", "")
//            obj.put("currency", "INR")
//            obj.put("amount", amount)
//            obj.put("prefill.contact", "9157140988")
//            obj.put("prefill.email", "sahildharani890@gmail.com")
//
//            checkout.open(this, obj)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//    }

//    fun done() {
//        startActivity(Intent(this, HurrayActivity::class.java))
//    }
}