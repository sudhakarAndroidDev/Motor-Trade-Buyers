package com.app.compare_my_trade.Filter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.app.compare_my_trade.R


class FilterAdapter (private val mList: List<MakeModel>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }

    private  lateinit var mlistner2: onItemClickListener2

    interface onItemClickListener2{
        fun onItemClick2(position: Int)
    }
    fun setOnItemClickListener2(listener2: onItemClickListener2){
        mlistner2 = listener2
    }
//    public val flist: List<FilterModel>
//        get() {
//            TODO()
//        }

    var lastChecked: CheckBox? = null
    var lastCheckedPos = 0
    var seleted_Checked: CheckBox? = null
    var seleted_position= 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filtwr_text, parent, false)

        return ViewHolder(view,mlistner,mlistner2)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val FilterModel = mList[position]



        holder.textView1.text = FilterModel.text1



//        for (i:Int in 0 until mList.size){

            try {


                for (k in 0 until FilterModel.name2.size){

                    var f_name = FilterModel.name2.get(k)
                    Log.i("indjejudeju",f_name+FilterModel.text1)

                    if (FilterModel.text1 != f_name){


                    }else{


                        seleted_position = position

                        seleted_Checked = holder.textView1
                        holder.textView1.isChecked = true
                    }
                }


            }catch (e: Exception){

            }

        if (FilterModel.year.equals("year")){

            holder.textView1.isChecked
            holder.textView1.tag = position


            if (position == 0 ) {
                lastChecked = holder.textView1
                lastCheckedPos = 0
            }


            holder.textView1.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(arg0: CompoundButton?, isChecked: Boolean) {

                    if(isChecked == true){
                        val cb = arg0 as CheckBox


                        val clickedPos = (cb.tag as Int).toInt()


                        if (cb.isChecked) {
                            lastChecked?.setChecked(false)
                            seleted_Checked?.setChecked(false)

                            lastChecked = cb
                            lastCheckedPos = clickedPos
                            holder.textView1.isChecked = true


                        } else lastChecked = null
                    }



                }
            })

        }



//        }




    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    @SuppressLint("ResourceAsColor")
    class ViewHolder(ItemView: View, listener: FilterAdapter.onItemClickListener, listener2: FilterAdapter.onItemClickListener2)  : RecyclerView.ViewHolder(ItemView)  {

        val textView1= itemView.findViewById<CheckBox>(R.id.f_text)
   //    val r_group = itemView.findViewById<RadioGroup>(R.id.r_group)





        init {







            textView1.setOnClickListener {


                if (textView1.isChecked.equals(true))
                {
                    listener.onItemClick(adapterPosition)
                }

                if (textView1.isChecked != true)
                {
                    listener2.onItemClick2(adapterPosition)
                }


            }


        }

    }


}