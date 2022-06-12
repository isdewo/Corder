package com.example.corder

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_menu.*

class AddMenuActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        val user = Firebase.auth.currentUser
        var uid = "0"
        if(user != null){
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            uid = user.uid
        }else{
            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
        }

//        var saveMenu = findViewById<Button>(R.id.saveBtn)
//        saveMenu.setOnClickListener{
////            var img = "https://cdn-icons-png.flaticon.com/512/6166/6166014.png"
//            var img = ContextCompat.getDrawable(this, R.drawable.americano)!!
//            var menuName = menuName.text.toString()
//            var menuCost = menuCost.text.toString()
//
//            if(menuName != "" && menuCost != "" && uid != "0") {
//                database = Firebase.database.reference
//                val listMenu =  ListMenu(img, uid, menuName, menuCost.toInt())
//                database.child("menus").child(uid).setValue(listMenu)
//
//
//                Toast.makeText(this, "메뉴가 추가되었습니다.", Toast.LENGTH_SHORT).show()
//                var intent = Intent(this, OwnerInsertMenuActivity::class.java)
//                startActivity(intent)
//            }else {
//                Toast.makeText(this, "메뉴추가에 실패했습니다.\n다시 입력해주세요.", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}