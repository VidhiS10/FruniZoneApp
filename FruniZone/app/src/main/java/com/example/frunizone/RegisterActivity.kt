package com.example.frunizone


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvAlreadyRegistered = findViewById<TextView>(R.id.tvAlreadyRegistered)

//        etPassword.transformationMethod = null // show
//        etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
//
//        etConfirmPassword.transformationMethod = null // show
//        etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()


        //Redirect to login page if user have registered
        tvAlreadyRegistered.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //Register btn
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            //  Empty check
            if (username.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Phone validation
            if (phone.length != 10 || !phone.all { it.isDigit() }) {
                etPhone.error = "Enter valid 10 digit phone"
                return@setOnClickListener
            }

            // Email validation
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Enter valid email"
                return@setOnClickListener
            }

            // Password validation
            val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")
            if (!password.matches(passwordPattern)) {
                etPassword.error = "Password must be 8+ chars, include UPPER, lower, digit"
                return@setOnClickListener
            }

            // Confirm password
            if (password != confirmPassword) {
                etConfirmPassword.error = "Passwords do not match"
                return@setOnClickListener
            }

            // Check duplicates in file
//            val duplicateMsg = checkDuplicate(phone, email)
//            if (duplicateMsg != null) {
//                Toast.makeText(this, duplicateMsg, Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }

            /*if (isUserExists(username, phone, email)) {
                Toast.makeText(this, "User already exists (username/phone/email taken)", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }*/


            try{
                val user = PersonModel(
                    user_id = "",
                    user_name = username,
                    user_email = email,
                    user_password = password,
                    user_phone = phone,
                    user_address = "",
                    user_pic = ""
                )
                RegisterUserApi().registerUser(user, this)
                // Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_LONG).show()
//
            }
            catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(this, "Error saving data: ${e.message}", Toast.LENGTH_LONG).show()
            }

            //Save user in text file
//            try {
//                val fileOutput = openFileOutput("user_data.txt", MODE_APPEND)
//                val writer = OutputStreamWriter(fileOutput)
//                val userData = "Username:$username | Phone:$phone | Email:$email | Password:$password\n"
//                writer.write(userData)
//                writer.close()
//
//                Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()
//
//              startActivity(Intent(this, LoginActivity::class.java))
////                startActivity(Intent(this, HomeActivity::class.java))
//                finish()
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    //Function to check user is exist form data base
    /* private fun checkDuplicate(phone: String, email: String): String? {
         try {
             val fileInput = openFileInput("user_data.txt")
             val reader = BufferedReader(InputStreamReader(fileInput))
             var line: String?

             while (reader.readLine().also { line = it } != null) {
                 line?.let {
                     val storedPhone = Regex("Phone:(.*?)\\|").find(it)?.groupValues?.get(1)?.trim()
                     val storedEmail = Regex("Email:(.*?)\\|").find(it)?.groupValues?.get(1)?.trim()

                     if (phone == storedPhone) {
                         reader.close()
                         return "Phone number already exists"
                     }
                     if (email == storedEmail) {
                         reader.close()
                         return "Email already exists"
                     }
                 }
             }
             reader.close()
         } catch (e: FileNotFoundException) {
             // File doesn't exist yet → no users registered
         } catch (e: Exception) {
             e.printStackTrace()
         }
         return null // No duplicate found
     }*/


    /* private fun isUserExists(username: String, phone: String, email: String): Boolean {
         try {
             val fileInput = openFileInput("user_data.txt")
             val reader = BufferedReader(InputStreamReader(fileInput))
             var line: String?

             while (reader.readLine().also { line = it } != null) {
                 line?.let {
                     val storedUsername = Regex("Username:(.*?)\\|").find(it)?.groupValues?.get(1)?.trim()
                     val storedPhone = Regex("Phone:(.*?)\\|").find(it)?.groupValues?.get(1)?.trim()
                     val storedEmail = Regex("Email:(.*?)\\|").find(it)?.groupValues?.get(1)?.trim()

                     if (username == storedUsername || phone == storedPhone || email == storedEmail) {
                         reader.close()
                         return true
                     }
                 }
             }
             reader.close()
         } catch (e: FileNotFoundException) {
             // File doesn't exist yet, no users registered
         } catch (e: Exception) {
             e.printStackTrace()
         }
         return false
     }*/
}