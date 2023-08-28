package com.example.onlinebazar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.onlinebazar.databinding.ActivityVerificationBinding
import com.google.firebase.auth.FirebaseAuth

class VerificationActivity : AppCompatActivity() {

    var binding : ActivityVerificationBinding? = null

    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser != null){
               val intent = Intent(this@VerificationActivity,
                   MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        supportActionBar!!.hide()
        binding!!.edittext.requestFocus()
        binding!!.btn.setOnClickListener {
            val intent = Intent(this@VerificationActivity,
                OtpActivity::class.java)
            intent.putExtra("phoneNumber",binding!!.edittext.text.toString())
            startActivity(intent)

        }
    }
}