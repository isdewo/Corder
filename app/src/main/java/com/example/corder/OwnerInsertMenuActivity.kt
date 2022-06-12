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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.list_menu.*

class OwnerInsertMenuActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var listMenu : RecyclerView
    private lateinit var list : ArrayList<ListMenu>
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_insert_menu)

        var addMenu = findViewById<Button>(R.id.addMenu)
        addMenu.setOnClickListener {
            startActivity(Intent(this, AddMenuActivity::class.java))
        }

        // home
        val clickHome = findViewById<Button>(R.id.home)
        clickHome.setOnClickListener {
            var intent = Intent(this, OwnerPageActivity::class.java)
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

        listMenu = findViewById(R.id.listMenu)

        listMenu.layoutManager = LinearLayoutManager(this)
    }

    private fun getListData(){
        val user = Firebase.auth.currentUser
        var uid = "0"
        if(user != null){
            uid = user.uid
        }

        database = FirebaseDatabase.getInstance()
        dbref = database.getReference("menus").child(uid)
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        dbref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for(dataSnapshot in snapshot.children){

                    val img = ""
                    val name =
                        dataSnapshot.getValue().toString()
//                    Toast.makeText(this@OwnerInsertMenuActivity, "$name", Toast.LENGTH_SHORT).show()

                    val menuName = dataSnapshot.child("menuName").getValue().toString()
                    val menuCost = dataSnapshot.child("menuCost").getValue().toString()

//                    Toast.makeText(this@OwnerInsertMenuActivity, "$menuName", Toast.LENGTH_SHORT).show()
//                    Toast.makeText(this@OwnerInsertMenuActivity, "$menuCost", Toast.LENGTH_SHORT).show()

                    list.add(ListMenu(img, uid, menuName, menuCost.toInt()))
                }
                val adapter = RecyclerMenuAdapter(list, {data -> adapterOnClick(data)})
                listMenu.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun adapterOnClick(data: ListMenu){

    }
}