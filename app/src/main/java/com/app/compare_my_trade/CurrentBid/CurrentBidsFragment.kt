package com.example.kotlin_project1.CurrentBid

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.PlanDetail
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.ui.postauthenticationui.ui.home.HomeViewModel
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.example.kotlin_project1.WonBid.WonBidsAdapter
import com.example.kotlin_project1.WonBid.WonBidsModel
import com.github.ybq.android.spinkit.SpinKitView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.nio.charset.Charset
import java.text.ParsePosition
import java.util.HashMap
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import kotlin.properties.Delegates


class CurrentBidsFragment : Fragment() {

    lateinit var recyclerview: RecyclerView

    var color by Delegates.notNull<Int>()

    lateinit var progresstext:TextView
    lateinit var spinKitView: SpinKitView

    lateinit var bid_price:String

    var adapter: CurrentBidsAdapter? = null
//    lateinit var p_id:String
//
//    var V_name : String? = null
//    var V_body_type: String? = null
//    var V_advertisement_id: String? = null
//    var front_image: String? = null
    @Nullable
    @Override
     override fun onCreateView(inflater: LayoutInflater,@Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_current_bids, container, false)


        recyclerview = view.findViewById(R.id.recyclerview1)
    color = ContextCompat.getColor(view.context, R.color.swiping_yellow)

    progresstext = view.findViewById(R.id.progressText)
    spinKitView = view.findViewById(R.id.progressBar)

        current_bids()




        return view
    }

    private fun current_bids() {

        spinKitView.visibility = View.VISIBLE
        val URL = "http://motortraders.zydni.com/api/buyers/biddings"

        val queue = Volley.newRequestQueue(activity)
        val model = ArrayList<CurrentBidsModel>()

        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->
                spinKitView.visibility = View.GONE
                try{

                    progresstext.visibility = View.VISIBLE

                val res = JSONObject(response)

                var successfulBids = res.getJSONArray("currentBids")
                recyclerview.layoutManager = LinearLayoutManager(activity)



                for (i in 0 until successfulBids.length()) {
                    val data: JSONObject = successfulBids.getJSONObject(i)


                    try {

                        progresstext.visibility = View.GONE


                        bid_price =data.getString("bid_price")
                        var product =data.getJSONObject("product")
                        var v_name = product.getString("brand_name")+" "+product.getString("model_name")
                        var body_type =product.getString("body_type")
                        var advertisement_id =product.getString("advertisement_id")
                        var published_at =product.getString("published_at")
                        var front_image =product.getString("front_image")
                        var seller =data.getJSONObject("seller")
                        var product_id =product.getString("id")
                        var bid_id = data.getString("id")
                        var v_model =  product.getString("model_year")
                   // var last_name =seller.getString("last_name")




                    model.add(CurrentBidsModel(front_image, published_at,v_name,v_model,advertisement_id,bid_price,product_id,bid_id))


                    } catch (e: JSONException) {

                    }

//                    Toast.makeText(activity,first_name.toString(),Toast.LENGTH_LONG).show()
//                    Log.i("jdhfisd",first_name.toString())

                }
                recyclerview.layoutManager = LinearLayoutManager(activity)





//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img_6, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))
//        data.add(CurrentBidsModel(R.drawable.img, "12 feb 2005","BMW 7 series (2005)","73OLD Seden","AD ID - 123456789"))



                adapter = CurrentBidsAdapter(model)

                recyclerview.adapter = adapter

                recyclerview.setItemViewCacheSize(10000)
                Log.wtf("size_of_my_list", Integer.toString(model.size));
                adapter!!.notifyDataSetChanged()
                Log.d("debugMode", "The onCreateView method has been launched");

                adapter!!.setOnItemClickListener(object :CurrentBidsAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val mod: CurrentBidsModel = model.get(position)

                         var pid = mod.p_id
                        var price = mod.text6
                        var bid = mod.b_id

//                        V_name = mod.text2
//                        V_body_type= mod.text3
//                        V_advertisement_id= mod.text4
//                        front_image= mod.image


                        val intent = Intent(activity, CarDetails::class.java).apply {
                            putExtra("v_id",pid)
                            putExtra("b_id",bid)
                            putExtra("type","current_bids")
                            putExtra("my_bid",price)
                        }
                        startActivity(intent)
                    }

                })




                }catch (e: Exception){

                }

            }, Response.ErrorListener { error ->

                spinKitView.visibility = View.GONE


                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(activity,"Check your internet connection", Toast.LENGTH_LONG).show()
                }

            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)




//        val icon = R.drawable.edit
//
//        val callback: ItemTouchHelper.SimpleCallback = object :
//            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//
//                val mod: CurrentBidsModel = model.get(viewHolder.absoluteAdapterPosition)
//
//                var p_id = mod.p_id
//
//                var V_name = mod.text2
//                var V_body_type= mod.text3
//                var V_advertisement_id= mod.text4
//                var front_image= mod.image
//                var bidId =  mod.b_id
//
//                try {
//
//                    when(direction){
//
//                        ItemTouchHelper.LEFT->{
//
//
//                            val dialog = Dialog(view!!.context)
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//                            dialog.setContentView(R.layout.activity_bid_now)
//
//                            val textView = dialog.findViewById<TextView>(R.id.close)
//                            val bid_name = dialog.findViewById<TextView>(R.id.bid_name)
//                            val type = dialog.findViewById<TextView>(R.id.type)
//                            val aid = dialog.findViewById<TextView>(R.id.aidid)
//                            val Aid = dialog.findViewById<TextView>(R.id.AdvertisementID)
//                            val bid = dialog.findViewById<LinearLayout>(R.id.bid)
//                            val bid_text = dialog.findViewById<TextView>(R.id.bid_text)
//                            val img = dialog.findViewById<ImageView>(R.id.bid_img)
//                            val price = dialog.findViewById<EditText>(R.id.bid_price)
//                            var Error = dialog.findViewById<TextView>(R.id.error)
//
//                            bid_text.text = "UPDATE AMOUNT"
//                            bid_name.text = V_name
//                            type.text = V_body_type
//                            aid.text = V_advertisement_id
//                            Aid.text = V_advertisement_id
//                            price.setText(bid_price)
//                            Glide.with(view!!.context).load(front_image).fitCenter().into(img)
//
//
//                            textView.setOnClickListener {
//
//                                dialog.dismiss()
//                            }
//
//                            dialog.setCancelable(false)
//
//
//                            val width = (resources.displayMetrics.widthPixels * 1.00).toInt()
//                            val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//                            //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
////            dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.MATCH_PARENT)
//                            dialog.show()
//
//                            bid.setOnClickListener {
//
//
//                                val URL = "http://motortraders.zydni.com/api/buyers/bid/update/"+bidId
//
//                                val queue = Volley.newRequestQueue(activity)
//
//
//                                val request: StringRequest = object : StringRequest(
//                                    Method.POST, URL,
//                                    Response.Listener { response ->
//
//                                        dialog.dismiss()
//
//                                        dialog.dismiss()
//                                        val dialog = Dialog(it.context)
//                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//                                        dialog.setContentView(R.layout.alert_dialogbox)
//                                        dialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.currentbackground);
//
//                                        val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
//                                        val oktext = dialog.findViewById<TextView>(R.id.yestext)
//                                        val cancel_text = dialog.findViewById<TextView>(R.id.no_text)
//                                        val title = dialog.findViewById<TextView>(R.id.title)
//                                        val massage = dialog.findViewById<TextView>(R.id.message)
//
//                                        title.setText("Success")
//                                        oktext.text = "Ok"
//                                        massage.setText("You update successful")
//
//                                        cancel_text.visibility  = View.INVISIBLE
//                                        ok.setOnClickListener {
//
//                                            dialog.dismiss()
//                                        }
//
////            val body = dialog.findViewById(R.id.body) as TextView
////            body.text = title
////            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
////            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
////            yesBtn.setOnClickListener {
////                dialog.dismiss()
////            }
////            noBtn.setOnClickListener { dialog.dismiss() }
//
//                                        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//                                        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                                        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//                                        //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
//                                        // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
//                                        dialog.show()
//
//
//
//                                    }, Response.ErrorListener { error ->
//
//                                        Error.visibility = View.VISIBLE
//
//
//                                        try {
//
//                                            val charset: Charset = Charsets.UTF_8
//
//                                            val jsonObject = String(error.networkResponse.data, charset)
//                                            val data = JSONObject(jsonObject)
//                                            val errors: JSONObject = data.getJSONObject("errors")
//
//                                            var bp : JSONArray? = null
//                                            var msg : JSONArray? = null
////                                msg = errors.getJSONArray("message")
////                                            Log.i("hfjdgskdjkefsd",jsonObject)
////                                            Log.e("fdsfsdfsfs",jsonObject)
////                                            Toast.makeText(activity,jsonObject,Toast.LENGTH_LONG).show()
//                                            bp = errors.getJSONArray("bid_price")
//                                            if (bp.equals(null)) {
//
//
//                                            }else{
//                                                val bid_price: JSONArray = errors.getJSONArray("bid_price")
//                                                Error.setText(bid_price.getString(0))
//                                            }
//
//                                        } catch (e: JSONException) {
//                                        } catch (error: UnsupportedEncodingException) {
//                                        }
//
//                                        try {
//
//                                            val charset: Charset = Charsets.UTF_8
//
//                                            val jsonObject = String(error.networkResponse.data, charset)
//                                            val data = JSONObject(jsonObject)
//                                            val errors: JSONObject = data.getJSONObject("errors")
//
//                                            var msg : JSONArray? = null
//                                            msg = errors.getJSONArray("message")
//                                            var ms = msg.getString(0)
////                                if (msg.equals(null)) {
////
////                                    AlertDialog.Builder(this)
////                                        .setTitle("Success")
////                                        .setMessage("You need Subscription before continuing")
////                                        .setNegativeButton(android.R.string.no, null)
////                                        .setPositiveButton(android.R.string.yes,  DialogInterface.OnClickListener { dialogInterface, i ->
////                                            val intent = Intent(this@CarDetails, PlanDetail::class.java).apply {
////                                            }
////                                            startActivity(intent)}
////                                        ).create().show()
////
////                                }else
//                                            if(ms.equals("Your bidding already exists.")) {
//                                                val message: JSONArray = errors.getJSONArray("message")
//                                                Error.setText(message.getString(0))
//                                            }
//                                            if (ms.equals("Unauthenticated")) {
//                                                dialog.dismiss()
//                                                AlertDialog.Builder(view!!.context)
//                                                    .setTitle("Success")
//                                                    .setMessage("You need Subscription before continuing")
//                                                    .setNegativeButton(android.R.string.no, null)
//                                                    .setPositiveButton(android.R.string.yes,  DialogInterface.OnClickListener { dialogInterface, i ->
//                                                        val intent = Intent(activity, PlanDetail::class.java).apply {
//                                                        }
//                                                        startActivity(intent)}
//                                                    ).create().show()
//
//                                            }else Error.setText("Enter max range value 6 digit")
//
//                                        } catch (e: JSONException) {
//                                        } catch (error: UnsupportedEncodingException) {
//                                        }
//
//                                    }) {
//
//
//                                    @Throws(AuthFailureError::class)
//                                    override fun getHeaders(): Map<String, String> {
//                                        val headers = HashMap<String, String>()
//                                        headers.put("Accept", "application/json")
//                                        headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))
//                                        return headers
//                                    }
//
//                                    override fun getParams(): Map<String, String>? {
//
//                                        val params: MutableMap<String, String> = HashMap()
//
//
//                                        var bid_price = price.text.toString()
//                                        params["bid_price"] = bid_price
//
//
//                                        return params
//
//
//                                    }
//
//
//                                }
//                                request.retryPolicy = DefaultRetryPolicy(
//                                    10000,
//                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                                )
//                                queue.add(request)
//
//
//
//                            }
//                            adapter!!.notifyDataSetChanged()
//                        }
//                        ItemTouchHelper.RIGHT->{
//
//
//                            val dialog = Dialog(view!!.context)
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//                            dialog.setContentView(R.layout.activity_bid_now)
//
//                            val textView = dialog.findViewById<TextView>(R.id.close)
//                            val bid_name = dialog.findViewById<TextView>(R.id.bid_name)
//                            val type = dialog.findViewById<TextView>(R.id.type)
//                            val aid = dialog.findViewById<TextView>(R.id.aidid)
//                            val Aid = dialog.findViewById<TextView>(R.id.AdvertisementID)
//                            val bid = dialog.findViewById<LinearLayout>(R.id.bid)
//                            val img = dialog.findViewById<ImageView>(R.id.bid_img)
//                            val price = dialog.findViewById<EditText>(R.id.bid_price)
//                            var Error = dialog.findViewById<TextView>(R.id.error)
//                            val bid_text = dialog.findViewById<TextView>(R.id.bid_text)
//
//
//                            bid_text.text = "UPDATE AMOUNT"
//                            bid_name.text = V_name
//                            type.text = V_body_type
//                            aid.text = V_advertisement_id
//                            Aid.text = V_advertisement_id
//                            price.setText(bid_price)
//                            Glide.with(view!!.context).load(front_image).fitCenter().into(img)
//
//
//                            textView.setOnClickListener {
//
//                                dialog.dismiss()
//                            }
//
//                            dialog.setCancelable(false)
//
//
//                            val width = (resources.displayMetrics.widthPixels * 1.00).toInt()
//                            val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//                            //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
////            dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.MATCH_PARENT)
//                            dialog.show()
//
//                            bid.setOnClickListener {
//
//                                dialog.dismiss()
//
//                                val URL = "http://motortraders.zydni.com/api/buyers/bid/update/"+bidId
//
//                                val queue = Volley.newRequestQueue(activity)
//
//
//                                val request: StringRequest = object : StringRequest(
//                                    Method.POST, URL,
//                                    Response.Listener { response ->
//
//                                        dialog.dismiss()
//                                        val dialog = Dialog(it.context)
//                                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//                                        dialog.setContentView(R.layout.alert_dialogbox)
//                                        dialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.currentbackground);
//
//                                        val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
//                                        val oktext = dialog.findViewById<TextView>(R.id.yestext)
//                                        val cancel_text = dialog.findViewById<TextView>(R.id.no_text)
//                                        val title = dialog.findViewById<TextView>(R.id.title)
//                                        val massage = dialog.findViewById<TextView>(R.id.message)
//
//                                        title.setText("Success")
//                                        oktext.text = "Ok"
//                                        massage.setText("You update successful")
//
//                                        cancel_text.visibility  = View.INVISIBLE
//                                        ok.setOnClickListener {
//
//                                            dialog.dismiss()
//                                        }
//
////            val body = dialog.findViewById(R.id.body) as TextView
////            body.text = title
////            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
////            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
////            yesBtn.setOnClickListener {
////                dialog.dismiss()
////            }
////            noBtn.setOnClickListener { dialog.dismiss() }
//
//                                        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//                                        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                                        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//                                        //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
//                                        // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
//                                        dialog.show()
//
//
//                                    }, Response.ErrorListener { error ->
//
//                                        Error.visibility = View.VISIBLE
//
//
//                                        try {
//
//                                            val charset: Charset = Charsets.UTF_8
//
//                                            val jsonObject = String(error.networkResponse.data, charset)
//                                            val data = JSONObject(jsonObject)
//                                            val errors: JSONObject = data.getJSONObject("errors")
//
//                                            var bpp : JSONArray? = null
//
//                                            bpp = errors.getJSONArray("bid_price")
//                                            if (bpp.equals(null)) {
//
//
//                                            }else{
//                                                val bid_price: JSONArray = errors.getJSONArray("bid_price")
//                                                Error.setText(bid_price.getString(0))
//                                            }
//
//                                        } catch (e: JSONException) {
//                                        } catch (error: UnsupportedEncodingException) {
//                                        }
//
//                                        try {
//
//                                            val charset: Charset = Charsets.UTF_8
//
//                                            val jsonObject = String(error.networkResponse.data, charset)
//                                            val data = JSONObject(jsonObject)
//                                            val errors: JSONObject = data.getJSONObject("errors")
//
//                                            var msgg : JSONArray? = null
//                                            msgg = errors.getJSONArray("message")
//                                            var ms = msgg.getString(0)
////                                if (msg.equals(null)) {
////
////                                    AlertDialog.Builder(this)
////                                        .setTitle("Success")
////                                        .setMessage("You need Subscription before continuing")
////                                        .setNegativeButton(android.R.string.no, null)
////                                        .setPositiveButton(android.R.string.yes,  DialogInterface.OnClickListener { dialogInterface, i ->
////                                            val intent = Intent(this@CarDetails, PlanDetail::class.java).apply {
////                                            }
////                                            startActivity(intent)}
////                                        ).create().show()
////
////                                }else
//                                            if(ms.equals("Your bidding already exists.")) {
//                                                val message: JSONArray = errors.getJSONArray("message")
//                                                Error.setText(message.getString(0))
//                                            }
//                                            if (ms.equals("Unauthenticated")) {
//                                                dialog.dismiss()
//                                                AlertDialog.Builder(view!!.context)
//                                                    .setTitle("Success")
//                                                    .setMessage("You need Subscription before continuing")
//                                                    .setNegativeButton(android.R.string.no, null)
//                                                    .setPositiveButton(android.R.string.yes,  DialogInterface.OnClickListener { dialogInterface, i ->
//                                                        val intent = Intent(activity, PlanDetail::class.java).apply {
//                                                        }
//                                                        startActivity(intent)}
//                                                    ).create().show()
//
//                                            }else Error.setText("Enter max range value 6 digit")
//
//                                        } catch (e: JSONException) {
//                                        } catch (error: UnsupportedEncodingException) {
//                                        }
//
//                                    }) {
//
//
//                                    @Throws(AuthFailureError::class)
//                                    override fun getHeaders(): Map<String, String> {
//                                        val headers = HashMap<String, String>()
//                                        headers.put("Accept", "application/json")
//                                        headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(activity))
//                                        return headers
//                                    }
//
//                                    override fun getParams(): Map<String, String>? {
//
//                                        val params: MutableMap<String, String> = HashMap()
//
//
//                                        var bid_price = price.text.toString()
//                                        params["bid_price"] = bid_price
//
//
//                                        return params
//
//
//                                    }
//
//
//                                }
//                                request.retryPolicy = DefaultRetryPolicy(
//                                    10000,
//                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                                )
//                                queue.add(request)
//
//
//
//                            }
//                            adapter!!.notifyDataSetChanged()
//                        }
//
//                    }
//                } catch (e: Exception) {
//                    Log.e("MainActivity", e.message!!)
//                }
//            }
//
//            // You must use @RecyclerViewSwipeDecorator inside the onChildDraw method
//            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//
//                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                    .addSwipeLeftBackgroundColor(color)
//                    .addSwipeRightBackgroundColor(color)
//                    .addSwipeLeftActionIcon(icon)
//                    .addSwipeRightActionIcon(icon)
//                    .create()
//                    .decorate()
//
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(callback)
//        itemTouchHelper.attachToRecyclerView(recyclerview)





    }


}