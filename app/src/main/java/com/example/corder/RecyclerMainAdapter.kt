package com.example.corder

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*
import java.net.URI

class RecyclerMainAdapter(private var items: ArrayList<ListData>, private val onClick: (ListData) -> Unit) : RecyclerView.Adapter<RecyclerMainAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: RecyclerMainAdapter.ViewHolder, position: Int) {
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
    ): RecyclerMainAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecyclerMainAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        var ivImg = view.findViewById<ImageView>(R.id.ivImg)
        var tvCname = view.findViewById<TextView>(R.id.tvCname)
        fun bind(listener: View.OnClickListener, item: ListData) {
//            var uri = Uri.parse(item.imgUri)
            Glide.with(ivImg.context).load(item.imgUri).into(ivImg)
//            ivImg.setImageURI(uri)
//            ivImg.setImageURI(Uri.parse(item.imgUrl))
            tvCname.setText(item.cafeName)
            view.setOnClickListener(listener)
        }
    }
}
