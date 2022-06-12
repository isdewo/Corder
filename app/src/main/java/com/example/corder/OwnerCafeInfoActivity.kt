package com.example.corder

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_owner_cafe_info.*

class OwnerCafeInfoActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var storageRef : StorageReference
    private lateinit var storage : FirebaseStorage
    val GALLERY = 101 // 앨범 권한 처리
    var uri : Uri? = null
    var imgUri : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_cafe_info)

        storage = FirebaseStorage.getInstance()
        getImg.setOnClickListener{
            val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_PICK)
            startActivityForResult(intent, GALLERY)
        }

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

//            Toast.makeText(this, "저장버튼: ${imgUri}", Toast.LENGTH_SHORT).show()
            imgUri = "http://t2.gstatic.com/licensed-image?q=tbn:ANd9GcS9DvbOUdtK8lQS6VYAfWQokJcjDSJqL2gzA7oKVIfBJfrRuHSA7p85-bbuaLU0vlKAK-zvPmgK9EAGbYE8ao8"
//            val imgUri = uri.toString()
            val name = cafeName.text.toString()
            val owner = ownerName.text.toString()
            val address = cafeAddress.text.toString()
            val call = cafeCall.text.toString()
            val time = cafeTime.text.toString()
            val limit = limitPrice.text.toString()
            if(imgUri != "" && name != "" && owner != "" && address != "" && call != "" && time != "" && uid != "0") {
                database = Firebase.database.reference
//                Toast.makeText(this, "${imgUri}", Toast.LENGTH_SHORT).show()
                val listData =  ListData(imgUri, uid, name, owner, address, call, time, limit.toInt())
                database.child("cafes").child(uid).setValue(listData)


                Toast.makeText(this, "카페 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "정보저장에 실패했습니다.\n다시 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GALLERY){
                uri = data?.getData()
//                imgUri = uri.toString()
                cafeImage.setImageURI(uri)
            }
        }
    }

//    private fun funImageUpload(view : View){
//        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        var imgFilename = "IMAGE_" + timeStamp + "_.png"
//        var storageRef = storage?.reference?.child("images")?.child(imgFilename)
//
//        storageRef?.putFile(uri!!)?.addOnSuccessListener {
//            Toast.makeText(view.context, "Image Uploaded", Toast.LENGTH_SHORT).show()
//        }
//    }

}