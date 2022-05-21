package com.example.corder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerMenuAdapter(private val items: ArrayList<ListMenu>) : RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder>(){
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerMenuAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "number: " + item.number + "menuName: " + item.menuName + "menuCost: " + item.menuCost, Toast.LENGTH_SHORT).show()
//            var intent = Intent(holder.itemView?.context, UserCafeInfoActivity::class.java)
//            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
        holder.apply {
            bind(listener, item)
//            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerMenuAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_menu, parent, false)
        return RecyclerMenuAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view:View = v
        private val menu: ImageView = view.findViewById(R.id.menu)
        private val menuName: TextView = view.findViewById(R.id.menuName)
        private val menuCost: TextView = view.findViewById(R.id.menuCost)
        fun bind(listener: View.OnClickListener, item: ListMenu){
            menu.setImageDrawable(item.img)
            menuName.setText(item.menuName)
            menuCost.setText(item.menuCost)
            view.setOnClickListener(listener)
        }
    }

}
