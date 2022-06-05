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
import com.example.corder.databinding.ActivityMainBinding
import com.example.corder.databinding.ActivityUserCafeInfoBinding
import kotlinx.android.synthetic.main.activity_user_cafe_info.*

class UserCafeInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cafe_info)

        val name = intent.getStringExtra("name")
        tv_name.setText(name)

        val getMenu = findViewById<Button>(R.id.getmenu)

        getMenu.setOnClickListener {
            // 메뉴확인페이지
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }
//        val list = ArrayList<ListMenu>()
//        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 1, "아메리카노", 3000))
//        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 2, "카페라떼", 3500))
//        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 3, "요거트스무디", 5500))
//
//        val adapter = RecyclerMenuAdapter(list, {data -> adapterOnClick(data)})
//        val listCafe = findViewById<RecyclerView>(R.id.listMenu)
//        listCafe.layoutManager = LinearLayoutManager(this)
//
//        listCafe.adapter = adapter
//    }
//
//    private fun adapterOnClick(data: ListMenu){
//        Toast.makeText(applicationContext, "menu : ${data.menuName}, cost: ${data.menuCost}", Toast.LENGTH_SHORT).show()
////        var intent = Intent(this, UserCafeInfoActivity::class.java)
////        startActivity(intent)
//    }
}