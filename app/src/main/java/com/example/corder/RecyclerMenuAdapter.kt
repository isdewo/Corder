package com.example.corder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class RecyclerMenuAdapter(private val items: ArrayList<ListMenu>, private val onClick: (ListMenu) -> Unit) : RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: RecyclerMenuAdapter.ViewHolder, position: Int) {
        val item = items[position]

        val listener = View.OnClickListener { it->
            item.let {
                onClick(item)
            }
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerMenuAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_menu, parent, false)
        return RecyclerMenuAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        val menu = view.findViewById<ImageView>(R.id.menu)
        val menuname = view.findViewById<TextView>(R.id.menuName)
        val menucost = view.findViewById<TextView>(R.id.menuCost)

        fun bind(listener: View.OnClickListener, item: ListMenu){
            Glide.with(menu.context).load(item.img).error(R.drawable.examplecoffee).into(menu)
            menuname.setText(item.menuName)
            menucost.setText(item.menuCost.toString())
            view.setOnClickListener(listener)
        }
    }
}
