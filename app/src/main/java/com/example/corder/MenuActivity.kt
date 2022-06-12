package com.example.corder

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_menu.*


class MenuActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var menuList : RecyclerView
    private lateinit var list : ArrayList<ListMenu>
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // 장바구니로 이동
        val clickCartbtn = findViewById<Button>(R.id.cart)
        clickCartbtn.setOnClickListener {
            var intent = Intent(this, UserCartActivity::class.java)
            startActivity(intent)
        }

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

        list = ArrayList<ListMenu>()

        getListData()


        menuList = findViewById<RecyclerView>(R.id.menuList)
        menuList.layoutManager = LinearLayoutManager(this)
    }

    private fun getListData(){
        var supervisorId = intent.getStringExtra("cafeCode").toString()
//        Toast.makeText(this@MenuActivity, "$supervisorId", Toast.LENGTH_SHORT).show()
        database = FirebaseDatabase.getInstance()
        dbref = database.getReference("menus").child(supervisorId)
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        dbref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for(dataSnapshot in snapshot.children){

                    val img = ""
                    val menuName = dataSnapshot.child("menuName").getValue().toString()
                    val menuCost = dataSnapshot.child("menuCost").getValue().toString()

                    list.add(ListMenu(img, supervisorId, menuName, menuCost.toInt()))
                }
                val adapter = RecyclerMenuAdapter(list, {data -> adapterOnClick(data)})
                menuList.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun adapterOnClick(data: ListMenu){
        var intent = Intent(this, UserSelectDrinkActivity::class.java)


//        intent.putExtra("image", bitmap)
        intent.putExtra("name", data.menuName)
        intent.putExtra("cost", data.menuCost)
        startActivity(intent)

    }
}