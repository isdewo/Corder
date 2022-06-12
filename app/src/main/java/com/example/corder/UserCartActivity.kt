package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class UserCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)

        val menuEat = findViewById<Button>(R.id.menuEat)
        menuEat.setOnClickListener{
            startActivity(Intent(this, SeatShowActivity::class.java))
        }
    }
}