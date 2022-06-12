package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class OwnerInsertMenuActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var listMenu : RecyclerView
    private lateinit var list : ArrayList<ListMenu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_insert_menu)

        var addMenu = findViewById<Button>(R.id.addMenu)
        addMenu.setOnClickListener {
            startActivity(Intent(this, AddMenuActivity::class.java))
        }

        database = FirebaseDatabase.getInstance()
        dbref = database.getReference("cafes")

        val user = Firebase.auth.currentUser
        var uid = "0"
        if(user != null){
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            uid = user.uid
        }else{
            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
        }

//        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                list.clear()
//                for (dataSnapshot in snapshot.children) {
////                    val img = dataSnapshot.child("img").getValue().toString()
//                    val menuName =
//                        dataSnapshot.child("menu").child("menuName").getValue().toString()
//                    val menuCost =
//                        dataSnapshot.child("menu").child("menuCost").getValue().toString()
//
//
//                    list.add(ListMenu(ContextCompat.getDrawable(applicationContext, R.drawable.americano)!!, uid, menuName, menuCost.toInt()))
//                }
//
//                val adapter = RecyclerMenuAdapter(list, {data -> adapterOnClick(data)})
//
//                listMenu.adapter = adapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
    }
    private fun adapterOnClick(data: ListMenu){

    }
}