package com.example.corder

import com.google.firebase.database.core.view.View


interface ImageClickListener {

    fun onItemClick(holder:RecyclerMainAdapter.ViewHolder?, view: View?, position: Int){


    }
}