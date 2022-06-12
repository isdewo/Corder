package com.example.corder

import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_add_menu.*
import kotlinx.android.synthetic.main.activity_user_select_drink.*
import kotlinx.android.synthetic.main.activity_user_select_drink.menuName

class UserSelectDrinkActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_select_drink)

        ivImg.setImageResource(R.drawable.americano)

        val name = intent.getStringExtra("name")
        menuName.setText(name)

        val ea = menuEA.text.toString()
        val cost = intent.getIntExtra("cost", 0)
        val totalPrice = ea.toInt() * cost
        total.setText(totalPrice.toString())

        // 취소버튼 클릭 시 화면 종료
        val goCancelbtn = findViewById<Button>(R.id.cancel)
        goCancelbtn.setOnClickListener {
            finish()
        }

        // 수량 증가 버튼
        val clickUpbtn = findViewById<Button>(R.id.upBtn)
        clickUpbtn.setOnClickListener{
            val ea = menuEA.text.toString()
            menuEA.text = (ea.toInt() + 1).toString()

            val cost = intent.getIntExtra("cost", 0)

            val totalPrice = (ea.toInt() + 1) * cost

            total.setText(totalPrice.toString())
        }

        // 수량 감소 버튼
        val clickDownbtn = findViewById<Button>(R.id.downBtn)
        clickDownbtn.setOnClickListener{
            val ea = menuEA.text.toString()
            if(ea.toInt() != 1){
                menuEA.text = (ea.toInt() - 1).toString()

                val cost = intent.getIntExtra("cost", 0)

                val totalPrice = (ea.toInt() - 1) * cost

                total.setText(totalPrice.toString())
            }
        }

        // 장바구니에 넣기
        val clickPush = findViewById<Button>(R.id.push)
        clickPush.setOnClickListener{
            val user = Firebase.auth.currentUser
            var uid = "0"
            if(user != null){
                uid = user.uid
            }

            val name = menuName.text.toString()
            val cost = total.text.toString().toInt()
            val count = menuEA.text.toString().toInt()


            if (name != "" && cost != -1 && uid != "0") {
                database = Firebase.database.reference
//                Toast.makeText(this, "${imgUri}", Toast.LENGTH_SHORT).show()
                val listOrderMenu =
                    ListOrderMenu(name, cost, count)

                database.child("cart").child(uid).child(name).setValue(listOrderMenu)

                Toast.makeText(this, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
//                var intent = Intent(this, MenuActivity::class.java)
//                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "다시 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}