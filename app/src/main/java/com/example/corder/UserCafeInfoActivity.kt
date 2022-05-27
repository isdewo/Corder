package com.example.corder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.corder.databinding.ActivityMainBinding
import com.example.corder.databinding.ActivityUserCafeInfoBinding

class UserCafeInfoActivity : AppCompatActivity() {
////    private var mBinding : ActivityUserCafeInfoBinding? = null
////    private val binding get() = mBinding!!
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_user_cafe_info)
////        mBinding = ActivityUserCafeInfoBinding.inflate(layoutInflater)
////        setContentView(binding.root)
//
//        val listMenu = findViewById<RecyclerView>(R.id.menuList)
//        val linearLayoutManager = LinearLayoutManager(this)
//
////        binding.listMenu.layoutManager = linearLayoutManager
//        listMenu.layoutManager = linearLayoutManager
//
//        var list = ArrayList<ListMenu>()
//        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 1, "아메리카노", 3000))
//        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 2, "카페라떼", 3500))
//        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 3, "요거트스무디", 5500))
//
//        val adapter = RecyclerMenuAdapter(list)
////        binding.listMenu.adapter = adapter
//        listMenu.adapter = adapter
//    }

    private var mBinding : ActivityUserCafeInfoBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cafe_info)
        mBinding = ActivityUserCafeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val listCafe = findViewById<RecyclerView>(R.id.listCafe)
        val linearLayoutManager = LinearLayoutManager(this)
//        binding.menuList.layoutManager = linearLayoutManager
        listCafe.layoutManager = linearLayoutManager

        var list = ArrayList<ListMenu>()
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 1, "아메리카노", 3000))
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 2, "카페라떼", 3500))
        list.add(ListMenu(ContextCompat.getDrawable(this, R.drawable.americano)!!, 3, "요거트스무디", 5500))

        val adapter = RecyclerMenuAdapter(list)
//        binding.menuList.adapter = adapter
        listCafe.adapter = adapter
    }
}