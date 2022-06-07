package com.example.corder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity(){

    private lateinit var dbref : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var listCafe : RecyclerView
    private lateinit var list : ArrayList<ListData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = ArrayList<ListData>()
//        list.add(ListData("http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcS9DvbOUdtK8lQS6VYAfWQokJcjDSJqL2gzA7oKVIfBJfrRuHSA7p85-bbuaLU0vlKAK-zvPmgK9EAGbYE8ao8", "1", "coffeeeeee", "ownerName", "경기도 용인시 처인구 역북동", "010-1111-1111", "오전 10:00 ~ 오후 12:00", 15000))
//        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 2, "coffee2", "ownerName", "경기도 용인시 처인구 역북동", "010-1111-1111", "오전 10:00 ~ 오후 12:00"))
//        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 3, "coffee3", "ownerName", "경기도 용인시 처인구 역북동", "010-1111-1111", "오전 10:00 ~ 오후 12:00"))
        getListData()

//        val adapter = RecyclerMainAdapter(list, {data -> adapterOnClick(data)})
//
        listCafe = findViewById(R.id.listCafe)

        listCafe.layoutManager = GridLayoutManager(this, 3)
//
//        listCafe.adapter = adapter

//        val goLoginbtn = findViewById<Button>(R.id.goLogin)
//
//        goLoginbtn.setOnClickListener {
//            // 자리현황 확인/변경
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
    }

    private fun getListData(){
        database = FirebaseDatabase.getInstance()
        dbref = database.getReference("cafes")

        dbref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                Toast.makeText(applicationContext, "onDataChange", Toast.LENGTH_SHORT).show()
                list.clear()
                for(dataSnapshot in snapshot.children){
                    val imgUri = dataSnapshot.child("imgUri").getValue().toString()
                    val cafeCode = dataSnapshot.child("cafeCode").getValue().toString()
                    val cafeName = dataSnapshot.child("cafeName").getValue().toString()
                    val ownerName = dataSnapshot.child("ownerName").getValue().toString()
                    val cafeAddress = dataSnapshot.child("cafeAddress").getValue().toString()
                    val cafeCall = dataSnapshot.child("cafeCall").getValue().toString()
                    val cafeTime = dataSnapshot.child("cafeTime").getValue().toString()
                    val limitPrice = dataSnapshot.child("limitPrice").getValue().toString()


                    list.add(ListData(imgUri, cafeCode, cafeName, ownerName, cafeAddress, cafeCall, cafeTime, limitPrice.toInt()))
                }

                val adapter = RecyclerMainAdapter(list, {data -> adapterOnClick(data)})

                listCafe.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun adapterOnClick(data: ListData){
        var intent = Intent(this, UserCafeInfoActivity::class.java)

        intent.putExtra("image", data.imgUri)
        intent.putExtra("name", data.cafeName)
        intent.putExtra("number", data.cafeCode)
        intent.putExtra("cafeTime", data.cafeTime)
        intent.putExtra("cafeCall", data.cafeCall)
        intent.putExtra("cafeAddress", data.cafeAddress)
        intent.putExtra("limitPrice", data.limitPrice)
        startActivity(intent)
    }
}

