package com.example.frunizone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.annotation.SuppressLint
import android.content.Intent

import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.example.frunizone.util.ConstantData
//import com.example.furniture.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zagori.bottomnavbar.BottomNavBar

class HomeActivity : AppCompatActivity() {
    private lateinit var bnvMain: BottomNavBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPref = getSharedPreferences(ConstantData.SP_LOGIN_PREFS, MODE_PRIVATE)
        val username = sharedPref.getString(ConstantData.KEY_USERNAME, "User")
        Toast.makeText(this, "Welcome, $username!", Toast.LENGTH_LONG).show()

        bnvMain = findViewById(R.id.bnvMain)
        openFragment(HomeFragment())

        bnvMain.setBottomNavigationListener(object : BottomNavBar.OnBottomNavigationListener {
            override fun onNavigationItemSelected(menuItem: MenuItem?): Boolean {
                if (menuItem != null) {
                    when (menuItem?.itemId) {
                        R.id.menu_home -> openFragment(HomeFragment())
                        R.id.menu_myacc -> openFragment(AccountFragment())
                        R.id.menu_cart -> {
                            val intent = Intent(this@HomeActivity, CartActivity::class.java)
                            startActivity(intent)
                        }

                        R.id.menu_cat -> openFragment(CategoriesFragment())
                        R.id.menu_offer -> openFragment(OffersFragment())
                        else -> openFragment(HomeFragment())
                    }
                }
                return true
            }


        })

    }
    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }
}