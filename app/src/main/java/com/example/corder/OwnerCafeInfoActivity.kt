package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_owner_cafe_info.*

class OwnerCafeInfoActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_cafe_info)

        val user = Firebase.auth.currentUser
        var uid = "0"
        if(user != null){
            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            uid = user.uid
        }else{
            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
        }

        cafeCode.text = uid
        val clickSavebtn = findViewById<Button>(R.id.saveBtn)
        clickSavebtn.setOnClickListener {
//            var num = cafeNum.text.toString()

            val imgUrl = "http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcS9DvbOUdtK8lQS6VYAfWQokJcjDSJqL2gzA7oKVIfBJfrRuHSA7p85-bbuaLU0vlKAK-zvPmgK9EAGbYE8ao8"
            val name = cafeName.text.toString()
            val owner = ownerName.text.toString()
            val address = cafeAddress.text.toString()
            val call = cafeCall.text.toString()
            val time = cafeTime.text.toString()
            val limit = limitPrice.text.toString()
            if(name != "" && owner != "" && address != "" && call != "" && time != "" && uid != "0") {
                database = Firebase.database.reference
                val listData =  ListData(imgUrl, uid, name, owner, address, call, time, limit.toInt())
                database.child("cafes").child(uid).setValue(listData)
//                database.child("cafes").child(uid).child("imgUrl").setValue("http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcS9DvbOUdtK8lQS6VYAfWQokJcjDSJqL2gzA7oKVIfBJfrRuHSA7p85-bbuaLU0vlKAK-zvPmgK9EAGbYE8ao8")
//                database.child("cafes").child(uid).child("cafeCode").setValue(uid)
//                database.child("cafes").child(uid).child("cafeName").setValue(name)
//                database.child("cafes").child(uid).child("ownerName").setValue(owner)
//                database.child("cafes").child(uid).child("cafeAddress").setValue(address)
//                database.child("cafes").child(uid).child("cafeCall").setValue(call)
//                database.child("cafes").child(uid).child("cafeTime").setValue(time)

                Toast.makeText(this, "카페 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "정보저장에 실패했습니다.\n다시 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}