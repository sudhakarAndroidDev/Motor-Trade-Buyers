package com.app.compare_my_trade.Favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.Filter.FilterAdapter
import com.app.compare_my_trade.Filter.MakeModel
import com.app.compare_my_trade.Filter.FilterShowAdapter
import com.app.compare_my_trade.Filter.FilterShowModel
import com.app.compare_my_trade.ProfileSettings
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.example.kotlin_project1.Favorite.FavoriteAdapter
import com.example.kotlin_project1.Favorite.FavoriteModel
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.ArrayList
import java.util.HashMap

class Favorite : AppCompatActivity() {

    lateinit var recyclerview:RecyclerView
  //  val data1 = ArrayList<HomeViewModel>()
  lateinit var Avatar: ImageView
    lateinit var filter: ImageButton

    val date = ArrayList<FavoriteModel>()

    lateinit var make:TextView
    lateinit var model:TextView
    lateinit var categories:TextView
    lateinit var model_year:TextView
    lateinit var transmission:TextView
    lateinit var drive_type:TextView
    lateinit var fuel_type:TextView
    lateinit var body_type :TextView
    lateinit var listview :RecyclerView
    lateinit var recyclerView :RecyclerView
    lateinit var data2 :String

    lateinit var progresstext:TextView
    lateinit var spinKitView: SpinKitView

    var adapter: FavoriteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)




        progresstext = findViewById(R.id.progressText)
        spinKitView = findViewById(R.id.progressBar)

        recyclerview = findViewById(R.id.recyclerview4)
        Avatar = findViewById(R.id.profile_photo)
        filter =  findViewById(R.id.filter)


        Avatar.setOnClickListener {


            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("nav","more")

            }
            startActivity(intent)

        }

        res()
        res2()

//        filter.setOnClickListener {
//
//            fit()
//
//        }


        var sample_editText = findViewById<EditText>(R.id.sample_editText)





        sample_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })






    }

//    private fun fit() {
//        // on below line we are creating a new bottom sheet dialog.
//        val dialog = BottomSheetDialog(this)
//
//        // on below line we are inflating a layout file which we have created.
//        //val view = layoutInflater.inflate(R.layout.activity_filter, null)
//        dialog!!.setContentView(R.layout.activity_filter)
//
//        val textView = dialog.findViewById<TextView>(R.id.close)
//        textView!!.setOnClickListener {
//
//            dialog.dismiss()
//        }
//
//        dialog.setCancelable(false)
//
//
//
//        dialog.behavior.peekHeight = 10000
//
//
////             set custom height and width
////                dialog.getWindow()!!.setLayout(600,800);
////            dialog.window!!.setLayout(
////                ViewGroup.LayoutParams.MATCH_PARENT,
////                ViewGroup.LayoutParams.MATCH_PARENT
////            )
//        dialog!!.show()
//
//        make = dialog.findViewById(R.id.make)!!
////        body_type = dialog.findViewById(R.id.body_type)!!
//        model = dialog.findViewById(R.id.model)!!
//        model_year = dialog.findViewById(R.id.year)!!
////        categories = dialog.findViewById(R.id.categories)!!
//        drive_type = dialog.findViewById(R.id.drive_type)!!
//        fuel_type = dialog.findViewById(R.id.fuel_type)!!
//        transmission = dialog.findViewById(R.id.transmission)!!
//        listview = dialog.findViewById(R.id.filterValuesRV)!!
//        recyclerView = dialog.findViewById(R.id.filter_show)!!
//
//
//        make!!.setOnClickListener {
//            make.setBackgroundResource(R.color.white)
//            body_type.setBackgroundResource(R.color.lite_blue)
//            model_year.setBackgroundResource(R.color.lite_blue)
//            model.setBackgroundResource(R.color.lite_blue)
//            drive_type.setBackgroundResource(R.color.lite_blue)
//            fuel_type.setBackgroundResource(R.color.lite_blue)
//            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//
//            val URL = "http://motortraders.zydni.com/api/buyers/make"
//            var type:String
//            type ="make"
//            fitcat(URL,type)
//        }
//        model!!.setOnClickListener {
//            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
//            model_year.setBackgroundResource(R.color.lite_blue)
//            model.setBackgroundResource(R.color.white)
//            drive_type.setBackgroundResource(R.color.lite_blue)
//            fuel_type.setBackgroundResource(R.color.lite_blue)
//            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//            val URL = "http://motortraders.zydni.com/api/buyers/car-models"
//            var type:String
//            type ="make"
//            fitcat(URL,type)
//        }
//        model_year!!.setOnClickListener {
//            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
//            model_year.setBackgroundResource(R.color.white)
//            model.setBackgroundResource(R.color.lite_blue)
//            drive_type.setBackgroundResource(R.color.lite_blue)
//            fuel_type.setBackgroundResource(R.color.lite_blue)
//            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//
//            val URL = "http://motortraders.zydni.com/api/buyers/model-years"
//            var type:String
//            type ="year"
//            fitcat(URL,type)
//        }
//        drive_type!!.setOnClickListener {
//            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
//            model_year.setBackgroundResource(R.color.lite_blue)
//            model.setBackgroundResource(R.color.lite_blue)
//            drive_type.setBackgroundResource(R.color.white)
//            fuel_type.setBackgroundResource(R.color.lite_blue)
//            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//            val URL = "http://motortraders.zydni.com/api/buyers/drive-types"
//            var type:String
//            type ="body_type"
//            fitcat(URL,type)
//        }
//        fuel_type!!.setOnClickListener {
//            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
//            model_year.setBackgroundResource(R.color.lite_blue)
//            model.setBackgroundResource(R.color.lite_blue)
//            drive_type.setBackgroundResource(R.color.lite_blue)
//            fuel_type.setBackgroundResource(R.color.white)
//            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//
//            val URL = "http://motortraders.zydni.com/api/buyers/fuel-types"
//            var type:String
//            type ="make"
//            fitcat(URL,type)
//        }
////        categories!!.setOnClickListener {
////            make.setBackgroundResource(R.color.lite_blue)
////            body_type.setBackgroundResource(R.color.lite_blue)
////            model_year.setBackgroundResource(R.color.lite_blue)
////            model.setBackgroundResource(R.color.lite_blue)
////            drive_type.setBackgroundResource(R.color.lite_blue)
////            fuel_type.setBackgroundResource(R.color.lite_blue)
////            transmission.setBackgroundResource(R.color.lite_blue)
////            categories.setBackgroundResource(R.color.white)
////            val URL = "http://motortraders.zydni.com/api/buyers/categories"
////            var type:String
////            type ="make"
////            fitcat(URL,type)
////        }
//        transmission!!.setOnClickListener {
//            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
//            model_year.setBackgroundResource(R.color.lite_blue)
//            model.setBackgroundResource(R.color.lite_blue)
//            drive_type.setBackgroundResource(R.color.lite_blue)
//            fuel_type.setBackgroundResource(R.color.lite_blue)
//            transmission.setBackgroundResource(R.color.white)
//            categories.setBackgroundResource(R.color.lite_blue)
//
//            val URL = "http://motortraders.zydni.com/api/buyers/transmissions"
//            var type:String
//            type ="body_type"
//            fitcat(URL,type)
//        }
////        body_type!!.setOnClickListener {
////            make.setBackgroundResource(R.color.lite_blue)
////            body_type.setBackgroundResource(R.color.white)
////            model_year.setBackgroundResource(R.color.lite_blue)
////            model.setBackgroundResource(R.color.lite_blue)
////            drive_type.setBackgroundResource(R.color.lite_blue)
////            fuel_type.setBackgroundResource(R.color.lite_blue)
////            transmission.setBackgroundResource(R.color.lite_blue)
////            categories.setBackgroundResource(R.color.lite_blue)
////            val URL = "http://motortraders.zydni.com/api/buyers/body-types"
////            var type:String
////            type ="body_type"
////            fitcat(URL,type)
////        }
//
//
//
//    }
//
//    private fun fitcat(URL : String,type :String) {
//        val queue = Volley.newRequestQueue(this)
//
//
//        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
//        object : StringRequest(
//            Method.GET, URL,
//            Response.Listener { response ->
//
//                val res = JSONArray(response)
//                listview!!.layoutManager = LinearLayoutManager(this)
//                recyclerView!!.layoutManager = LinearLayoutManager(this)
//
//                val  name = ArrayList<MakeModel>()
//                val  name2 = ArrayList<FilterShowModel>()
//
//
//                for (i in 0 until res.length()) {
//
//
//                    if (type.equals("make")) {
//                        val data: JSONObject = res.getJSONObject(i)
//                        data2 = data.getString("name")
//                    }else if (type.equals("body_type")) {
//                        val data: JSONObject = res.getJSONObject(i)
//                        data2 = data.getString("title")
//                    }else if (type.equals("year")){
//                        val years = res.getString(i)
//                        data2 = years.toString()
//                    }
//
//
//
//
////                    name.add(FilterModel(data2))
//
//
//
//                }
//                var adapter = FilterAdapter(name)
//                var adapter2 = FilterShowAdapter(name2)
//                // Toast.makeText(activity,data1.toString(),Toast.LENGTH_LONG).show()
//
//                listview!!.adapter = adapter
//
//
//
//                adapter.setOnItemClickListener(object : FilterAdapter.onItemClickListener{
//                    override fun onItemClick(position: Int) {
//
//                        val model: MakeModel = name.get(position)
//
//                        var text = model.text1
//
//
////                                val model2:FilterShowModel = name2.get(position)
////                                var text2 = model2.text1
//
//
////                        name2.add(FilterShowModel(text))
//
//                        for (i in 0 until name2.size){
//
//                            if (name2.get(i).equals(text)){
//                                adapter.notifyItemRemoved(i)
//                            }
//                        }
//
//
//                        recyclerView!!.adapter = adapter2
//
//
//                    }
//
//                })
//                adapter.setOnItemClickListener2(object : FilterAdapter.onItemClickListener2{
//                    override fun onItemClick2(position: Int) {
//
//                        val model: MakeModel = name.get(position)
//
//                        var text = model.text1
////
////
//////                                val model2:FilterShowModel = name2.get(position)
//////                                var text2 = model2.text1
////
////
////                        name2.add(FilterShowModel(text))
//
//
//
////                        recyclerView!!.adapter = adapter2
//                    }
//                })
//
//
//
//
//
//                recyclerView.setLayoutManager(
//                    LinearLayoutManager(
//                        this,
//                        LinearLayoutManager.HORIZONTAL,
//                        false
//                    )
//                )
//
//                listview.setOnTouchListener(View.OnTouchListener { v, event ->
//                    v.parent.requestDisallowInterceptTouchEvent(true)
//                    v.onTouchEvent(event)
//                    true
//                })
//
//                listview.setItemViewCacheSize(10000)
//
//
//            }, Response.ErrorListener { error ->
//
//
//            }) {
//
//
//
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers.put("Accept","application/json")
//                // headers.put("Authorization","Bearer 112|9MrQvKhGRJOnZ01nVA7lU9JtxvmfsN5YcozCPNFU")
//
//                return headers
//            }
//
//
//
//        }
//        request.retryPolicy = DefaultRetryPolicy(
//            10000,
//            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        )
//        queue.add(request)
//
//    }

    private fun res() {

        spinKitView.visibility = View.VISIBLE

        val URL = "http://motortraders.zydni.com/api/buyers/favourites"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->
                spinKitView.visibility = View.GONE
                try {

                        progresstext.visibility = View.VISIBLE


                val res = JSONObject(response)
                val data = res.getJSONArray("data")


                recyclerview.layoutManager = LinearLayoutManager(this)



                for (i in 0 until data.length()) {
                    val data2: JSONObject = data.getJSONObject(i)

                    progresstext.visibility = View.GONE

                    var V_id = data2.getString("id")
                    var v_name = data2.getString("name")+" "+data2.getString("brand_name")
                    var vehicle_price = data2.getString("vehicle_price")
                    var front_image = data2.getString("front_image")
                    var published_at = data2.getString("published_at")
                    var status = data2.getString("vehicle_status")
                    var v_model = data2.getString("model_name")+" "+ data2.getString("model_year")
//                    Log.i("LOG_VOLLEY", response.toString())
//                    Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()




                    date.add(FavoriteModel(front_image, v_name,"$ "+vehicle_price,published_at,V_id,status,v_model))



                }





                adapter = FavoriteAdapter(date)



                adapter!!.setOnItemClickListener2(object : FavoriteAdapter.onItemClickListener2{
                    override fun onItemClick2(position: Int) {


                        val mod: FavoriteModel = date.get(position)

                        var pid = mod.id

                        val URL = "http://motortraders.zydni.com/api/buyers/favourites/"+pid

                        val queue = Volley.newRequestQueue(this@Favorite)


                        val request: StringRequest = object : StringRequest(
                            Method.POST, URL,
                            Response.Listener { response ->

//                    holder.imageView2.setBackgroundResource(R.drawable.heart2)
//                    Toast.makeText(it.context,response.toString(),Toast.LENGTH_LONG).show()



                                if (response.toString().equals("true")){

                                    date.removeAt(position)
                                    adapter!!.notifyItemRemoved(position)



                                    Toast.makeText(this@Favorite,"Removed",Toast.LENGTH_LONG).show()


//                                    val intent = Intent(this@Favorite, HomeActivity::class.java).apply {
//
//
//                                    }
//                                    startActivity(intent)




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
                                headers.put("Accept","application/json")
                                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@Favorite))

                                return headers
                            }

                            override fun getParams()  : Map<String, String>?  {

                                val params: MutableMap<String, String> = HashMap()



                                params["type"] = "remove"



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

                })




//                adapter!!.setOnItemClickListener(object : FavoriteAdapter.onItemClickListener{
//                    override fun onItemClick(position: Int) {
//
//
//                    }
//
//                })




                recyclerview.adapter = adapter

                recyclerview.setItemViewCacheSize(10000)







                } catch (e: Exception) {
                }


            }, Response.ErrorListener { error ->
                spinKitView.visibility = View.GONE


                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                }
//                Log.e("LOG_VOLLEY", error.toString())
//                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@Favorite))

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


                var avatar = res.getString("avatar")




                if (avatar.equals(null))
                {

                }else{
                    if(this !=null) {


                        Glide.with(getApplicationContext()).load(avatar).fitCenter().into(Avatar)
                    }
                }



//                Toast.makeText(this,fisrt_name.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd",response.toString())



            }, { error ->


            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@Favorite))

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
    fun filter(text: String) {

        val filteredCourseAry: ArrayList<FavoriteModel> = ArrayList()



        for (eachCourse in date) {
            if (eachCourse.text1!!.toLowerCase().contains(text.toLowerCase())) {
                filteredCourseAry.add(eachCourse)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        if (adapter != null){
            adapter!!.filterList(filteredCourseAry);
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()


            val intent = Intent(this, HomeActivity::class.java).apply {


            }
            startActivity(intent)

    }
}