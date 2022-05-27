package com.example.corder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class AddCafeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cafe)
    }

    private fun saveInfo(){
        val pw = findViewById<EditText>(R.id.et_Password)
        val password = pw.text.toString()

        val cn = findViewById<EditText>(R.id.cafeName)
        val cafeName = cn.text.toString()

        val cp = findViewById<EditText>(R.id.cafeNum)
        val cafeNum = cp.text.toString()

        val ot = findViewById<EditText>(R.id.openTime)
        val openTime = ot.text.toString()

        val bt = findViewById<EditText>(R.id.breakTime)
        val breakTime = bt.text.toString()

        val ca = findViewById<EditText>(R.id.cafeAddress)
        val cafeAddress = ca.text.toString()

        val lp = findViewById<EditText>(R.id.limitPrice)
        val limitPrice = lp.text.toString()
    }
}