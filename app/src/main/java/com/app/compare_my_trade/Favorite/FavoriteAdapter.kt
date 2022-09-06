package com.example.kotlin_project1.Favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.ui.home.HomeViewModel
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.DeclinedBid.DeclinedBidsAdapter
import com.example.kotlin_project1.DeclinedBid.DeclinedBidsModel
import okhttp3.internal.notify
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.util.ArrayList
import java.util.HashMap


class FavoriteAdapter (private var mList: List<FavoriteModel>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

//    private  lateinit var mlistner: onItemClickListener

//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }
//    fun setOnItemClickListener(listener: onItemClickListener){
//        mlistner = listener
//    }


    private  lateinit var mlistner2: onItemClickListener2

    interface onItemClickListener2{
        fun onItemClick2(position: Int)
    }
    fun setOnItemClickListener2(listener2: onItemClickListener2){
        mlistner2 = listener2
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite, parent, false)

        return ViewHolder(view,mlistner2)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val FavoriteModel = mList[position]


        Glide.with(holder.itemView).load(FavoriteModel.img1).fitCenter().into(holder.imageView)



        holder.textView1.text = FavoriteModel.text1
        holder.textView2.text = FavoriteModel.text2
        holder.textView3.text = FavoriteModel.text3
        holder.textView4.text = FavoriteModel.status
        holder.model.text = FavoriteModel.model

        holder.itemView.setOnClickListener {




            var pid = FavoriteModel.id


            val intent = Intent(it.context, CarDetails::class.java).apply {
                putExtra("v_id",pid)
                putExtra("fav_bid_now","fav_bid_now")

            }
            it.context.startActivity(intent)


        }




    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun filterList(filteredCourseList: ArrayList<FavoriteModel>) {
        this.mList = filteredCourseList;
        notifyDataSetChanged();
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener2: FavoriteAdapter.onItemClickListener2) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.shapeableImageView)
        val textView1: TextView = itemView.findViewById(R.id.name)
        val textView2: TextView = itemView.findViewById(R.id.price)
        val textView3: TextView = itemView.findViewById(R.id.date)
        val textView4: TextView = itemView.findViewById(R.id.status)
        val model: TextView = itemView.findViewById(R.id.model)
        val favorite_but:ImageButton = itemView.findViewById(R.id.favorite_icon)

        init {
//            itemView.setOnClickListener {
//
//                listener.onItemClick(adapterPosition)
//            }

            favorite_but.setOnClickListener {

                listener2.onItemClick2(adapterPosition)

            }

        }
    }




}