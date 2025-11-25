package com.example.frunizone

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.frunizone.api.AddressApi
import com.example.frunizone.util.ConstantData

class AddressActivity : AppCompatActivity() {
    // Declare at top (GLOBAL in this Activity)
//    private lateinit var tvName: TextView
//    private lateinit var tvHome: TextView
//    private lateinit var tvDefault: TextView
//    private lateinit var tvAddress: TextView
//    private lateinit var tvMobile: TextView
//    private lateinit var btnChange: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_address)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        openFragmentAds(AddressFragment())



//        val back = findViewById<ImageView>(R.id.ivBack)
//        val title = findViewById<TextView>(R.id.tvTitle)
//
//        title.text = "Address"
//
//        back.setOnClickListener {
//            onBackPressed()
//        }
//        // Initialize Views
//        tvName = findViewById(R.id.tvName)
//        tvHome = findViewById(R.id.tvHome)
//        tvDefault = findViewById(R.id.tvDefault)
//        tvAddress = findViewById(R.id.tvAddress)
//        tvMobile = findViewById(R.id.tvMobile)
//        btnChange = findViewById(R.id.btnChange)
//        btnChange.setOnClickListener {
//            (activity as? HomeActivity)?.openFragment(FAQFragment())
//        }
//
//
//        val sp: SharedPreferences = getSharedPreferences(ConstantData.SP_LOGIN_PREFS, MODE_PRIVATE)
////        val savedName = sp.getString(ConstantData.KEY_USERNAME, "")
//        //val savedPhone = sp.getString(ConstantData.KEY_PHONE, "")
////        Toast.makeText(requireContext(), "name:"+savedName+"phone"+savedPhone, Toast.LENGTH_SHORT).show()
//
//        val userId = sp.getString(ConstantData.KEY_ID, "") ?: ""
//        Toast.makeText(applicationContext, "User"+ userId, Toast.LENGTH_SHORT).show()
//
//        loadAddress(userId)
//
//    }
//    fun loadAddress(userId: String) {
//
//        AddressApi().getDefaultAddress(userId, this) { output ->
//
//            if (output == null || output.addresses.isNullOrEmpty()) {
//                tvName.text = "No Address Found"
//                tvHome.text = "Type"
//                tvDefault.text = "( Default )"
//                tvAddress.text = "Full address here..."
//                tvMobile.text = "0000000000"
//                return@getDefaultAddress
//            }
//
//            val a = output.addresses!![0]
//
//            tvName.text = a.full_name
//            tvHome.text = a.address_type
//            tvDefault.text = if (a.is_default == "1") "( Default )" else ""
//            tvAddress.text = "${a.house_no}, ${a.area}, ${a.landmark}, ${a.city}, ${a.state} - ${a.pincode}"
//            tvMobile.text = a.phone
//        }
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

    fun openFragmentAds(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameAds, fragment)
            .addToBackStack(null)
            .commit()
    }
}