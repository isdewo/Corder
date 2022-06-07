package com.example.corder

import android.content.ContentValues.TAG
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

class JoinActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database.reference
    private lateinit var curUser: FirebaseUser


    private var userSort:Boolean = false
    private var isBlank = false // 빈칸 확인
    private var clickBtn = ""

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = FirebaseAuth.getInstance()

        val idCreation = findViewById<Button>(R.id.idCreation)
        idCreation.setOnClickListener{
            register()
        }

    }

    private fun register(){
        val inputId = findViewById<EditText>(R.id.inputId)
        val email = inputId.text.toString().trim()
        val inputPw = findViewById<EditText>(R.id.inputPw)
        val password = inputPw.text.toString().trim()
        val inputName = findViewById<EditText>(R.id.inputName)
        val name = inputName.text.toString().trim()

        val sellerButton = findViewById<Button>(R.id.supervisorBtn)
        sellerButton.setOnClickListener{
            userSort = true
            clickBtn = "supervisor"
        }

        val customerButton = findViewById<Button>(R.id.customerBtn)
        customerButton.setOnClickListener {
            userSort = false
            clickBtn = "customer"
        }

        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            isBlank = true
            return
        }
        else{
            isBlank = false
        }

        if (!isBlank) {
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        Log.d(TAG,"회원가입 성공")
                        val user = auth.currentUser
                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        val id = auth.uid
                        updateUI(user)
                        registerUser(email, name, id , password, userSort)
                    }
                    else{
                        Log.d(TAG,"회원가입 실패",task.exception)
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        else {
            if (isBlank) {   // 작성 안한 항목이 있을 경우
                Toast.makeText(this, "빈 칸을 모두 채워 주세요",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateUI(user: FirebaseUser?){
        if(user != null){
            if(clickBtn == "customer"){
                // 로그인 화면으로 이동
                userSort = false
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                curUser = Firebase.auth.currentUser!!
            }else if(clickBtn == "supervisor"){
                // 로그인 화면으로 이동
                userSort = true
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                curUser = Firebase.auth.currentUser!!

            }
        }
    }

    private fun registerUser(userEmail:String,userName: String,uID:String?, userPW:String, userSort:Boolean){
        val user = User(userEmail, userName, uID , userPW, userSort)
        database.child("users").push().setValue(user)
    }
}