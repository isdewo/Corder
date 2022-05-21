package com.example.corder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserCafeInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cafe_info)

        val listMenu = findViewById<RecyclerView>(R.id.menuList)
        val linearLayoutManager = LinearLayoutManager(this)

//        binding.listCafe.layoutManager = gridLayoutManager
        listMenu.layoutManager = linearLayoutManager

        var list = ArrayList<ListMenu>()
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 1, "아메리카노", 3000))
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 2, "카페라떼", 3500))
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 3, "요거트스무디", 5500))

        val adapter = RecyclerMenuAdapter(list)
//        binding.listMenu.adapter = adapter
        listMenu.adapter = adapter
    }
}