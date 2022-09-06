package com.app.compare_my_trade.Filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R

class FilterShowAdapter(private val mList: ArrayList<FilterShowModel>) : RecyclerView.Adapter<FilterShowAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filtwr_show, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val FilterModel = mList[position]





        holder.textView1.text = FilterModel.text1


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val textView1= itemView.findViewById<TextView>(R.id.fs_text)



    }
}