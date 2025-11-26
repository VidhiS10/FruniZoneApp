package com.example.frunizone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HurrayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hurray)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val back = findViewById<ImageView>(R.id.ivBack)
        val title = findViewById<TextView>(R.id.tvTitle)

        title.text = "Order Confirm"

        back.setOnClickListener {
            onBackPressed()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@HurrayActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()   // close current activity
        }, 4000)
    }
}