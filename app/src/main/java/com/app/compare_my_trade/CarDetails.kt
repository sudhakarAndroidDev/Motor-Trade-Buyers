package com.app.compare_my_trade


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarImage.CarImage
import com.app.compare_my_trade.Favorite.Favorite
import com.app.compare_my_trade.ui.login.LoginFragment
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.arindicatorview.ARIndicatorView
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.SpinKitView
import kotlinx.android.synthetic.main.activity_car_details.*
import kotlinx.android.synthetic.main.activity_car_details.view.*
import kotlinx.android.synthetic.main.car_image.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class CarDetails : AppCompatActivity() {

    lateinit var arIndicatorView: ARIndicatorView
    lateinit var recyclerview:RecyclerView

    lateinit var v_id:String
    lateinit var bidId:String


    lateinit var v_name:TextView
    lateinit var v_body_type:TextView
    lateinit var v_advertisement_id:TextView
    lateinit var v_status:TextView
    lateinit var v_location:TextView
    lateinit var v_fuel_type:TextView
    lateinit var v_transmission:TextView
    lateinit var v_odometer:TextView
    lateinit var v_drive_type:TextView
    lateinit var v_make:TextView
    lateinit var v_body_type_table:TextView
    lateinit var v_model:TextView
    lateinit var v_model_des:TextView
    lateinit var v_transmission_table:TextView
    lateinit var v_drive_type_table:TextView
    lateinit var v_fuel_type_table:TextView
    lateinit var v_service_log:TextView
    lateinit var v_current_odometer:TextView
    lateinit var v_VIN:TextView
    lateinit var v_price_table:TextView
    lateinit var v_status_table:TextView
    lateinit var post_name:TextView
    lateinit var post_number:TextView
    lateinit var post_email:TextView
    lateinit var post_date:TextView
    lateinit var v_price:TextView
    lateinit var v_registration_no:TextView
    lateinit var update_bids:TextView
    lateinit var mybidprice:TextView
    lateinit var model_year:TextView

    lateinit var Make:TextView
    lateinit var body_type_table:TextView
    lateinit var Model:TextView
    lateinit var model_des:TextView
    lateinit var transmission_table:TextView
    lateinit var drive_type_table:TextView
    lateinit var fuel_type_table:TextView
    lateinit var service_log:TextView
    lateinit var current_odometer:TextView
    lateinit var VIN:TextView
    lateinit var price_table:TextView
    lateinit var status_table:TextView
    lateinit var registration_no:TextView

    var V_name : String? = null
    var V_body_type: String? = null
    var V_advertisement_id: String? = null
    var front_image: String? = null
    var My_bids: String? = null

    lateinit var linearLayout: LinearLayout
    lateinit var status_backround: LinearLayout
    lateinit var seller_avatar:ImageView

    lateinit var favotiteicon:ImageButton

    var favorite_status:String="false"


    lateinit var load: LinearLayout
    lateinit var relativeLayout: RelativeLayout
    lateinit var spinKitView: SpinKitView

    lateinit var V_price:String
    lateinit var bidNow:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)





        v_name = findViewById(R.id.v_name)
        v_body_type= findViewById(R.id.body_type)
        v_advertisement_id= findViewById(R.id.advertisement_id)
        v_status= findViewById(R.id.vehicle_status)
//        v_location= findViewById(R.id.location)
        v_fuel_type= findViewById(R.id.fuel_type)
        v_transmission= findViewById(R.id.transmission)
        v_odometer= findViewById(R.id.odometer)
        v_drive_type= findViewById(R.id.drive_type)
        v_make= findViewById(R.id.make)
        v_body_type_table= findViewById(R.id.body_type_table)
        v_model= findViewById(R.id.model)
        v_model_des= findViewById(R.id.model_des)
        v_transmission_table= findViewById(R.id.transmission_table)
        v_drive_type_table= findViewById(R.id.drive_type_table)
        v_fuel_type_table= findViewById(R.id.fuel_type_table)
        v_service_log= findViewById(R.id.service_log)
        v_current_odometer= findViewById(R.id.current_odometer)
        v_VIN= findViewById(R.id.vehicle_VIN)
        v_price_table= findViewById(R.id.vehicle_price_table)
        v_status_table= findViewById(R.id.vehicle_status_table)
        post_name= findViewById(R.id.post_name)
        post_number= findViewById(R.id.post_number)
        post_email=findViewById(R.id.post_email)
        post_date= findViewById(R.id.post_at)
        v_price= findViewById(R.id.vehicle_price)
        v_registration_no = findViewById(R.id.Registration)
        mybidprice =  findViewById(R.id.my_bid_price)
        model_year = findViewById(R.id.model_year)

        favotiteicon = findViewById(R.id.siv_favorite)


        Make= findViewById(R.id.Make)
        body_type_table= findViewById(R.id.Body_type_table)
        Model= findViewById(R.id.Model)
        model_des= findViewById(R.id.Model_des)
        transmission_table= findViewById(R.id.Transmission_table)
        drive_type_table= findViewById(R.id.Drive_type_table)
        fuel_type_table= findViewById(R.id.Fuel_type_table)
        service_log= findViewById(R.id.Service_log)
        current_odometer= findViewById(R.id.Current_odometer)
        VIN= findViewById(R.id.Vehicle_VIN)
        price_table= findViewById(R.id.Vehicle_price_table)
        status_table= findViewById(R.id.Vehicle_status_table)
        registration_no = findViewById(R.id.registration)

        linearLayout = findViewById(R.id.div)
        status_backround = findViewById(R.id.status_background)
        seller_avatar = findViewById(R.id.seller_avatar)


        relativeLayout = findViewById(R.id.scr)
        spinKitView = findViewById(R.id.progressBar)

//        Make.text= "Make"
//        body_type_table.text= "Body Type"
//        Model.text= "Model"
//        model_des.text= "Model Des"
//        transmission_table.text=  "Transmission"
//        drive_type_table.text= "Drive type"
//        fuel_type_table.text= "FuelT ype"
//        service_log.text= "Service Log"
//        current_odometer.text= "Current Odometer/Mileage"
//        VIN.text= "Vehicle VIN"
//        price_table.text= "vehicle Price"
//        status_table.text= "Vehicle Status"
//        registration_no.text = "Registration No."



        v_id = intent.getStringExtra("v_id").toString()
        My_bids = intent.getStringExtra("my_bid").toString()
        update_bids = findViewById(R.id.bid_text)
        bidId = intent.getStringExtra("b_id").toString()

        if (intent.getStringExtra("n_id").toString() != null){
            val URL = "http://motortraders.zydni.com/api/buyers/notification/mark-as-read/"+intent.getStringExtra("n_id").toString()

            val queue = Volley.newRequestQueue(this)


            val request: StringRequest = object : StringRequest(
                Method.POST, URL,
                Response.Listener { response ->



                    if (response.toString().equals("true")){
                        startService(Intent(this, HomeActivity::class.java).putExtra("n_success","n_success"))
                    }

                }, Response.ErrorListener { error ->

                }) {


                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept", "application/json")
                    headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@CarDetails))
                    return headers
                }




            }
            request.retryPolicy = DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(request)
        }

//        Toast.makeText(this,v_id,Toast.LENGTH_LONG).show()


        res2()
        data()
        Glide.with(applicationContext).load("http://motortraders.zydni.com/images/unknown.png").fitCenter().into(seller_avatar)

        arIndicatorView =  findViewById(R.id.ar_indicator)

        bidNow =  findViewById<LinearLayout>(R.id.bid_now_layout)
        recyclerview =  findViewById<RecyclerView>(R.id.view_image)



        var back = findViewById(R.id.back) as TextView

        back.setOnClickListener {
            if (intent.getStringExtra("bid_now").toString().equals("bid_now")) {
                val intent = Intent(this, HomeActivity::class.java).apply {


                }
                startActivity(intent)
            }else if((intent.getStringExtra("fav_bid_now").toString().equals("fav_bid_now"))){
                val intent = Intent(this, Favorite::class.java).apply {


                }
                startActivity(intent)
            }else{
                super.onBackPressed()
            }
        }



        favotiteicon.setOnClickListener {

            if (PreferenceUtils.getTokan(this) == null) {

                val dialog = Dialog(it.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
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
                        Intent(this, LoginFragment::class.java).apply {
                        }
                    startActivity(intent)
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
                Log.i("dbnjhdfjzdnfjkdz", PreferenceUtils.getTokan(this))
                val URL = "http://motortraders.zydni.com/api/buyers/favourites/" + v_id

                val queue = Volley.newRequestQueue(it.context)


                val request: StringRequest = object : StringRequest(
                    Method.POST, URL,
                    Response.Listener { response ->

//                    holder.imageView2.setBackgroundResource(R.drawable.heart2)
//                    Toast.makeText(it.context,response.toString(),Toast.LENGTH_LONG).show()

                        if (response.toString().equals("true") && favorite_status.equals("false")) {

                            favotiteicon.setBackgroundResource(R.drawable.heart3)
//                        holder.imageView2.setImageResource(R.drawable.heart2)
                            favorite_status = "true"

                        } else if (response.toString()
                                .equals("true") && favorite_status.equals("true")
                        ) {

                            favotiteicon.setBackgroundResource(R.drawable.ic_favorite_not_selected)
                            favorite_status = "false"
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


                        if (favorite_status.equals("false")) {
                            params["type"] = "add"
                        } else if (favorite_status.equals("true")) {
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




        bidNow.setOnClickListener {

            if (PreferenceUtils.getTokan(this) == null) {

                val dialog = Dialog(it.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
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
                        Intent(this, LoginFragment::class.java).apply {
                        }
                    startActivity(intent)
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

                if (intent.getStringExtra("bid_now").toString().equals("bid_now")||intent.getStringExtra("fav_bid_now").toString().equals("fav_bid_now")||intent.getStringExtra("type").toString().equals("current_bids")) {
                    bids()
                }

//                if (intent.getStringExtra("type").toString().equals("current_bids")) {
//                    bidupdate()
//                } else {
//                    bids()
//                }
//            val dialog = Dialog(this)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//            dialog.setContentView(R.layout.activity_bid_now)
//
//            val textView = dialog.findViewById<TextView>(R.id.close)
//            textView.setOnClickListener {
//
//                dialog.dismiss()
//            }
////            val body = dialog.findViewById(R.id.body) as TextView
////            body.text = title
////            val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
////            val noBtn = dialog.findViewById(R.id.noBtn) as TextView
////            yesBtn.setOnClickListener {
////                dialog.dismiss()
////            }
////            noBtn.setOnClickListener { dialog.dismiss() }
//          //  dialog.setCanceledOnTouchOutside(false)
//            dialog.setCancelable(false)
//
//
//            val width = (resources.displayMetrics.widthPixels * 1.00).toInt()
//            val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//         //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
////            dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.MATCH_PARENT)
//            dialog.show()


            }
        }
    }



//    private fun bidupdate() {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//        dialog.setContentView(R.layout.activity_bid_now)
//
//        val textView = dialog.findViewById<TextView>(R.id.close)
//        val bid_name = dialog.findViewById<TextView>(R.id.bid_name)
//        val type = dialog.findViewById<TextView>(R.id.type)
//        val aid = dialog.findViewById<TextView>(R.id.aidid)
//        val Aid = dialog.findViewById<TextView>(R.id.AdvertisementID)
//        val bid = dialog.findViewById<LinearLayout>(R.id.bid)
//        val bid_text = dialog.findViewById<TextView>(R.id.bid_text)
//        val img = dialog.findViewById<ImageView>(R.id.bid_img)
//        val price = dialog.findViewById<EditText>(R.id.bid_price)
//        var Error = dialog.findViewById<TextView>(R.id.error)
//
//        bid_text.text = "UPDATE AMOUNT"
//        bid_name.text = V_name
//        type.text = V_body_type
//        aid.text = V_advertisement_id
//        Aid.text = V_advertisement_id
//        price.setText(V_price)
//        Glide.with(this).load(front_image).fitCenter().into(img)
//
//
//        textView.setOnClickListener {
//
//            dialog.dismiss()
//        }
//
//        dialog.setCancelable(false)
//
//
//        val width = (resources.displayMetrics.widthPixels * 1.00).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//        //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
////            dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.MATCH_PARENT)
//        dialog.show()
//
//        bid.setOnClickListener {
//
//
//
//            val URL = "http://motortraders.zydni.com/api/buyers/bid/update/"+bidId
//
//            val queue = Volley.newRequestQueue(this)
//
//
//            val request: StringRequest = object : StringRequest(
//                Method.POST, URL,
//                Response.Listener { response ->
//
//
//                    dialog.dismiss()
//                    val dialog = Dialog(it.context)
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//                    dialog.setContentView(R.layout.alert_dialogbox)
//                    dialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.currentbackground);
//
//                    val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
//                    val oktext = dialog.findViewById<TextView>(R.id.yestext)
//                    val cancel_text = dialog.findViewById<TextView>(R.id.no_text)
//                    val title = dialog.findViewById<TextView>(R.id.title)
//                    val massage = dialog.findViewById<TextView>(R.id.message)
//
//                    title.setText("Success")
//                    oktext.text = "Ok"
//                    massage.setText("You update successful")
//
//                    cancel_text.visibility  = View.INVISIBLE
//                    ok.setOnClickListener {
//
//                        dialog.dismiss()
//                    }
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
//                    val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//                    val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                    dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//                    //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
//                    // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
//                    dialog.show()
//
//
//                    v_price.text = "$" +price.text.toString()
//
//                }, Response.ErrorListener { error ->
//
//                    Error.visibility = View.VISIBLE
//
//
//                    try {
//
//                        val charset: Charset = Charsets.UTF_8
//
//                        val jsonObject = String(error.networkResponse.data, charset)
//                        val data = JSONObject(jsonObject)
//                        val errors: JSONObject = data.getJSONObject("errors")
//
//                        var bp : JSONArray? = null
//                        var msg : JSONArray? = null
////                                msg = errors.getJSONArray("message")
////                                            Log.i("hfjdgskdjkefsd",jsonObject)
////                                            Log.e("fdsfsdfsfs",jsonObject)
////                                            Toast.makeText(activity,jsonObject,Toast.LENGTH_LONG).show()
//                        bp = errors.getJSONArray("bid_price")
//                        var ms = bp.getString(0)
//                        if (bp.equals(null)) {
//
//
//                        }
//
//
//                        if ( ms.equals("Your subscription plan is ended or expired")) {
//                            dialog.dismiss()
//                            val dialog = Dialog(it.context)
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//                            dialog.setContentView(R.layout.alert_dialogbox)
//                            dialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.currentbackground);
//
//                            val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
//                            val oktext = dialog.findViewById<TextView>(R.id.yestext)
//                            val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
//                            val title = dialog.findViewById<TextView>(R.id.title)
//                            val massage = dialog.findViewById<TextView>(R.id.message)
//
//                            title.setText("Alert")
//                            oktext.text = "Buy"
//                            massage.setText("You don't have any active plan")
//
//                            cancel.setOnClickListener {
//
//                                dialog.dismiss()
//                            }
//                            ok.setOnClickListener {
//
//                                val intent = android.content.Intent(it.context, PlanDetail::class.java)
//                                    .apply {
//                                    }
//                                startActivity(intent)
//                                dialog.dismiss()
//                            }
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
//                            val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//                            val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//                            //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
//                            // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
//                            dialog.show()
//
//
//                        }else{
//                            val bid_price: JSONArray = errors.getJSONArray("bid_price")
//                            Error.setText(bid_price.getString(0))
//                        }
//
//                    } catch (e: JSONException) {
//                    } catch (error: UnsupportedEncodingException) {
//                    }
//
//                    try {
//
//                        val charset: Charset = Charsets.UTF_8
//
//                        val jsonObject = String(error.networkResponse.data, charset)
//                        val data = JSONObject(jsonObject)
//                        val errors: JSONObject = data.getJSONObject("errors")
//
//                        var msg : JSONArray? = null
//                        msg = errors.getJSONArray("message")
//                        var ms = msg.getString(0)
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
//                        if(ms.equals("Your bidding already exists.")) {
//                            val message: JSONArray = errors.getJSONArray("message")
//                            Error.setText(message.getString(0))
//                        }
//                        if (ms.equals("Unauthenticated")) {
//                            dialog.dismiss()
//                            dialog.dismiss()
//                            val dialog = Dialog(it.context)
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//
//                            dialog.setContentView(R.layout.alert_dialogbox)
//                            dialog.getWindow()!!.setBackgroundDrawableResource(R.drawable.currentbackground);
//
//                            val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
//                            val oktext = dialog.findViewById<TextView>(R.id.yestext)
//                            val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
//                            val title = dialog.findViewById<TextView>(R.id.title)
//                            val massage = dialog.findViewById<TextView>(R.id.message)
//
//                            title.setText("Alert")
//                            oktext.text = "Buy"
//                            massage.setText("You don't have any active plan")
//
//                            cancel.setOnClickListener {
//
//                                dialog.dismiss()
//                            }
//                            ok.setOnClickListener {
//
//                                val intent = android.content.Intent(it.context, PlanDetail::class.java)
//                                    .apply {
//                                    }
//                                startActivity(intent)
//                                dialog.dismiss()
//                            }
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
//                            val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
//                            val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//                            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
//                            //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
//                            // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
//                            dialog.show()
//
//
//                        }else Error.setText("Enter max range value 6 digit")
//
//                    } catch (e: JSONException) {
//                    } catch (error: UnsupportedEncodingException) {
//                    }
//
//                }) {
//
//
//                @Throws(AuthFailureError::class)
//                override fun getHeaders(): Map<String, String> {
//                    val headers = HashMap<String, String>()
//                    headers.put("Accept", "application/json")
//                    headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@CarDetails))
//                    return headers
//                }
//
//                override fun getParams(): Map<String, String>? {
//
//                    val params: MutableMap<String, String> = HashMap()
//
//
//                    var bid_price = price.text.toString()
//                    params["bid_price"] = bid_price
//
//
//                    return params
//
//
//                }
//
//
//            }
//            request.retryPolicy = DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            )
//            queue.add(request)
//
//
//
//        }
//    }

    private fun bids() {
        if (PreferenceUtils.getTokan(this) == null) {

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
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
                    Intent(this, LoginFragment::class.java).apply {
                    }
                startActivity(intent)
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

            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
            dialog.setContentView(R.layout.activity_bid_now)

            val textView = dialog.findViewById<TextView>(R.id.close)
            val bid_name = dialog.findViewById<TextView>(R.id.bid_name)
            val type = dialog.findViewById<TextView>(R.id.type)
            val aid = dialog.findViewById<TextView>(R.id.aidid)
            val Aid = dialog.findViewById<TextView>(R.id.AdvertisementID)
            val bid = dialog.findViewById<LinearLayout>(R.id.bid)
            val img = dialog.findViewById<ImageView>(R.id.bid_img)
            val price = dialog.findViewById<EditText>(R.id.bid_price)
            val loading = dialog.findViewById<SpinKitView>(R.id.progressBar)
            var Error = dialog.findViewById<TextView>(R.id.error)

            bid_name.text = V_name
            type.text = V_body_type
            aid.text = V_advertisement_id
            Aid.text = V_advertisement_id
            Glide.with(this).load(front_image).fitCenter().into(img)


            textView.setOnClickListener {

                dialog.dismiss()
            }

            dialog.setCancelable(false)


//            val width = (resources.displayMetrics.widthPixels * 1.00).toInt()
//            val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
//            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
//            dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.show()

            bid.setOnClickListener {

                loading.visibility = View.VISIBLE
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Error.visibility = View.GONE

                val URL = "http://motortraders.zydni.com/api/buyers/bid/add/"+v_id

                val queue = Volley.newRequestQueue(this)


                val request: StringRequest = object : StringRequest(
                    Method.POST, URL,
                    Response.Listener { response ->

                        loading.visibility = View.GONE
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                         Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)



                    }, Response.ErrorListener { error ->

                        Error.visibility = View.VISIBLE
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        loading.visibility = View.GONE



                        if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                            Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                        }else{
                        try {

                            val charset: Charset = Charsets.UTF_8

                            val jsonObject = String(error.networkResponse.data, charset)
                            val data = JSONObject(jsonObject)
                            val errors: JSONObject = data.getJSONObject("errors")


                            var bp: JSONArray? = null


//                                msg = errors.getJSONArray("message")
                            bp = errors.getJSONArray("bid_price")
                            var ms = bp.getString(0)
                            if (bp.equals(null)) {


                            }


                            if (ms.equals("Your subscription plan is ended or expired. Please goto pricing plan page for more information")) {
                                dialog.dismiss()
                                val dialog = Dialog(it.context)
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                                dialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
                                dialog.setContentView(R.layout.alert_dialogbox)


                                val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                                val oktext = dialog.findViewById<TextView>(R.id.yestext)
                                val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                                val title = dialog.findViewById<TextView>(R.id.title)
                                val massage = dialog.findViewById<TextView>(R.id.message)

                                title.setText("Alert")
                                oktext.text = "Buy"
                                massage.setText("Your subscription plan is ended or expired. Please goto pricing plan page")

                                cancel.setOnClickListener {

                                    dialog.dismiss()
                                }
                                ok.setOnClickListener {

                                    val intent =
                                        android.content.Intent(it.context, PlanDetail::class.java)
                                            .apply {
                                            }
                                    startActivity(intent)
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
                                val bid_price: JSONArray = errors.getJSONArray("bid_price")
                                Error.setText(bid_price.getString(0))
                            }
                        } catch (e: JSONException) {
                        } catch (error: UnsupportedEncodingException) {
                        }

                        try {

                            val charset: Charset = Charsets.UTF_8

                            val jsonObject = String(error.networkResponse.data, charset)
                            val data = JSONObject(jsonObject)
                            val errors: JSONObject = data.getJSONObject("errors")

                            var msg: JSONArray? = null
                            msg = errors.getJSONArray("message")
                            var ms = msg.getString(0)
//                                if (msg.equals(null)) {
//
//                                    AlertDialog.Builder(this)
//                                        .setTitle("Success")
//                                        .setMessage("You need Subscription before continuing")
//                                        .setNegativeButton(android.R.string.no, null)
//                                        .setPositiveButton(android.R.string.yes,  DialogInterface.OnClickListener { dialogInterface, i ->
//                                            val intent = Intent(this@CarDetails, PlanDetail::class.java).apply {
//                                            }
//                                            startActivity(intent)}
//                                        ).create().show()
//
//                                }elseYour bidding already exists.
                            if (ms.equals("Your bidding already exists.")) {
                                val message: JSONArray = errors.getJSONArray("message")
                                Error.setText(message.getString(0))
                            } else if (ms.equals("Unauthenticated")) {
                                dialog.dismiss()
                                val dialog = Dialog(it.context)
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

                                dialog.setContentView(R.layout.alert_dialogbox)


                                val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                                val oktext = dialog.findViewById<TextView>(R.id.yestext)
                                val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                                val title = dialog.findViewById<TextView>(R.id.title)
                                val massage = dialog.findViewById<TextView>(R.id.message)

                                title.setText("Alert")
                                oktext.text = "Buy"
                                massage.setText("You don't have any active plan")

                                cancel.setOnClickListener {

                                    dialog.dismiss()
                                }
                                ok.setOnClickListener {

                                    val intent =
                                        android.content.Intent(it.context, PlanDetail::class.java)
                                            .apply {
                                            }
                                    startActivity(intent)
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


                            } else Error.setText("Enter max range value 6 digit")

                        } catch (e: JSONException) {
                        } catch (error: UnsupportedEncodingException) {
                        }
                    }

                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@CarDetails))
                        return headers
                    }

                    override fun getParams(): Map<String, String>? {

                        val params: MutableMap<String, String> = HashMap()


                        var bid_price = price.text.toString()
                        params["bid_price"] = bid_price


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

    }

    private fun data() {

        val URL = "http://motortraders.zydni.com/api/buyers/car-show/"+v_id

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = @SuppressLint("ResourceAsColor")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                linearLayout.visibility = View.VISIBLE
                relativeLayout.visibility = View.VISIBLE
                spinKitView.visibility = View.GONE
                val res = JSONObject(response)
                val hiddenSeller:JSONObject
                val acceptedBid:String
                var vehicleDetail:JSONObject
                vehicleDetail = res.getJSONObject("vehicleDetail")
                hiddenSeller = res.getJSONObject("hiddenSeller")
                acceptedBid = res.getString("acceptedBid")






                var V_id = vehicleDetail.getString("id")
                V_name = vehicleDetail.getString("brand_name")+" "+vehicleDetail.getString("model_name")
                V_body_type= vehicleDetail.getString("model_year")
                var Body_type = vehicleDetail.getString("body_type")
                V_advertisement_id= "Ad ID - "+vehicleDetail.getString("advertisement_id")
                var V_status= vehicleDetail.getString("vehicle_status")
                var V_location= vehicleDetail.getString("name")
                var V_fuel_type= vehicleDetail.getString("fuel_type")
                var Model_year= vehicleDetail.getString("model_year")
                var V_transmission= vehicleDetail.getString("transmission")
                var V_odometer= vehicleDetail.getString("odometer_mileage")+" km"
                var V_drive_type= vehicleDetail.getString("drive_type")
                var V_make= vehicleDetail.getString("brand_name")
                var V_model= vehicleDetail.getString("model_name")
                var V_model_des= vehicleDetail.getString("model_description")
                var V_service_log= vehicleDetail.getString("service_log_book")
                var V_VIN= vehicleDetail.getString("vehicle_vin")
                var Post_name= hiddenSeller.getString("name")
                var Post_number= hiddenSeller.getString("business_phone")
                var Post_email= hiddenSeller.getString("business_email")



                if (acceptedBid.equals("null")){
                }else{
                    val acceptedBidobject = JSONObject(acceptedBid)


                    My_bids = acceptedBidobject.getString("bid_price")


                }

                try {

                    var Post_avatar:String = hiddenSeller.getString("avatar")

                    if(V_status.equals("unavailable") && intent.getStringExtra("type").toString() != "declined"){


                        if(this !=null) {
                            Glide.with(applicationContext).load(Post_avatar).fitCenter().into(seller_avatar)
                        }
                    }
                    if(V_status.equals("sold") && intent.getStringExtra("type").toString() != "declined"){


                        if(this !=null) {
                            Glide.with(applicationContext).load(Post_avatar).fitCenter().into(seller_avatar)
                        }
                    }

                }catch (e: JSONException){

                }

                var Post_date= vehicleDetail.getString("published_at")
                V_price= vehicleDetail.getString("vehicle_price")
                var V_registration_no = vehicleDetail.getString("vehicle_registration_number")




                v_name.text =  V_name
                v_body_type.text= V_body_type
                v_advertisement_id.text= V_advertisement_id

                v_status_table.text= V_status

//                v_location.text= V_location
                v_fuel_type.text= V_fuel_type
                v_transmission.text= V_transmission
                v_odometer.text= V_odometer
                v_drive_type.text= V_drive_type
                v_make.text= V_make
                v_body_type_table.text= Body_type
                v_model.text= V_model
                v_model_des.text= V_model_des
                v_transmission_table.text=  V_transmission
                v_drive_type_table.text= V_drive_type
                v_fuel_type_table.text= V_fuel_type
            //    v_service_log.text= V_service_log
                v_current_odometer.text= V_odometer
                v_VIN.text= V_VIN
                v_price_table.text= "$ "+V_price
                model_year.text = Model_year

                post_date.text= "Posted on : "+Post_date
                if (V_service_log.equals("null")){
                    v_service_log.text= "-"
                }else{
                    v_service_log.text= "view"
                }
                v_service_log.setOnClickListener {
                    if (V_service_log.equals("null")){

                    }else{
                        val viewIntent = Intent("android.intent.action.VIEW",
                            Uri.parse(V_service_log))
                        startActivity(viewIntent)
                    }

                }

                if (intent.getStringExtra("type").toString().equals("current_bids"))
                {

                }else if (intent.getStringExtra("type").toString().equals("won_bids")){


                }else if (intent.getStringExtra("type").toString().equals("declined")){


                }else{
                    v_price.text= "$ "+V_price
                }
                val color = ContextCompat.getColor(this, R.color.blue)


                if (intent.getStringExtra("type").toString().equals("current_bids"))
                {

                    mybidprice.text = "My Bid  Amount"
                    v_price.text = "$" +My_bids
//                    linearLayout.visibility = View.GONE
                    status_backround.setBackgroundResource(R.drawable.v_38)

                }else if (intent.getStringExtra("type").toString().equals("won_bids")){
                    mybidprice.text = "My Bid  Amount"
//                    linearLayout.visibility = View.GONE
                    v_price.text = "$" +My_bids
                    status_backround.setBackgroundResource(R.drawable.v_39)
                    v_status.text= "Won bids"
                    update_bids.text = "Bid accepted"
                    bidNow.setBackgroundResource(R.color.white)
                    update_bids.setTextColor(color)
                }else if (intent.getStringExtra("type").toString().equals("declined")){
//                    linearLayout.visibility = View.GONE
                    v_status.text= "Declined bids"
                    mybidprice.text = "My Bid  Amount"
                    v_price.text = "$" +My_bids
                    status_backround.setBackgroundResource(R.drawable.v_41)
                    update_bids.text = "Bid denied"
                    bidNow.setBackgroundResource(R.color.white)
                    update_bids.setTextColor(color)
                }

                if(V_status.equals("unavailable")&& intent.getStringExtra("type").toString() != "declined"){
                    post_name.text= "Name: "+Post_name

                    if (Post_email.equals("null")){
                        post_email.text= "Email: "+"-"
                    }else{
                        post_email.text= "Email: "+Post_email
                    }
                    if (Post_number.equals("null")){
                        post_number.text="Ph: "+"-"
                    }else{
                        post_number.text="Ph: "+Post_number
                    }
                    mybidprice.text = "My Bid  Amount"
//                    linearLayout.visibility = View.GONE
                    v_status.text= "Won bids"
                    status_backround.setBackgroundResource(R.drawable.v_39)
                    v_price.text = "$" +My_bids
                    update_bids.text = "Bid accepted"
                    update_bids.setTextColor(color)
                    bidNow.setBackgroundResource(R.color.white)
                }

                if(V_status.equals("sold")&& intent.getStringExtra("type").toString() != "declined"){
                    post_name.text= "Name: "+Post_name

                    if (Post_email.equals("null")){
                        post_email.text= "Email: "+"-"
                    }else{
                        post_email.text= "Email: "+Post_email
                    }
                    if (Post_number.equals("null")){
                        post_number.text="Ph: "+"-"
                    }else{
                        post_number.text="Ph: "+Post_number
                    }
//                    linearLayout.visibility = View.GONE
                    v_price.text = "$" +My_bids
                    status_backround.setBackgroundResource(R.drawable.v_39)
                    v_status.text= "Won bids"
                    update_bids.text = "Bid sold"
                    mybidprice.text = "My Bid  Amount"
                    update_bids.setTextColor(color)
                    bidNow.setBackgroundResource(R.color.white)
                }

                v_registration_no.text = V_registration_no





                front_image= vehicleDetail.getString("front_image")
                var rear_image= vehicleDetail.getString("rear_image")
                var left_side_image= vehicleDetail.getString("left_side_image")
                var interior_image= vehicleDetail.getString("interior_image")
                var cargo_area_image= vehicleDetail.getString("cargo_area_image")
                var engine_bay_image= vehicleDetail.getString("engine_bay_image")
                var roof_image= vehicleDetail.getString("roof_image")
                var wheels_image= vehicleDetail.getString("wheels_image")
                var keys_image= vehicleDetail.getString("keys_image")



                recyclerview.layoutManager = LinearLayoutManager(this)

                val data = ArrayList<CarImageModel2>()

                data.add(CarImageModel2(front_image.toString()))
                if(rear_image.equals("null")){

                }else
                {
                    data.add(CarImageModel2(rear_image.toString()))
                }

                if(left_side_image.equals("null")) {
                }
                else{
                    data.add(CarImageModel2(left_side_image.toString() ))
                }

                if(interior_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(interior_image.toString() ))
                }
                if(cargo_area_image.equals("null")) {
                }
                else {
                    data.add(CarImageModel2(cargo_area_image.toString()))
                }
                if(engine_bay_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(engine_bay_image.toString()))
                }
                if(roof_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(roof_image.toString() ))
                }
                if(wheels_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(wheels_image.toString() ))
                }
                if(keys_image.equals("null")) {
                }else{
                    data.add(CarImageModel2(keys_image.toString()))
                }




                val adapter = CarImageAdapter2(data)


                recyclerview.adapter = adapter


                adapter.setOnItemClickListener(object : CarImageAdapter2.onItemClickListener{
                    override fun onItemClick(position: Int) {


                        val intent = Intent(this@CarDetails, CarImage::class.java).apply {
                            putExtra("id",V_id)
                        }
                        startActivity(intent)
                    }

                })
                recyclerview.setLayoutManager(
                    LinearLayoutManager(
                        this,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                )
                arIndicatorView.attachTo(recyclerview, true)




            }, Response.ErrorListener { error ->
                spinKitView.visibility = View.GONE


                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                }

            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
//                headers.put("Authorization","Bearer ")

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)


    }

    private fun res2() {

        val URL = "http://motortraders.zydni.com/api/buyers/detail"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            { response ->

                val res = JSONObject(response)




                try {
                    var my_fav_ids: JSONArray? = null

                    my_fav_ids = res.getJSONArray("my_fav_ids")

                    for (i in 0 until my_fav_ids.length()){

                        var f_id = my_fav_ids!!.getString(i)

                        if (v_id.equals(f_id)){

                            favorite_status =  "true"
                            favotiteicon.setBackgroundResource(R.drawable.heart3)
                        }
                    }


                }catch (e: JSONException){

                }





            }, { error ->


            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@CarDetails))

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)
    }

    override fun onBackPressed() {


        if (intent.getStringExtra("bid_now").toString().equals("bid_now")) {
            val intent = Intent(this, HomeActivity::class.java).apply {


            }
            startActivity(intent)
        }else
            if((intent.getStringExtra("fav_bid_now").toString().equals("fav_bid_now"))){
            val intent = Intent(this, Favorite::class.java).apply {


            }
            startActivity(intent)
        } else{
            super.onBackPressed()

        }
    }


}







data class CarImageModel2 (val image: String ){
}









class  CarImageAdapter2 (private val mList: List<CarImageModel2>) : RecyclerView.Adapter<CarImageAdapter2.ViewHolder>() {


    private  lateinit var mlistner: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mlistner = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_image2, parent, false)

        return ViewHolder(view,mlistner)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val CarImageModel2 = mList[position]


        Glide.with(holder.itemView).load(CarImageModel2.image).fitCenter().into(holder.imageView)



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener: CarImageAdapter2.onItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)




        init {
            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)
            }
        }

    }


}