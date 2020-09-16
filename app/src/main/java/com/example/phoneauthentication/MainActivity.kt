package com.example.phoneauthentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_otp_verification.*
import kotlinx.android.synthetic.main.activity_otp_verification.buttonSignIn
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonContinue.setOnClickListener {
            if(editTextPhone.text.isNullOrEmpty() && editTextCode.text.length < 10)
            {
                editTextPhone.error = "Invalid Number"
                editTextPhone.requestFocus()
            }
            else
            {
                val intent = Intent(this@MainActivity, OtpVerification::class.java)
                intent.putExtra("phonenumber", editTextPhone.text.toString())
                startActivity(intent)
            }
        }
    }
}