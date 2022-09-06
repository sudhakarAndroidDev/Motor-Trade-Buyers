package com.example.kotlin_project1.CurrentBid

import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.login.LoginFragment
import com.app.compare_my_trade.ui.postauthenticationui.ui.home.HomeViewModel
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.util.*

class HomeAdapter (public var mList: ArrayList<HomeViewModel>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {






    interface onItemClickListener{
        fun onItemClick(id: String)
    }
    fun setOnItemClickListener(listener: onItemClickListener){

    }

//    private  lateinit var mlistner2: onItemClickListener2
//
//
//
//
//    interface onItemClickListener2{
//        fun onItemClick2(position: Int)
//    }
//    fun setOnItemClickListener2(listener2: onItemClickListener2){
//        mlistner2 = listener2
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.buyer_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val HomeViewModel = mList[position]





        holder.textView1.text = HomeViewModel.text1
        holder.textView2.text = HomeViewModel.text2
        holder.textView3.text = HomeViewModel.text3
        holder.fuel_type.text = HomeViewModel.fuel_type
        holder.transmission.text = HomeViewModel.transmission
        holder.odometer.text = HomeViewModel.odomewter+".Km"
        holder.drive_type.text = HomeViewModel.drive_type
        holder.model.text = HomeViewModel.model

        var status = HomeViewModel.favorite_status
        Glide.with(holder.itemView).load(HomeViewModel.img1).fitCenter().into(holder.imageView)

//        holder.imageView2.setBackgroundResource(R.drawable.heart2)

        holder.imageView2.setOnClickListener {

            if (PreferenceUtils.getTokan(it.context) == null) {

                val dialog = Dialog(it.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

                dialog.setContentView(R.layout.alert_dialogbox)


                val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                val oktext = dialog.findViewById<TextView>(R.id.yestext)
                val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                val title = dialog.findViewById<TextView>(R.id.title)
                val massage = dialog.findViewById<TextView>(R.id.message)

                title.setText("Alert")
                oktext.text = "OK"
                massage.setText("You need sign in or sign up before continuing")

                cancel.setOnClickListener {

                    dialog.dismiss()
                }
                ok.setOnClickListener {

                    val intent =
                        Intent(it.context, LoginFragment::class.java).apply {
                        }
                    it.context.startActivity(intent)
                    dialog.dismiss()
                }

//            val body = dialog.findViewById(R.id.body) as TextView
//            body.text = title
//            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//            yesBtn.setOnClickListener {
//                dialog.dismiss()
//            }
//            noBtn.setOnClickListener { dialog.dismiss() }


                //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
                // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.show()



            } else {

                val URL = "http://motortraders.zydni.com/api/buyers/favourites/" + HomeViewModel.id

                val queue = Volley.newRequestQueue(it.context)


                val request: StringRequest = object : StringRequest(
                    Method.POST, URL,
                    Response.Listener { response ->

//                    holder.imageView2.setBackgroundResource(R.drawable.heart2)
//                    Toast.makeText(it.context,response.toString(),Toast.LENGTH_LONG).show()

                        if (response.toString().equals("true") && status.equals("false")) {

                            holder.imageView2.setBackgroundResource(R.drawable.heart3)
//                        holder.imageView2.setImageResource(R.drawable.heart2)
                            status = "true"


                        } else if (response.toString().equals("true") && status.equals("true")) {

                            holder.imageView2.setBackgroundResource(R.drawable.ic_favorite_not_selected)

                            status = "false"


                        }


                    }, Response.ErrorListener { error ->
                        // Error.visibility = View.VISIBLE
                        try {
//


                        } catch (e: JSONException) {
                        } catch (error: UnsupportedEncodingException) {
                        }

                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization",
                            "Bearer " + PreferenceUtils.getTokan(it.context))

                        return headers
                    }

                    override fun getParams(): Map<String, String>? {

                        val params: MutableMap<String, String> = HashMap()


                        if (status.equals("false")) {
                            params["type"] = "add"
                        } else if (status.equals("true")) {
                            params["type"] = "remove"
                        }


                        return params


                    }


                }
                request.retryPolicy = DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue.add(request)
            }
        }

        if (status.equals("true")){
            holder.imageView2.setBackgroundResource(R.drawable.heart3)
        }else
        {
            HomeViewModel.id
            holder.imageView2.setBackgroundResource(R.drawable.ic_favorite_not_selected)
        }


        holder.itemView.setOnClickListener {

            var id = HomeViewModel.id

            val intent = Intent(it.context, CarDetails::class.java).apply {
                putExtra("v_id",id)
                putExtra("bid_now","bid_now")

            }
            it.context.startActivity(intent)

        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun close(colse:String) {
        notifyDataSetChanged();
    }

    fun filterList(filteredCourseList: ArrayList<HomeViewModel>) {
        this.mList = filteredCourseList;
        notifyDataSetChanged();
    }

//    override fun getFilter(): Filter? {
//        return exampleFilter
//    }
//
//
//    private val exampleFilter: Filter = object : Filter() {
//        override fun performFiltering(constraint: CharSequence): FilterResults {
//            val filteredList: MutableList<HomeViewModel> = ArrayList<HomeViewModel>()
//            if (constraint == null || constraint.length == 0) {
//                filteredList.addAll(mList!!)
//            } else {
//                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
//                for (item in mList!!) {
//                    if (item.text1.toLowerCase().contains(filterPattern) || item.text2
//                            .toLowerCase().contains(filterPattern)
//                    ) {
//                        filteredList.add(item)
//                    }
//                }
//            }
//            val results = FilterResults()
//            results.values = filteredList
//            return results
//        }
//
//
//        override fun publishResults(constraint: CharSequence, results: FilterResults) {
//
//            mList.clear()
//            mList.addAll(results?.values  as Collection<HomeViewModel>)
//
//
////            mList.clear()
////            mList.addAll(results.values as ArrayList<*>)
////
////            mList.clear()
////            mList.addAll(results.values as List<*>)
//
//            notifyDataSetChanged()
//        }
//
//    }


//    override fun getFilter(): Filter? {
//        return exampleFilter
//    }
//
//    private val exampleFilter: Filter = object : Filter() {
//        override fun performFiltering(constraint: CharSequence): FilterResults {
//            val filteredList: MutableList<HomeViewModel> = ArrayList<HomeViewModel>()
//            if (constraint == null || constraint.length == 0) {
//                filteredList.addAll(mList)
//            } else {
//                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
//                for (item in mList) {
//                    if (item.text2.toLowerCase().contains(filterPattern) || item.text1
//                            .toLowerCase().contains(filterPattern)
//                    ) {
//                        filteredList.add(item)
//                    }
//                }
//            }
//            val results = FilterResults()
//            results.values = filteredList
//            return results
//        }
//
//        override fun publishResults(constraint: CharSequence, results: FilterResults) {
//            mList.clear()
//            mList.addAll(results.values as Collection<HomeViewModel>)
//            notifyDataSetChanged()
//        }
//    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.shapeableImageView)
        val imageView2:ImageButton =  itemView.findViewById(R.id.siv_favorite)
        val textView1: TextView = itemView.findViewById(R.id.mtv_itemTitle)
        val textView2: TextView = itemView.findViewById(R.id.mtv_itemAmount)
        val textView3: TextView = itemView.findViewById(R.id.mtv_itemDate)
        val fuel_type: TextView = itemView.findViewById(R.id.fuel_type)
        val drive_type: TextView = itemView.findViewById(R.id.drive_type)
        val odometer: TextView = itemView.findViewById(R.id.odometer)
        val transmission: TextView = itemView.findViewById(R.id.transmission)
        val card:RelativeLayout = itemView.findViewById(R.id.cardView)
        val model:TextView = itemView.findViewById(R.id.model)




        init {
//            itemView.setOnClickListener {
//
//
//
//
////                val HomeViewModel = mList[adapterPosition]
////
////                var id = HomeViewModel.id
////
////              listener.onItemClick(id)
//            }




        }




    }


}