package com.example.corder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.corder.databinding.ActivityUserCafeInfoBinding
import com.google.firebase.firestore.auth.User

class RecyclerMenuAdapter(private val items: ArrayList<ListMenu>) : RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_menu, parent, false)
        return RecyclerMenuAdapter.ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "number: " + items[position].number + "menuName: " + items[position].menuName + "menuCost: " + items[position].menuCost, Toast.LENGTH_SHORT).show()
        }
        holder.bind(listener, items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var v: View) : RecyclerView.ViewHolder(v){
        fun bind(listener: View.OnClickListener, item: ListMenu){
            v.setOnClickListener(listener)
//            v.menuName.text = item.menuName
//            v.cost.text = item.menuCost
        }
}

//class RecyclerMenuAdapter(private val items: ArrayList<ListMenu>) : RecyclerView.Adapter<RecyclerMenuAdapter.ViewHolder>(){
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder {
//        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_menu, parent, false)
//        return ViewHolder(inflatedView)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//
//    override fun onBindViewHolder(holder: RecyclerMenuAdapter.ViewHolder, position: Int) {
//        val item = items[position]
//        val listener = View.OnClickListener { it ->
//            Toast.makeText(it.context, "number: " + item.number + "menuName: " + item.menuName + "menuCost: " + item.menuCost, Toast.LENGTH_SHORT).show()
////            var intent = Intent(holder.itemView?.context, UserCafeInfoActivity::class.java)
////            ContextCompat.startActivity(holder.itemView.context, intent, null)
//        }
//        holder.apply {
//            bind(listener, item)
//            itemView.tag = item
//        }
//    }
//
//    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
//        private var view:View = v
//        private val menu: ImageView = view.findViewById(R.id.menu)
//        private val menuName: TextView = view.findViewById(R.id.menuName)
//        private val menuCost: TextView = view.findViewById(R.id.menuCost)
//        fun bind(listener: View.OnClickListener, item: ListMenu){
//            menu.setImageDrawable(item.img)
//            menuName.setText(item.menuName)
//            menuCost.setText(item.menuCost)
//            view.setOnClickListener(listener)
//        }
//    }


}
