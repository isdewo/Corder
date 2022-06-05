package com.example.corder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val list = ArrayList<ListMenu>()
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 1, "아메리카노", 3000))
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 2, "카페라떼", 3500))
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 3, "요거트스무디", 5500))
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 3, "치즈케이크", 8000))
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 3, "초코케이크", 8000))

        val adapter = RecyclerMenuAdapter(list, {data -> adapterOnClick(data)})
        val menuList = findViewById<RecyclerView>(R.id.menuList)
        menuList.layoutManager = LinearLayoutManager(this)
        menuList.adapter = adapter
    }

    private fun adapterOnClick(data: ListMenu){
        Toast.makeText(applicationContext, "Clicked -> menuName: ${data.menuName}, cost: ${data.menuCost}", Toast.LENGTH_SHORT).show()
//        var intent = Intent(this, UserCafeInfoActivity::class.java)
//
////
////        intent.putExtra("image", bitmap)
//        intent.putExtra("name", data.cName)
//        intent.putExtra("number", data.number)
//        startActivity(intent)
    }
}