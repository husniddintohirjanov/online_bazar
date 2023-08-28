package com.example.onlinebazar

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.onlinebazar.databinding.ActivityOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    var  binding : ActivityOtpBinding? = null
    var verificationId : String? = null
    var auth : FirebaseAuth? = null
    var dialog : ProgressDialog? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        dialog = ProgressDialog(this@OtpActivity)
        dialog!!.setMessage("Sending OTP...")
        dialog!!.setCancelable(false)
        dialog!!.show()
        auth = FirebaseAuth.getInstance()
        supportActionBar!!.hide()
        val phoneNumber = intent.getStringExtra("phoneNumber")
        binding!!.phone.text = "Verify $phoneNumber"

        val options = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(phoneNumber!!)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this@OtpActivity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    TODO("Not yet implemented")
                }

                override fun onCodeSent(verifyId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verifyId, forceResendingToken)
                    dialog!!.dismiss()
                    verificationId = verifyId
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
                    binding!!.etidtext1.requestFocus()
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
        findViewById<Button>(R.id.btn1).setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(verificationId!!, findViewById<EditText>(R.id.etidtext1).text.toString())

            auth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@OtpActivity, SetupProfileActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        Toast.makeText(this@OtpActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

