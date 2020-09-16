package com.example.phoneauthentication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_otp_verification.*
import java.util.concurrent.TimeUnit

class OtpVerification : AppCompatActivity() {
    var mAuth : FirebaseAuth?=null
    var verificationId : String = ""
    var forceResendingToken : PhoneAuthProvider.ForceResendingToken?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        mAuth = FirebaseAuth.getInstance()
        val phonenumber = intent.getStringExtra("phonenumber")
        requestOtp(phonenumber!!)
        buttonSignIn.setOnClickListener {
            if(editTextCode.text.isNotEmpty())
            {
                verifyCode(editTextCode.text.toString())
            }
        }

    }

    private fun requestOtp(phonenum: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phonenum,
            60L,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)
                    verificationId = p0
                    forceResendingToken = p1
                }

                override fun onCodeAutoRetrievalTimeOut(p0: String) {
                    super.onCodeAutoRetrievalTimeOut(p0)
                }

                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    if(editTextCode.text.isNotEmpty())
                    {
                        verifyCode(editTextCode.text.toString())
                    }
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@OtpVerification, p0.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

            })
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential).addOnCompleteListener(object :
            OnCompleteListener<AuthResult> {
            override fun onComplete(task: Task<AuthResult>) {
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@OtpVerification,
                        "Authentication Successfull",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this@OtpVerification, "Authentication Failed", Toast.LENGTH_LONG)
                        .show()
                }
            }

        })
    }
}