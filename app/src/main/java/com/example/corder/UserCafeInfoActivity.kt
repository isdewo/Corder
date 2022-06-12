package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.corder.databinding.ActivityMainBinding
import com.example.corder.databinding.ActivityUserCafeInfoBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_cafe_info.*

class UserCafeInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_cafe_info)

        val name = intent.getStringExtra("name")
        tv_name.setText(name)

        val imgurl = intent.getStringExtra("image")
        Glide.with(this).load(imgurl).into(shopPicture)

        val time = intent.getStringExtra("cafeTime")
        inputTime.setText(time)

        val addr = intent.getStringExtra("cafeAddress")
        address.setText(addr)

        val callNum = intent.getStringExtra("cafeCall")
        call.setText(callNum)

        val limit = intent.getIntExtra("limitPrice", 0)
        lowOrder.setText(limit.toString())

//        shopPicture.setImageBitmap(img)

        val getMenu = findViewById<Button>(R.id.getmenu)

        getMenu.setOnClickListener {
            // 메뉴확인페이지
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }
}