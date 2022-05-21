package com.example.corder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerMainAdapter(private val items: ArrayList<ListData>) : RecyclerView.Adapter<RecyclerMainAdapter.ViewHolder>(){
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerMainAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "cName: " + item.cName + " Number: " + item.number, Toast.LENGTH_SHORT).show()
//            var intent = Intent(items, UserCafeInfoActivity::class.java)
//            ContextCompat.startActivity(holder.itemView.context, intent, null)
//            startActivity(intent)
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerMainAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecyclerMainAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var  view: View = v
        private val ivImg: ImageView = view.findViewById(R.id.ivImg)
        private val tvCname: TextView = view.findViewById((R.id.tvCname))
        fun bind(listener: View.OnClickListener, item: ListData){
            ivImg.setImageDrawable(item.img)
            tvCname.setText(item.cName)
            view.setOnClickListener(listener)
        }
    }
}
