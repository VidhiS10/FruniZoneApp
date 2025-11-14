package com.example.frunizone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.frunizone.api.RegisterUserApi
import com.example.frunizone.model.PersonModel
import com.example.frunizone.util.ConstantData


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etPhone = findViewById<EditText>(R.id.etPhn)
        val etPassword = findViewById<EditText>(R.id.etPwd)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvSignup = findViewById<TextView>(R.id.tvSignup)

        //Redirect to register page if user have registered
        tvSignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val enteredPhone = etPhone.text.toString().trim()
            val enteredPass = etPassword.text.toString().trim()

            if (enteredPhone.isEmpty() || enteredPass.isEmpty()) {
                Toast.makeText(this, "Enter phone & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                /*val fileInput = openFileInput("user_data.txt")
                val reader = BufferedReader(InputStreamReader(fileInput))
                var line: String?
                var storedUsername: String? = null
                var loginSuccess = false

                while (reader.readLine().also { line = it } != null) {
                    line?.let {
                        val username = Regex("Username:(.*?)\\|").find(it)?.groupValues?.get(1)?.trim()
                        val storedPhone = Regex("Phone:(.*?)\\|").find(it)?.groupValues?.get(1)?.trim()
                        val storedPassword = Regex("Password:(.*)").find(it)?.groupValues?.get(1)?.trim()

                        if (enteredPhone == storedPhone && enteredPass == storedPassword) {
                            loginSuccess = true
                            storedUsername = username
                        }
                    }
                }
                reader.close()


                if (loginSuccess) {
//                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                    val sharedPref = getSharedPreferences("MyAppPref", MODE_PRIVATE)
                    sharedPref.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString("username", storedUsername) // save username
                        .apply()

                    startActivity(Intent(this, HomeActivity::class.java))
//                startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Phone or Password", Toast.LENGTH_SHORT).show()
                }*/

                val model = PersonModel("", "", "", enteredPass, enteredPhone, "", "")
//                RegisterUserApi().login(model, this)
//                val sharedPref = getSharedPreferences(ConstantData.SP_LOGIN_PREFS, MODE_PRIVATE)
//                val username = sharedPref.getString(ConstantData.KEY_USERNAME, "User")
//                Toast.makeText(this, "Welcome, $username!", Toast.LENGTH_LONG).show()

                RegisterUserApi().login(model, this) { success ->

                    if (success) {

                        val sharedPref = getSharedPreferences(ConstantData.SP_LOGIN_PREFS, MODE_PRIVATE)
                        val username = sharedPref.getString(ConstantData.KEY_USERNAME, "User")

                        Toast.makeText(this, "Welcome $username", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()

                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "No user found! Please register.", Toast.LENGTH_SHORT).show()
            }
        }


    }

}