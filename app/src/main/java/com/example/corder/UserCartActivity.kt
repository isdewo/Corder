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
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_user_cart.*
import kotlinx.android.synthetic.main.activity_user_cart.radioGroup
import kotlin.random.Random

class UserCartActivity : AppCompatActivity() {

    private lateinit var dbRes: DatabaseReference
    private lateinit var dbref : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var listOrder : RecyclerView
    private lateinit var list : ArrayList<ListOrderMenu>
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)

        var clickBtn = 0
        radioGroup.setOnCheckedChangeListener{ _, checkedId ->
            when(checkedId){
                R.id.takein -> {
                    clickBtn = 0
                }
                R.id.takeout -> {
                    clickBtn = 1
                }
            }
        }
        
        // 결제 취소
        val clickNotpay = findViewById<Button>(R.id.notpay)
        clickNotpay.setOnClickListener {
            finish()
        }


        // 결제
        val pay = findViewById<Button>(R.id.pay)
        pay.setOnClickListener {
//            database.child("pay").child(uid).child(name).setValue(listMenu)
            getListData(1)
            Toast.makeText(this, "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            
            if(clickBtn == 1){
                var intent = Intent(this, PayListActivity::class.java)
                startActivity(intent)   
            }else if(clickBtn == 0){
                // 좌석 페이지
                Toast.makeText(this, "좌석 페이지", Toast.LENGTH_SHORT).show()
            }
        }

        list = ArrayList<ListOrderMenu>()

        getListData(0)


        listOrder = findViewById<RecyclerView>(R.id.listOrder)
        listOrder.layoutManager = LinearLayoutManager(this)

    }

    private fun getListData(isPay: Int){
        val user = Firebase.auth.currentUser
        var price = 0
        var uid = "0"
        if(user != null){
            uid = user.uid
        }

        database = FirebaseDatabase.getInstance()
        dbref = database.getReference("cart").child(uid)
//        Toast.makeText(this, "$uid", Toast.LENGTH_SHORT).show()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
        dbRes = Firebase.database.reference

        dbref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for(dataSnapshot in snapshot.children){

                    val menuName = dataSnapshot.child("menuName").getValue().toString()
                    val menuCost = dataSnapshot.child("menuCost").getValue().toString().toInt()
                    val count = dataSnapshot.child("countMenu").getValue().toString().toInt()
                    price += menuCost

                    list.add(ListOrderMenu(menuName, menuCost, count))
                    if(isPay == 1){
                        dbRes.child("pay").child(uid).setValue(list)
                    }
                }
                menuCost.text = "Total: " + price.toString()

                var adapter = RecyclerOrderMenuAdapter(list, {data -> adapterOnClick(data)})
                listOrder.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun adapterOnClick(data: ListOrderMenu){


//        val menuEat = findViewById<Button>(R.id.menuEat)
//        menuEat.setOnClickListener {
//            startActivity(Intent(applicationContext, SeatEditActivity::class.java))
//        }

    }
}