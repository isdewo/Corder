package com.example.corder

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join.*
import kotlin.random.Random

class JoinActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database.reference
    private lateinit var curUser: FirebaseUser

    private var userSort = 0
    private var isBlank = false // 빈칸 확인
    private var clickBtn = 0
    private var userType = ""

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = FirebaseAuth.getInstance()

        radioGroup.setOnCheckedChangeListener{ _, checkedId ->
            when(checkedId){
                R.id.customerBtn -> {
                    clickBtn = 0
                }
                R.id.supervisorBtn -> {
                    clickBtn = 1
                }
            }
        }

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
            val userNumb = Random.nextInt(0,200)
            userSort = userNumb
            userType = "supervisor"
        }

        val customerButton = findViewById<Button>(R.id.customerBtn)
        customerButton.setOnClickListener {
            userSort = 0
            userType = "customer"
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
                        registerUser(email, name, id, password, userSort)
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
            if(clickBtn == 0){
                // 로그인 화면으로 이동
                Toast.makeText(this, "customer로 회원가입 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                curUser = Firebase.auth.currentUser!!
            }else if(clickBtn == 1){
                // 가게정보 등록 화면으로 이동
                Toast.makeText(this, "supervisor로 회원가입 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, OwnerCafeInfoActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                curUser = Firebase.auth.currentUser!!
            }
        }
    }
    
    private fun registerUser(userEmail:String,userName: String,uID:String?, userPW:String, userSort:Int){
        val user = User(userEmail, userName, uID , userPW, userSort)
        database.child("users").push().setValue(user)
    }
}
