package com.example.kotlin_project1.CurrentBid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R
import com.bumptech.glide.Glide
import com.example.kotlin_project1.WonBid.WonBidsModel

import java.util.prefs.NodeChangeListener

class CurrentBidsAdapter(private val mList: List<CurrentBidsModel>) : RecyclerView.Adapter<CurrentBidsAdapter.ViewHolder>() {

    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.current_bids, parent, false)

        return ViewHolder(view,mlistner)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val CurrentBidsModel = mList[position]


        Glide.with(holder.itemView).load(CurrentBidsModel.image).fitCenter().into(holder.imageView)

        holder.textView1.text = CurrentBidsModel.text1
        holder.textView2.text = CurrentBidsModel.text2
        holder.textView3.text = CurrentBidsModel.text3
        holder.textView4.text = "Ad ID - "+CurrentBidsModel.text4
        holder.textView6.text = "$"+CurrentBidsModel.text6

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image1)
        val textView1: TextView = itemView.findViewById(R.id.tv1)
        val textView2: TextView = itemView.findViewById(R.id.tv2)
        val textView3: TextView = itemView.findViewById(R.id.tv3)
        val textView4: TextView = itemView.findViewById(R.id.tv4)
        val textView6: TextView = itemView.findViewById(R.id.my_bid_price)
        val view: TextView = itemView.findViewById(R.id.view)


        init {
            itemView.setOnClickListener {

              listener.onItemClick(adapterPosition)
            }


        }

    }
}