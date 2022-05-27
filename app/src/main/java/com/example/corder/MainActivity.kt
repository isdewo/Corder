package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.corder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val goLoginbtn = findViewById<Button>(R.id.goLogin)

        goLoginbtn.setOnClickListener {
            // 자리현황 확인/변경
            startActivity(Intent(this, LoginActivity::class.java))
        }

//        val listCafe = findViewById<RecyclerView>(R.id.listCafe)
        val gridLayoutManager = GridLayoutManager(applicationContext, 3)
        binding.listCafe.layoutManager = gridLayoutManager
//        listCafe.layoutManager = gridLayoutManager

        var list = ArrayList<ListData>()
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 1, "coffee1"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 2, "coffee2"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 3, "coffee3"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 4, "coffee4"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 5, "coffee5"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 6, "coffee6"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 7, "coffee7"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 8, "coffee8"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 9, "coffee9"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 10, "coffee10"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 11, "coffee11"))
        list.add(ListData(ContextCompat.getDrawable(this, R.drawable.coffee)!!, 12, "coffee12"))

        val adapter = RecyclerMainAdapter(list)
        binding.listCafe.adapter = adapter
//        listCafe.adapter = adapter
    }
}