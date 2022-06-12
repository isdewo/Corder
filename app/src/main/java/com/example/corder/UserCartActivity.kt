package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class UserCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)

        // 결제 취소
        val clickNotpay = findViewById<Button>(R.id.notpay)
        clickNotpay.setOnClickListener {
            finish()
        }
    }
}