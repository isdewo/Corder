package com.example.corder

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddCafeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database.reference
    private lateinit var curUser: FirebaseUser

    private var isBlank = false //빈칸 확인

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cafe)

        val makeCafeBtn = findViewById<Button>(R.id.saveInfo)

        makeCafeBtn.setOnClickListener {
            // 카페등록
            saveInfo()
        }
    }

    private fun saveInfo(){
        val pw = findViewById<EditText>(R.id.et_Password)
        val password = pw.text.toString().trim()
        val cn = findViewById<EditText>(R.id.cafeName)
        val cafeName = cn.text.toString().trim()
        val cp = findViewById<EditText>(R.id.cafeNum)
        val cafeNum = cp.text.toString().trim()
        val ot = findViewById<EditText>(R.id.openTime)
        val openTime = ot.text.toString().trim()
        val bt = findViewById<EditText>(R.id.breakTime)
        val breakTime = bt.text.toString().trim()
        val ca = findViewById<EditText>(R.id.cafeAddress)
        val cafeAddress = ca.text.toString().trim()
        val lp = findViewById<EditText>(R.id.limitPrice)
        val limitPrice = lp.text.toString().trim()

        if(password.isEmpty() || cafeName.isEmpty() || cafeNum.isEmpty() || openTime.isEmpty() || breakTime.isEmpty() || cafeAddress.isEmpty() || limitPrice.isEmpty()) {
            isBlank = true
            return
        }
        else{
            isBlank = false
        }

        if(!isBlank){
            database.get().addOnCompleteListener(this){ task ->
                if(task.isSuccessful) {
                    Log.d(ContentValues.TAG, "카페 생성 성공")
                    val user = auth.currentUser
                    val email = user?.email
                    val seatText = ""
                    Toast.makeText(this, "카페 생성 완료!", Toast.LENGTH_SHORT).show()
                    registerCafe(
                        email,
                        cafeName,
                        cafeNum,
                        openTime,
                        breakTime,
                        cafeAddress,
                        limitPrice,
                        seatText
                    )
                    updateUI(user)
                }
                else{
                    Log.d(ContentValues.TAG,"카페 생성 실패",task.exception)
                    Toast.makeText(this, "카페 생성 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            if(isBlank){
                Toast.makeText(this, "빈 칸을 모두 채워 주세요", Toast.LENGTH_SHORT).show()
            }
        }

        
    }

    private fun updateUI(user: FirebaseUser?){
        if(user!=null){
            val intent = Intent(this, OwnerPageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            curUser = Firebase.auth.currentUser!!
        }
    }

    private fun registerCafe( userEmail: String?,cafeName:String, cafeNum:String, openTime:String, breakTime:String, cafeAddress:String, limitPrice:String, seatText:String){
        val cafe = Cafe(userEmail,cafeName, cafeNum, openTime, breakTime, cafeAddress, limitPrice, seatText)
        database.child("cafes").push().setValue(cafe)
    }



}