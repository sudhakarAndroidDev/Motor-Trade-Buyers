package com.app.compare_my_trade.ui.postauthenticationui.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.EventLogTags
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.Favorite.Favorite
import com.app.compare_my_trade.Filter.FilterAdapter
import com.app.compare_my_trade.Filter.FilterShowAdapter
import com.app.compare_my_trade.Filter.FilterShowModel
import com.app.compare_my_trade.Filter.MakeModel
import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.login.LoginFragment
import com.app.compare_my_trade.utils.PreferenceUtils
import com.example.kotlin_project1.CurrentBid.HomeAdapter
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nex3z.notificationbadge.NotificationBadge
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class HomeFragment : Fragment() {


    lateinit var recyclerview: RecyclerView
    var data1 = ArrayList<HomeViewModel>()

    lateinit var favorite: ImageButton
    lateinit var filter: ImageButton

    lateinit var make: TextView
    lateinit var model: TextView

    //    lateinit var categories:TextView
    lateinit var model_year: TextView
    lateinit var transmission: TextView
    lateinit var drive_type: TextView
    lateinit var fuel_type: TextView

    //    lateinit var body_type :TextView
    lateinit var listview: RecyclerView
    lateinit var recyclerView: RecyclerView
    lateinit var ApplyFilters: LinearLayout
    lateinit var ResetAll: TextView

    var data2: String? = null
    var filter_id: Int? = null
//    var data3 : String? = null

    //    lateinit var progressBar:ProgressBar
    lateinit var progresstext: TextView
    lateinit var spinKitView: SpinKitView

    var my_fav_ids: JSONArray? = null

    lateinit var Relative: RelativeLayout


//    lateinit var searchView:SearchView
//    lateinit var textView:TextView


    var adapter: HomeAdapter? = null
    val comman_name = ArrayList<String>()

    var name = ArrayList<MakeModel>()
    val name2 = ArrayList<FilterShowModel>()
    val make_name = ArrayList<String>()

    var year_name: String? = null

    val make_id = ArrayList<Int>()


    val model_id = ArrayList<Int>()


    val transmission_id = ArrayList<Int>()


    val drive_id = ArrayList<Int>()


    val fuel_id = ArrayList<Int>()

    var check: Boolean = false

    var badge: NotificationBadge? = null
    var mCount = 0

    lateinit var loadingProgressBar: SpinKitView

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var maindialog: BottomSheetDialog? = null

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?,

        ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        recyclerview = view.findViewById(R.id.rv_tradingItems)


        favorite = view.findViewById(R.id.favorite_icon)
        filter = view.findViewById(R.id.filter_btn2)

//        progressBar = view.findViewById(R.id.progressBar)
        progresstext = view.findViewById(R.id.progressText)
        spinKitView = view.findViewById(R.id.progressBar)

        Relative = view.findViewById(R.id.Relative)

        badge = view.findViewById(R.id.badge)

        swipeRefreshLayout = view.findViewById(R.id.swiper)


//        searchView = view.findViewById(R.id.searchable)
//
//        textView = view.findViewById(R.id.txtHeader)
//
//
//
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                adapter!!.getFilter()!!.filter(newText)
//                return false
//            }
//        })
//        searchView.setOnSearchClickListener { textView.setVisibility(View.GONE) }
//
//        searchView.setOnCloseListener {
//            res()
//            textView.setVisibility(View.VISIBLE)
//            false
//        }
//        searchView.queryHint = "Search"


        var sample_editText = view.findViewById<EditText>(R.id.sample_editText)





        sample_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })



        favorite.setOnClickListener {

            if (PreferenceUtils.getTokan(activity) == null) {

                val dialog = Dialog(it.context)

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
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
                        Intent(activity, LoginFragment::class.java).apply {
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

                val intent = Intent(activity, Favorite::class.java).apply {

                }
                startActivity(intent)

            }
        }

        filter.setOnClickListener {

            fit()

        }
        res2()

        val URL1 = "http://motortraders.zydni.com/api/buyers/car-list"
        var dialog = view?.let { BottomSheetDialog(it.context) }

//        dialog!!.setContentView(R.layout.activity_filter)


        res(URL1, dialog!!)




        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
            swipeRefreshLayout.setRefreshing(false)


//
//                var close:String = " "
//                adapter!!.close(close)


            data1.removeAll(data1)
            name.removeAll(name)
            name2.removeAll(name2)
            make_name.removeAll(make_name)


            make_id.removeAll(make_id)
            model_id.removeAll(model_id)
            transmission_id.removeAll(transmission_id)
            fuel_id.removeAll(fuel_id)
            drive_id.removeAll(drive_id)
            data1.removeAll(data1)
            year_name = ""

            mCount = 0

            badge!!.setNumber(mCount)
//        dialog!!.setContentView(R.layout.activity_filter)


            val URL1 = "http://motortraders.zydni.com/api/buyers/car-list"
            val dialog = view?.let { BottomSheetDialog(it.context) }

//        dialog!!.setContentView(R.layout.activity_filter)
            res(URL1, dialog!!)
        })



        return view

    }

    private fun fit() {
        // on below line we are creating a new bottom sheet dialog.

        var adapter2 = FilterShowAdapter(name2)

        maindialog = view?.let { BottomSheetDialog(it.context) }!!
        maindialog!!.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
        // on below line we are inflating a layout file which we have created.
        //val view = layoutInflater.inflate(R.layout.activity_filter, null)
        maindialog!!.setContentView(R.layout.activity_filter)

        val textView = maindialog!!.findViewById<TextView>(R.id.close)
        loadingProgressBar = maindialog!!.findViewById(R.id.progressBar)!!
        textView!!.setOnClickListener {

            maindialog!!.dismiss()
            if (check.equals(false) || make_name.toString().equals("[]")) {
//
//                var close:String = " "
//                adapter!!.close(close)


                data1.removeAll(data1)
                name.removeAll(name)
                name2.removeAll(name2)
                make_name.removeAll(make_name)


                make_id.removeAll(make_id)
                model_id.removeAll(model_id)
                transmission_id.removeAll(transmission_id)
                fuel_id.removeAll(fuel_id)
                drive_id.removeAll(drive_id)
                data1.removeAll(data1)
                year_name = ""
                val URL1 = "http://motortraders.zydni.com/api/buyers/car-list"

                mCount = 0

                badge!!.setNumber(mCount)
//        dialog!!.setContentView(R.layout.activity_filter)
                res(URL1, maindialog!!)

            }
        }

        maindialog!!.setCancelable(false)



        maindialog!!.behavior.peekHeight = 10000


//             set custom height and width
//                dialog.getWindow()!!.setLayout(600,800);
//            dialog.window!!.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
        maindialog!!.show()

        make = maindialog!!.findViewById(R.id.make)!!
//        body_type = dialog.findViewById(R.id.body_type)!!
        model = maindialog!!.findViewById(R.id.model)!!
        model_year = maindialog!!.findViewById(R.id.year)!!
//        categories = dialog.findViewById(R.id.categories)!!
        drive_type = maindialog!!.findViewById(R.id.drive_type)!!
        fuel_type = maindialog!!.findViewById(R.id.fuel_type)!!
        transmission = maindialog!!.findViewById(R.id.transmission)!!
        listview = maindialog!!.findViewById(R.id.filterValuesRV)!!
        recyclerView = maindialog!!.findViewById(R.id.filter_show)!!

        ApplyFilters = maindialog!!.findViewById(R.id.Apply_Filter)!!
        ResetAll = maindialog!!.findViewById(R.id.reset_all)!!

        loadingProgressBar!!.visibility = View.VISIBLE
        make.setBackgroundResource(R.color.white)
//        name.removeAll(name)
        val URL = "http://motortraders.zydni.com/api/buyers/make"
        var type: String
        type = "make"
        fitcat(URL, type, adapter2)

        make!!.setOnClickListener {
            make.setBackgroundResource(R.color.white)
//            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//            name.removeAll(name)
            val URL = "http://motortraders.zydni.com/api/buyers/make"
            var type: String
            type = "make"
            fitcat(URL, type, adapter2)
            loadingProgressBar!!.visibility = View.VISIBLE
        }
        model!!.setOnClickListener {
            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.white)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//            name.removeAll(name)
            val URL = "http://motortraders.zydni.com/api/buyers/car-models"
            var type: String
            type = "model"
            fitcat(URL, type, adapter2)
            loadingProgressBar!!.visibility = View.VISIBLE
        }
        model_year!!.setOnClickListener {
//            name.removeAll(name)
            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.white)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
            val URL = "http://motortraders.zydni.com/api/buyers/model-years"
            var type: String
            type = "year"
            fitcat(URL, type, adapter2)
            loadingProgressBar!!.visibility = View.VISIBLE
        }
        drive_type!!.setOnClickListener {
            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.white)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//            name.removeAll(name)
            val URL = "http://motortraders.zydni.com/api/buyers/drive-types"
            var type: String
            type = "drive"
            fitcat(URL, type, adapter2)
            loadingProgressBar!!.visibility = View.VISIBLE
        }
        fuel_type!!.setOnClickListener {
            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.white)
            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//            name.removeAll(name)
            val URL = "http://motortraders.zydni.com/api/buyers/fuel-types"
            var type: String
            type = "fuel"
            fitcat(URL, type, adapter2)
            loadingProgressBar!!.visibility = View.VISIBLE
        }
//        categories!!.setOnClickListener {
//            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
//            model_year.setBackgroundResource(R.color.lite_blue)
//            model.setBackgroundResource(R.color.lite_blue)
//            drive_type.setBackgroundResource(R.color.lite_blue)
//            fuel_type.setBackgroundResource(R.color.lite_blue)
//            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.white)
//            val URL = "http://motortraders.zydni.com/api/buyers/categories"
//            var type:String
//            type ="make"
//            fitcat(URL,type)
//        }
        transmission!!.setOnClickListener {
            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.white)
//            categories.setBackgroundResource(R.color.lite_blue)
//            name.removeAll(name)
            val URL = "http://motortraders.zydni.com/api/buyers/transmissions"
            var type: String
            type = "transmission"
            fitcat(URL, type, adapter2)
            loadingProgressBar!!.visibility = View.VISIBLE
        }
        ApplyFilters.setOnClickListener {

//            Toast.makeText(it.context,transmission_id.toString(),Toast.LENGTH_LONG).show()


            filter_by()


        }

        ResetAll.setOnClickListener {

            make.setBackgroundResource(R.color.white)
//            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)


            name.removeAll(name)
            name2.removeAll(name2)
            make_name.removeAll(make_name)

            make_id.removeAll(make_id)
            model_id.removeAll(model_id)
            transmission_id.removeAll(transmission_id)
            fuel_id.removeAll(fuel_id)
            drive_id.removeAll(drive_id)
            data1.removeAll(data1)
            year_name = ""

            val URL = "http://motortraders.zydni.com/api/buyers/make"
            var type: String
            type = "make"
            fitcat(URL, type, adapter2)


            val URL1 = "http://motortraders.zydni.com/api/buyers/car-list"
            val dialog = view?.let { BottomSheetDialog(it.context) }

//        dialog!!.setContentView(R.layout.activity_filter)
            res(URL1, dialog!!)





            recyclerView!!.adapter = adapter2


        }

//        body_type!!.setOnClickListener {
//            make.setBackgroundResource(R.color.lite_blue)
//            body_type.setBackgroundResource(R.color.white)
//            model_year.setBackgroundResource(R.color.lite_blue)
//            model.setBackgroundResource(R.color.lite_blue)
//            drive_type.setBackgroundResource(R.color.lite_blue)
//            fuel_type.setBackgroundResource(R.color.lite_blue)
//            transmission.setBackgroundResource(R.color.lite_blue)
//            categories.setBackgroundResource(R.color.lite_blue)
//            val URL = "http://motortraders.zydni.com/api/buyers/body-types"
//            var type:String
//            type ="body_type"
//            fitcat(URL,type)
//        }


    }

    private fun fitcat(URL: String, type: String, adapter2: FilterShowAdapter) {
        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                try {
                    loadingProgressBar!!.visibility = View.GONE

                    name.removeAll(name)
                    val res = JSONArray(response)
                    listview!!.layoutManager = LinearLayoutManager(activity)
                    recyclerView!!.layoutManager = LinearLayoutManager(activity)


//                var name2:JSONArray

                    var year: String? = null

                    for (i in 0 until res.length()) {


                        if (type.equals("make")) {
                            val data: JSONObject = res.getJSONObject(i)
                            data2 = data.getString("name")
                            filter_id = data.getInt("id")
                            year = "null"
//                        data3 = data.getString("id")
                        } else if (type.equals("transmission")) {
                            val data: JSONObject = res.getJSONObject(i)
                            data2 = data.getString("title")
                            filter_id = data.getInt("id")
                            year = "null"
//                        data3 = data.getString("id")
                        } else if (type.equals("year")) {
                            val years = res.getString(i)
                            data2 = years.toString()
                            year = "year"
                        } else if (type.equals("drive")) {
                            val data: JSONObject = res.getJSONObject(i)
                            data2 = data.getString("title")
                            filter_id = data.getInt("id")
                            year = "null"
//                        data3 = data.getString("id")
                        } else if (type.equals("model")) {
                            val data: JSONObject = res.getJSONObject(i)
                            data2 = data.getString("name")
                            filter_id = data.getInt("id")
                            year = "null"
//                        data3 = data.getString("id")
                        } else if (type.equals("fuel")) {
                            val data: JSONObject = res.getJSONObject(i)
                            data2 = data.getString("name")
                            filter_id = data.getInt("id")
                            year = "null"
//                        data3 = data.getString("id")
                        }




                        name.add(MakeModel(data2!!, filter_id!!, make_name, year!!))


                    }
                    var adapter = FilterAdapter(name)


                    // Toast.makeText(activity,data1.toString(),Toast.LENGTH_LONG).show()

                    listview!!.adapter = adapter







                    adapter.setOnItemClickListener(object : FilterAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            try {


                                val model: MakeModel = name.get(position)

                                var text = model.text1
                                var F_id = model.id
                                var last: Int? = 0
                                if (name2.size != 0) {
                                    last = name2.size - 1
                                }


                                if (model.year.equals("year")) {
                                    if (name2.size != 0) {
                                        name2.removeAt(last!!)
                                        make_name.removeAt(last)
                                    }
                                    name2.add(FilterShowModel(text))

                                    make_name.add(text)

                                } else {
                                    name2.add(FilterShowModel(text))

                                    make_name.add(text)
                                }





                                if (type.equals("make")) {
                                    make_id.add(F_id)
                                }
                                if (type.equals("transmission")) {
                                    transmission_id.add(F_id)
                                }
                                if (type.equals("year")) {


                                    year_name = "model_year[]=" + text
                                }
                                if (type.equals("drive")) {
                                    drive_id.add(F_id)
                                }
                                if (type.equals("model")) {
                                    model_id.add(F_id)
                                }
                                if (type.equals("fuel")) {
                                    fuel_id.add(F_id)
                                }





                                recyclerView!!.adapter = adapter2

                            } catch (e: Exception) {

                            }

                        }

                    })
                    adapter.setOnItemClickListener2(object : FilterAdapter.onItemClickListener2 {
                        override fun onItemClick2(position: Int) {

                            try {


                                val model: MakeModel = name.get(position)

                                var text2 = model.text1
                                var F_id = model.id


//
//                            Toast.makeText(activity,f_name.toString(),Toast.LENGTH_LONG).show()
//                            Log.i("difujnred",name2.toString())
//                            Toast.makeText(activity,text.toString(),Toast.LENGTH_LONG).show()
//                            Log.i("difujnred",text.toString())


//                                adapter2.notifyItemRemoved(position)

//                        name2.removeAt(position)
//                        Toast.makeText(activity,F_id.toString(),Toast.LENGTH_LONG).show()
//                        Log.i("difujnred", F_id.toString())


                                for (k in 0 until make_name.size) {
                                    try {

                                        Log.i("difujnred", name2.get(k).toString() + text2)


                                        var f_name = make_name.get(k)


                                        if (f_name != text2) {

                                        } else {
                                            name2.removeAt(k)
                                            make_name.removeAt(k)

                                        }
                                    } catch (e: Exception) {

                                    }

                                }

                                if (type.equals("make")) {
                                    for (k in 0 until make_id.size) {
                                        try {

                                            var f_id = make_id.get(k)

                                            if (f_id != F_id) {

                                            } else {
                                                make_id.removeAt(k)
                                            }
                                        } catch (e: Exception) {

                                        }

                                    }
                                }
                                if (type.equals("transmission")) {
                                    for (k in 0 until transmission_id.size) {
                                        try {

                                            var f_id = transmission_id.get(k)

                                            if (f_id != F_id) {

                                            } else {

                                                transmission_id.removeAt(k)

                                            }
                                        } catch (e: Exception) {

                                        }

                                    }
                                }
                                if (type.equals("year")) {


                                    year_name = ""


                                }
                                if (type.equals("drive")) {
                                    for (k in 0 until drive_id.size) {
                                        try {

                                            var f_id = drive_id.get(k)

                                            if (f_id != F_id) {

                                            } else {

                                                drive_id.removeAt(k)

                                            }
                                        } catch (e: Exception) {

                                        }

                                    }

                                }
                                if (type.equals("model")) {
                                    for (k in 0 until model_id.size) {
                                        try {

                                            var f_id = model_id.get(k)

                                            if (f_id != F_id) {

                                            } else {
                                                model_id.removeAt(k)

                                            }
                                        } catch (e: Exception) {

                                        }

                                    }
                                }
                                if (type.equals("fuel")) {
                                    for (k in 0 until fuel_id.size) {
                                        try {

                                            var f_id = fuel_id.get(k)

                                            if (f_id != F_id) {

                                            } else {

                                                fuel_id.removeAt(k)
                                            }
                                        } catch (e: Exception) {

                                        }

                                    }

                                }


                                recyclerView!!.adapter = adapter2
                            } catch (e: Exception) {

                            }

                        }
                    })



                    recyclerView!!.adapter = adapter2


                    recyclerView.setLayoutManager(
                        LinearLayoutManager(
                            activity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    )

                    listview.setOnTouchListener(View.OnTouchListener { v, event ->
                        v.parent.requestDisallowInterceptTouchEvent(true)
                        v.onTouchEvent(event)
                        true
                    })

                    listview.setItemViewCacheSize(10000)
                } catch (e: Exception) {

                }

            }, Response.ErrorListener { error ->
                loadingProgressBar.visibility = View.GONE

                if (error.toString()
                        .equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")
                ) {
                    Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_LONG)
                        .show()
                }


            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                // headers.put("Authorization","Bearer 112|9MrQvKhGRJOnZ01nVA7lU9JtxvmfsN5YcozCPNFU")

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

    private fun res(URL1: String, dialog: BottomSheetDialog) {
        dialog.dismiss()
//        Toast.makeText(activity,transmission_id.toString(),Toast.LENGTH_LONG).show()

        spinKitView.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = object : StringRequest(
            Method.GET, URL1,
            Response.Listener { response ->


                try {

                    progresstext.visibility = View.VISIBLE


                    try {


                        val res = JSONArray(response)

//                    Log.i("LOG_VOLLEY", response)


                        spinKitView.visibility = View.GONE


                        recyclerview.layoutManager = LinearLayoutManager(activity)



                        for (i in 0 until res.length()) {
                            val data: JSONObject = res.getJSONObject(i)


                            progresstext.visibility = View.GONE

                            var V_id = data.getString("id")
                            var v_name =
                                data.getString("brand_name") + " " + data.getString("model_name")
                            var vehicle_price = data.getString("vehicle_price")
                            var front_image = data.getString("front_image")
                            var published_at = data.getString("published_at")
                            var fuel_type = data.getString("fuel_type")
                            var transmission = data.getString("transmission")
                            var odometer = data.getString("odometer_mileage")
                            var drive_type = data.getString("drive_type")
                            var v_model = data.getString("model_year")

//                    Log.i("LOG_VOLLEY", published_at.toString())
//                    Toast.makeText(activity, published_at.toString(), Toast.LENGTH_LONG).show()
                            var favorite_status: String? = "false"


                            try {
                                for (j in 0 until my_fav_ids!!.length()) {
                                    var f_id: String? = null
                                    f_id = my_fav_ids!!.getString(j)

                                    if (V_id.equals(f_id)) {

                                        favorite_status = "true"

                                        break
                                    } else {
                                        favorite_status = "false"

                                    }


                                }

                            } catch (e: Exception) {

                            }








                            data1.add(HomeViewModel(front_image,
                                v_name,
                                "$ " + vehicle_price,
                                published_at,
                                V_id,
                                fuel_type,
                                transmission,
                                odometer,
                                drive_type,
                                favorite_status!!,
                                v_model
                            ))


                        }




                        adapter = HomeAdapter(data1)
                        recyclerview.setItemViewCacheSize(1000000000)

                        recyclerview.adapter = adapter

//                adapter!!.setOnItemClickListener(object : HomeAdapter.onItemClickListener{
//                    override fun onItemClick(v_id: String) {
//
//
//                        val intent = Intent(activity, CarDetails::class.java).apply {
//                            putExtra("v_id",v_id)
//                            putExtra("bid_now","bid_now")
//
//                        }
//                        startActivity(intent)
//                    }
//
//                })


//                    adapter!!.setOnItemClickListener2(object : HomeAdapter.onItemClickListener2{
//                        override fun onItemClick2(position: Int) {
//

//                            val queue = Volley.newRequestQueue(activity)
//
//
//                            val request: StringRequest = object : StringRequest(
//                                Method.GET, URL1,
//                                Response.Listener { response ->
//
//
//
//                                    try {
//
//
//                                        try {
//
//
//                                            val res = JSONArray(response)
//
//
//
//
//                                            for (i in 0 until res.length()) {
//                                                val data: JSONObject = res.getJSONObject(i)
//
//
//                                                var V_id = data.getString("id")
//                                                var v_name = data.getString("name")+" "+data.getString("brand_name")
//                                                var vehicle_price = data.getString("vehicle_price")
//                                                var front_image = data.getString("front_image")
//                                                var published_at = data.getString("published_at")
//                                                var fuel_type= data.getString("fuel_type")
//                                                var transmission= data.getString("transmission")
//                                                var odometer= data.getString("odometer_mileage")
//                                                var drive_type= data.getString("drive_type")
//                                                var v_model = data.getString("model_name")
//
////                    Log.i("LOG_VOLLEY", published_at.toString())
////                    Toast.makeText(activity, published_at.toString(), Toast.LENGTH_LONG).show()
//                                                var favorite_status: String? = "false"
//                                                try {
//
//
//                                                    for (j in 0 until res.length()){
//                                                        try {
//
//                                                            var f_id: String? = null
//                                                            f_id = my_fav_ids!!.getString(j)
//
//                                                            if (V_id.equals(f_id)){
//
//                                                                favorite_status = "true"
//
//
//                                                            }
//                                                        } catch (e: Exception) {
//                                                        }
//
//                                                    }
//
//
//
//                                                }catch (e: JSONException){
//
//                                                }
//
//
//                                                data1.add(HomeViewModel(front_image, v_name,"$ "+vehicle_price,published_at,V_id,fuel_type,transmission,odometer,drive_type,
//                                                    favorite_status!!,v_model
//                                                ))
//
//                                            }
//
////
////
////                                            adapter = HomeAdapter(data1)
////
////                                            recyclerview.adapter = adapter
////
////
////                                            recyclerview.setItemViewCacheSize(1000000000)
//
//                                        }catch (e: JSONException){
//
//                                        }
//
//                                    } catch (e: Exception) {
//
//                                    }
//
//                                }, Response.ErrorListener { error ->
//
//
//                                }) {
//
//
//
//                                @Throws(AuthFailureError::class)
//                                override fun getHeaders(): Map<String, String> {
//                                    val headers = HashMap<String, String>()
//                                    headers.put("Accept","application/json")
//
//
//                                    return headers
//                                }
//
//
//
//                            }
//                            request.retryPolicy = DefaultRetryPolicy(
//                                10000,
//                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                            )
//                            queue.add(request)

//                        }
//
//                    })


                    } catch (e: JSONException) {

                    }

                } catch (e: Exception) {

                }

            }, Response.ErrorListener { error ->
                spinKitView.visibility = View.GONE

                if (error.toString()
                        .equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")
                ) {
                    Toast.makeText(activity, "Check your internet connection", Toast.LENGTH_LONG)
                        .show()
                }


            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private fun res2() {


        val URL = "http://motortraders.zydni.com/api/buyers/detail"

        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            { response ->

                val res = JSONObject(response)




                try {

                    my_fav_ids = res.getJSONArray("my_fav_ids")


                } catch (e: Exception) {

                }


//                Toast.makeText(this,fisrt_name.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd",response.toString())


            }, { error ->


            }) {


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Authorization", "Bearer " + PreferenceUtils.getTokan(activity))

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

    //    private fun filter(text: String) {
//        // creating a new array list to filter our data.
//        val filteredlist: ArrayList<HomeViewModel> = ArrayList()
//
//        // running a for loop to compare elements.
//        for (item in courseModalArrayList!!) {
//            // checking if the entered string matched with any item of our recycler view.
//            if (item.text2.toLowerCase().contains(text.toLowerCase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredlist.add(item)
//            }
//        }
//        if (filteredlist.isEmpty()) {
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
//        } else {
//            // at last we are passing that filtered
//            // list to our adapter class.
//            adapter!!.filterList(filteredlist)
//        }
//    }
    fun filter(text: String) {

        val filteredCourseAry: ArrayList<HomeViewModel> = ArrayList()



        for (eachCourse in data1) {
            if (eachCourse.text1!!.toLowerCase()
                    .contains(text.toLowerCase()) || eachCourse.text2!!.toLowerCase()
                    .contains(text.toLowerCase())
                || eachCourse.text3!!.toLowerCase()
                    .contains(text.toLowerCase()) || eachCourse.drive_type!!.toLowerCase()
                    .contains(text.toLowerCase())
                || eachCourse.fuel_type!!.toLowerCase()
                    .contains(text.toLowerCase()) || eachCourse.odomewter!!.toLowerCase()
                    .contains(text.toLowerCase())
                || eachCourse.transmission!!.toLowerCase().contains(text.toLowerCase())
            ) {
                filteredCourseAry.add(eachCourse)
            }
        }

        //calling a method of the adapter class and passing the filtered list

        if (adapter != null) {
            adapter!!.filterList(filteredCourseAry)
        }

    }

//    override fun onStart() {
//        super.onStart()
//
//        Log.i("make", make_id.toString())
//        Log.i("model", model_id.toString())
//        Log.i("transmission", transmission_id.toString())
//        Log.i("fuel", fuel_id.toString())
//        Log.i("drive", drive_id.toString())
//        Log.i("year", year_name.toString())
//
////            var umake: String? = null
////            if (make_id != null){
////                umake =
////            }
//
//        //                    Log.i("usdegfyiusdghif",s_id.toString());
////                    Toast.makeText(order_conformation.this, s_id.toString(), Toast.LENGTH_SHORT).show();
//        val arr = JSONArray()
//
//        for (a in 0 until make_id.size) {
//            val b: Int = make_id.get(a)
//
//        }
//        arr.put(2)
//
//        var make_name = ""
//
//        val make_last: Int = make_id.size - 1
//
//        for (a in make_id.indices) {
//            var b: String? = null
//
//            b = "&make[]=" + make_id.get(a) + "&"
//
//
//            val c: String? = make_name
//
//            make_name = c + b
//
//        }
//
//        var model_name = ""
//
//        val model_last: Int = model_id.size - 1
//
//        for (a in model_id.indices) {
//            var b: String? = null
//
//
//            b = "&model[]=" + model_id.get(a) + "&"
//
//
//            val c: String? = model_name
//
//            model_name = c + b
//
//        }
//
//        var drive_name = ""
//
//        val drive_last: Int = drive_id.size - 1
//
//        for (a in drive_id.indices) {
//            var b: String? = null
//
//
//            b = "&drive_type[]=" + drive_id.get(a) + "&"
//
//
//            val c: String? = drive_name
//
//            drive_name = c + b
//
//        }
//
//        var transmission_name = ""
//
//        val transmission_last: Int = transmission_id.size - 1
//
//        for (a in transmission_id.indices) {
//            var b: String? = null
//
//
//            b = "transmission[]=" + transmission_id.get(a) + "&"
//
//
//            val c: String? = transmission_name
//
//            transmission_name = c + b
//
//        }
//
//        var fuel_name = ""
//
//        val fuel_type_last: Int = fuel_id.size - 1
//
//        for (a in fuel_id.indices) {
//            var b: String? = null
//
//
//            b = "fuel_type[]=" + fuel_id.get(a) + "&"
//
//
//            val c: String? = fuel_name
//
//            fuel_name = c + b
//
//        }
//
//
//        Log.i("judhbhbf", year_name.toString())
////            java.lang.String.valueOf("1")
//
////            var URL = "http://motortraders.zydni.com/api/buyers/car-list?make[]="+make_id+"&model[]="+model_id+"&fuel_type[]="+fuel_id+"&drive_type[]="+drive_id+"&transmission[]="+transmission_id+"&model_year="+year_name
//        var URL1 =
//            "http://motortraders.zydni.com/api/buyers/car-list?" + make_name + model_name + drive_name + transmission_name + fuel_name + year_name
//        check = true
////            var close:String = " "
////            adapter!!.close(close)
//
//        mCount = name2.size
//
//        badge!!.setNumber(mCount)
//
//        check = true
//        data1.removeAll(data1)
//        var dialog = view?.let { BottomSheetDialog(it.context) }
//        res(URL1, dialog!!)
//
//    }

    fun filter_by() {

        Log.i("make", make_id.toString())
        Log.i("model", model_id.toString())
        Log.i("transmission", transmission_id.toString())
        Log.i("fuel", fuel_id.toString())
        Log.i("drive", drive_id.toString())
        Log.i("year", year_name.toString())

//            var umake: String? = null
//            if (make_id != null){
//                umake =
//            }

        //                    Log.i("usdegfyiusdghif",s_id.toString());
//                    Toast.makeText(order_conformation.this, s_id.toString(), Toast.LENGTH_SHORT).show();
        val arr = JSONArray()

        for (a in 0 until make_id.size) {
            val b: Int = make_id.get(a)

        }
        arr.put(2)

        var make_name = ""

        val make_last: Int = make_id.size - 1

        for (a in make_id.indices) {
            var b: String? = null

            b = "&make[]=" + make_id.get(a) + "&"


            val c: String? = make_name

            make_name = c + b

        }

        var model_name = ""

        val model_last: Int = model_id.size - 1

        for (a in model_id.indices) {
            var b: String? = null


            b = "&model[]=" + model_id.get(a) + "&"


            val c: String? = model_name

            model_name = c + b

        }

        var drive_name = ""

        val drive_last: Int = drive_id.size - 1

        for (a in drive_id.indices) {
            var b: String? = null


            b = "&drive_type[]=" + drive_id.get(a) + "&"


            val c: String? = drive_name

            drive_name = c + b

        }

        var transmission_name = ""

        val transmission_last: Int = transmission_id.size - 1

        for (a in transmission_id.indices) {
            var b: String? = null


            b = "transmission[]=" + transmission_id.get(a) + "&"


            val c: String? = transmission_name

            transmission_name = c + b

        }

        var fuel_name = ""

        val fuel_type_last: Int = fuel_id.size - 1

        for (a in fuel_id.indices) {
            var b: String? = null


            b = "fuel_type[]=" + fuel_id.get(a) + "&"


            val c: String? = fuel_name

            fuel_name = c + b

        }


        Log.i("judhbhbf", year_name.toString())
//            java.lang.String.valueOf("1")

//            var URL = "http://motortraders.zydni.com/api/buyers/car-list?make[]="+make_id+"&model[]="+model_id+"&fuel_type[]="+fuel_id+"&drive_type[]="+drive_id+"&transmission[]="+transmission_id+"&model_year="+year_name
        var URL1 =
            "http://motortraders.zydni.com/api/buyers/car-list?" + make_name + model_name + drive_name + transmission_name + fuel_name + year_name
        check = true
//            var close:String = " "
//            adapter!!.close(close)

        mCount = name2.size

        badge!!.setNumber(mCount)

        check = true
        data1.removeAll(data1)
        var dialog = view?.let { BottomSheetDialog(it.context) }
        res(URL1, maindialog!!)
    }

}








