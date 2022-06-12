package com.example.corder

import android.content.ContentValues
import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_menu.*
import kotlinx.android.synthetic.main.activity_owner_cafe_info.*
import java.util.*

class AddMenuActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var storage: FirebaseStorage
    private val resultLauncher: ActivityResultLauncher<Intent>? = null
    var uri: Uri? = null
    var imgUri: String = ""
    var downloadUri: Uri? = null
    private val PICK_IMAGE = 10001
    val calendar: Calendar = Calendar.getInstance()
//    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//        uri= (result.data?.data)
//        uri?.let { upload(it) }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        val user = Firebase.auth.currentUser
        var uid = "0"
        if(user != null){
//            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
            uid = user.uid
        }

        storage = FirebaseStorage.getInstance()

        var saveMenu = findViewById<Button>(R.id.saveBtn)
        saveMenu.setOnClickListener{
            uri = downloadUri


            imgUri = uri.toString()

            val name = menuName.text.toString()
            val cost = menuCost.text.toString().toInt()


            if (name != "" && cost != -1 && uid != "0") {
                database = Firebase.database.reference
//                Toast.makeText(this, "${imgUri}", Toast.LENGTH_SHORT).show()
                val listMenu =
                    ListMenu(imgUri, uid, name, cost)

                database.child("menus").child(uid).child(name).setValue(listMenu)


                Toast.makeText(this, "메뉴가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, OwnerInsertMenuActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "메뉴추가에 실패했습니다.\n다시 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun upload(uri: Uri) {
        storageRef = storage.reference
        val sdf = SimpleDateFormat("yyyyMMddhhmmss")
        val fileName = sdf.format(calendar.timeInMillis) + ".jpg"
        val riversRef = storageRef.child("/$fileName")

        val rivers = riversRef.putFile(uri)
            .addOnCompleteListener(this) { taskSnapshot ->
                if (taskSnapshot.isSuccessful) {
                    Log.d(ContentValues.TAG, "업로드 성공")
                    Toast.makeText(this, "업로드 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(ContentValues.TAG, "업로드 실패", taskSnapshot.exception)
                    Toast.makeText(this, "업로드 실패", Toast.LENGTH_SHORT).show()
                }
            }

        val urltask = rivers.continueWithTask{ task ->
            if(!task.isSuccessful){
                task.exception?.let{
                    throw it
                }
            }
            riversRef.downloadUrl
        }.addOnCompleteListener{ task ->
            if(task.isSuccessful){
                downloadUri = task.result
            }
        }
    }
}