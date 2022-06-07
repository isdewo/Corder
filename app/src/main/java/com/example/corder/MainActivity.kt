package com.example.corder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class MainActivity : AppCompatActivity(){

    private lateinit var dbref : DatabaseReference
    private lateinit var listCafe : RecyclerView
    private lateinit var list : ArrayList<ListData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = ArrayList<ListData>()
        list.add(ListData("http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcS9DvbOUdtK8lQS6VYAfWQokJcjDSJqL2gzA7oKVIfBJfrRuHSA7p85-bbuaLU0vlKAK-zvPmgK9EAGbYE8ao8", "1", "coffeeeeee", "ownerName", "경기도 용인시 처인구 역북동", "010-1111-1111", "오전 10:00 ~ 오후 12:00", 15000))
//        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 2, "coffee2", "ownerName", "경기도 용인시 처인구 역북동", "010-1111-1111", "오전 10:00 ~ 오후 12:00"))
//        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 3, "coffee3", "ownerName", "경기도 용인시 처인구 역북동", "010-1111-1111", "오전 10:00 ~ 오후 12:00"))
//        getListData()

        val adapter = RecyclerMainAdapter(list, {data -> adapterOnClick(data)})

        listCafe = findViewById(R.id.listCafe)
//        val listCafe = findViewById<RecyclerView>(R.id.listCafe)
        listCafe.layoutManager = GridLayoutManager(this, 3)

        listCafe.adapter = adapter

//        val goLoginbtn = findViewById<Button>(R.id.goLogin)
//
//        goLoginbtn.setOnClickListener {
//            // 자리현황 확인/변경
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
    }

    private fun getListData(){
        dbref = FirebaseDatabase.getInstance().getReference("cafes")
        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    Toast.makeText(this@MainActivity, "존재", Toast.LENGTH_SHORT).show()
                    for(dataSnapshot in snapshot.children){
//                        val cafe = dataSnapshot.getValue(ListData::class.java)
                        val cafe = dataSnapshot.getValue(ListData::class.java)
                        list.add(cafe!!)
                    }
                    listCafe.adapter = RecyclerMainAdapter(list, {data -> adapterOnClick(data)})
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun adapterOnClick(data: ListData){
//        Toast.makeText(applicationContext, "coffee name : ${data.cName}, Number: ${data.number}", Toast.LENGTH_SHORT).show()
        var intent = Intent(this, UserCafeInfoActivity::class.java)

        intent.putExtra("image", data.imgUrl)
        intent.putExtra("name", data.cafeName)
        intent.putExtra("number", data.cafeCode)
        startActivity(intent)
    }
}

