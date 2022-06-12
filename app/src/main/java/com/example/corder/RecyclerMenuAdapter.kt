package com.example.corder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_menu.view.*


class RecyclerMenuAdapter(private val items: ArrayList<ListMenu>, private val onClick: (ListMenu) -> Unit) : RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerMenuAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
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
        private  val view: View = v
        fun bind(listener: View.OnClickListener, item: ListMenu){
            view.menu.setImageDrawable(item.img)
            view.menuName.text = item.menuName
            view.menuCost.text = item.menuCost.toString()
            view.setOnClickListener(listener)
        }
    }
}