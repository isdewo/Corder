package com.example.corder

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val joinButton = findViewById<Button>(R.id.goJoin)
        joinButton.setOnClickListener {
            // 회원가입
            startActivity(Intent(this, JoinActivity::class.java))
        }

        val loginButton= findViewById<Button>(R.id.gotoLogin)
        loginButton.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val em = findViewById<EditText>(R.id.et_email)
        val email = em.text.toString()
        val pw = findViewById<EditText>(R.id.et_Password)
        val password = pw.text.toString()

        if(email.isBlank()|| password.isBlank()){
            Toast.makeText(this, "빈 칸을 모두 채워 주세요", Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    //로그인 성공
                    val user = auth.currentUser
                    Toast.makeText(this, "환영합니다!",Toast.LENGTH_SHORT).show()
                    loginSave(email,password)
                    updateUI(user)
                }
                else{
                    //로그인 실패
                    Toast.makeText(this, "로그인 실패",Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

    }

    private fun loginSave(a:String,b:String){
        val pref = getSharedPreferences("preferences", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString("email", a)
        editor.putString("password",b)
        editor.putBoolean("login", true)
        editor.apply()
    }

    private fun updateUI(user: FirebaseUser? ){
        if(user != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }
}