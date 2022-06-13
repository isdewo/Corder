package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_pay_list.*
import kotlinx.android.synthetic.main.activity_user_cart.*

class PayListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var listPay : RecyclerView
    private lateinit var list : ArrayList<ListOrderMenu>
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_list)

        // home
        val clickHome = findViewById<Button>(R.id.home)
        clickHome.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // logout
        val clickLogout = findViewById<Button>(R.id.logout)
        clickLogout.setOnClickListener {
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        list = ArrayList<ListOrderMenu>()

        getListData()


        listPay = findViewById<RecyclerView>(R.id.listPay)
        listPay.layoutManager = LinearLayoutManager(this)

    }

    private fun getListData(){
        val user = Firebase.auth.currentUser
//        var price = 0
        var uid = "0"
        if(user != null){
            uid = user.uid
        }

        database = FirebaseDatabase.getInstance()
        dbref = database.getReference("pay").child(uid)
//        Toast.makeText(this, "$uid", Toast.LENGTH_SHORT).show()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference


        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for (dataSnapshot in snapshot.children) {
                    val menuName = dataSnapshot.child("menuName").getValue().toString()
                    val menuCost = dataSnapshot.child("menuCost").getValue().toString().toInt()
                    val count = dataSnapshot.child("countMenu").getValue().toString().toInt()

                    list.add(ListOrderMenu(menuName, menuCost, count))

                }

                var adapter = RecyclerOrderMenuAdapter(list, {data -> adapterOnClick(data)})
                listPay.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun adapterOnClick(data: ListOrderMenu){

    }
}