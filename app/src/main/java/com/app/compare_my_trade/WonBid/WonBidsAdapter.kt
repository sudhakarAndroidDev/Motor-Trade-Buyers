package com.example.kotlin_project1.WonBid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R
import com.bumptech.glide.Glide
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.CurrentBid.CurrentBidsModel


class WonBidsAdapter (private val mList: List<WonBidsModel>) : RecyclerView.Adapter<WonBidsAdapter.ViewHolder>() {


    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.won_bids, parent, false)

        return ViewHolder(view,mlistner)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val WonBidsModel = mList[position]



        Glide.with(holder.itemView).load(WonBidsModel.image).fitCenter().into(holder.imageView)

        holder.textView1.text = WonBidsModel.text1
        holder.textView2.text = WonBidsModel.text2
        holder.textView3.text = WonBidsModel.text3
        holder.textView4.text = "Ad ID - "+WonBidsModel.text4
        holder.textView5.text = WonBidsModel.text5
        holder.textView6.text = "$"+WonBidsModel.text6

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener: WonBidsAdapter.onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image2)
        val textView1: TextView = itemView.findViewById(R.id.tv5)
        val textView2: TextView = itemView.findViewById(R.id.tv6)
        val textView3: TextView = itemView.findViewById(R.id.tv7)
        val textView4: TextView = itemView.findViewById(R.id.tv8)
        val textView5: TextView = itemView.findViewById(R.id.s_name)
        val textView6: TextView = itemView.findViewById(R.id.my_bids_price)
        val view: TextView = itemView.findViewById(R.id.view)

        init {
            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)
            }


        }
    }
}