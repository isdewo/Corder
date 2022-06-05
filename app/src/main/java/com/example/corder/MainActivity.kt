package com.example.corder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<ListData>()
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 1, "coffee1"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 2, "coffee2"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 3, "coffee3"))

        val adapter = RecyclerMainAdapter(list, {data -> adapterOnClick(data)})
        val listCafe = findViewById<RecyclerView>(R.id.listCafe)
        listCafe.layoutManager = GridLayoutManager(this, 3)

        listCafe.adapter = adapter

        val goLoginbtn = findViewById<Button>(R.id.goLogin)

        goLoginbtn.setOnClickListener {
            // 자리현황 확인/변경
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun adapterOnClick(data: ListData){
        Toast.makeText(applicationContext, "coffee name : ${data.cName}, Number: ${data.number}", Toast.LENGTH_SHORT).show()
        var intent = Intent(this, UserCafeInfoActivity::class.java)

//
//        intent.putExtra("image", bitmap)
        intent.putExtra("name", data.cName)
        intent.putExtra("number", data.number)
        startActivity(intent)
    }
}

