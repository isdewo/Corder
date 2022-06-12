package com.example.corder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class MainActivity : AppCompatActivity(){

    private lateinit var dbref : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var listCafe : RecyclerView
    private lateinit var list : ArrayList<ListData>
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = ArrayList<ListData>()

        getListData()

        listCafe = findViewById(R.id.listCafe)

        listCafe.layoutManager = GridLayoutManager(this, 3)
    }

    private fun getListData(){
        database = FirebaseDatabase.getInstance()
        dbref = database.getReference("cafes")
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        dbref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
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
        intent.putExtra("cafeCode", data.cafeCode)
        intent.putExtra("cafeTime", data.cafeTime)
        intent.putExtra("cafeCall", data.cafeCall)
        intent.putExtra("cafeAddress", data.cafeAddress)
        intent.putExtra("limitPrice", data.limitPrice)
        startActivity(intent)
    }
}
