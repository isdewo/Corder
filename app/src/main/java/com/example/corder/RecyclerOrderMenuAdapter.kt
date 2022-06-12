package com.example.corder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_ordermenu.view.*


class RecyclerOrderMenuAdapter(private var items: ArrayList<ListOrderMenu>, private val onClick: (ListOrderMenu) -> Unit) : RecyclerView.Adapter<RecyclerOrderMenuAdapter.ViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerOrderMenuAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it->
            item.let{
                onClick(item)
            }
        }
        holder.apply{
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerOrderMenuAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_ordermenu, parent, false)
        return RecyclerOrderMenuAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: ListOrderMenu){
            view.menuName.text = item.menuName
            view.menuEA.text = item.countMenu.toString()
            view.menuCost.text = item.menuCost.toString()
        }
    }
}