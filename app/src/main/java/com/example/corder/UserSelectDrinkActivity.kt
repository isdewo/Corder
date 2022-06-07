package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_user_select_drink.*

class UserSelectDrinkActivity : AppCompatActivity() {
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
        val clickPushbtn = findViewById<Button>(R.id.push)
        clickPushbtn.setOnClickListener {
            var intent = Intent(this, UserCartActivity::class.java)
            startActivity(intent)
        }

    }

}