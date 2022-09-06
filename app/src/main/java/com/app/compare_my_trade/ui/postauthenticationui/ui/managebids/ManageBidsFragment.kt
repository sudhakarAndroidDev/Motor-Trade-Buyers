package com.app.compare_my_trade.ui.postauthenticationui.ui.managebids

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.Favorite.Favorite
import com.app.compare_my_trade.Filter.FilterAdapter
import com.app.compare_my_trade.Filter.MakeModel
import com.app.compare_my_trade.R
import com.example.kotlin_project1.CurrentBid.CurrentBidsFragment
import com.example.kotlin_project1.DeclinedBid.DeclinedBidsFragment
import com.example.kotlin_project1.WonBid.WonBidsFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentTransaction

import com.app.compare_my_trade.Filter.FilterShowAdapter
import com.app.compare_my_trade.Filter.FilterShowModel
import com.app.compare_my_trade.ui.login.LoginFragment
import com.app.compare_my_trade.utils.PreferenceUtils


class ManageBidsFragment : Fragment() {

//    lateinit var tabLayout: TabLayout
//    lateinit var viewPager: ViewPager
    lateinit var favorite: ImageButton
    lateinit var filter: ImageButton


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

    var tabLayout: TabLayout? = null
    var frameLayout: FrameLayout? = null
    var fragment: Fragment? = null
    var fragmentTransaction: FragmentTransaction? = null


//    var arrayList: ArrayList<String>? = null


//    private lateinit var manageBidsViewModel: ManageBidsViewModel
//    private var _binding: FragmentManageBidsBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!

    @Nullable
    @Override
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?,
                              @Nullable savedInstanceState: Bundle?): View? {


//        manageBidsViewModel =
//            ViewModelProvider(this).get(ManageBidsViewModel::class.java)
//
//        _binding = FragmentManageBidsBinding.inflate(inflater, container, false)
//        val root: View = binding.root

        val root= inflater.inflate(R.layout.fragment_manage_bids, container, false)

//        tabLayout = root.findViewById(R.id.tabLayout)
//        viewPager = root.findViewById(R.id.viewPager)
        favorite = root.findViewById(R.id.favorite_icon)
        filter =  root.findViewById(R.id.filter_btn2)




        tabLayout = root.findViewById(R.id.tabLayout) as TabLayout
        frameLayout = root.findViewById(R.id.frameLayout) as FrameLayout

        fragment = CurrentBidsFragment()

        fragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction!!.replace(R.id.frameLayout,
            fragment as CurrentBidsFragment
        )
        fragmentTransaction!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction!!.commit()



        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> fragment = CurrentBidsFragment()
                    1 -> fragment = WonBidsFragment()
                    2 -> fragment = DeclinedBidsFragment()

                }



                val ft = requireFragmentManager().beginTransaction()
                ft.replace(R.id.frameLayout, fragment!!)
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commit()



            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


//
//        setupViewPager(viewPager);
//
//
//        tabLayout.setupWithViewPager(viewPager);




        favorite.setOnClickListener {

                  if (PreferenceUtils.getTokan(activity) == null) {

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






//        arrayList = ArrayList()
//
//        val adapter4: ArrayAdapter<String> =
//            ArrayAdapter<String>(requireContext(), R.layout.filter_color, arrayList!!)



        filter.setOnClickListener {

//            fit()

        }









//        tabLayout.addTab(tabLayout.newTab().setText("Current Bids"))
//        tabLayout.addTab(tabLayout.newTab().setText("Won Bids"))
//        tabLayout.addTab(tabLayout.newTab().setText("Declined Bids"))
//        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
//        val adapter = Adapter(this.requireContext(), getParentFragmentManager()  ,
//            tabLayout.tabCount)
//        viewPager.adapter = adapter
//
//        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                viewPager.currentItem = tab.position
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//
//        })








        return root
    }
//    private fun setupViewPager(viewPager: ViewPager) {
//        val adapter = ViewPagerAdapter(parentFragmentManager)
//        adapter.addFrag(CurrentBidsFragment(), "ONE")
//        adapter.addFrag(WonBidsFragment(), "TWO")
//        adapter.addFrag(DeclinedBidsFragment(), "THREE")
//        viewPager.adapter = adapter
//    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    private fun fit() {
        // on below line we are creating a new bottom sheet dialog.
        val dialog = view?.let { BottomSheetDialog(it.context) }

        // on below line we are inflating a layout file which we have created.
        //val view = layoutInflater.inflate(R.layout.activity_filter, null)
        dialog!!.setContentView(R.layout.activity_filter)

        val textView = dialog.findViewById<TextView>(R.id.close)
        textView!!.setOnClickListener {

            dialog.dismiss()
        }

        dialog.setCancelable(false)



        dialog.behavior.peekHeight = 10000


//             set custom height and width
//                dialog.getWindow()!!.setLayout(600,800);
//            dialog.window!!.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
        dialog!!.show()

        make = dialog.findViewById(R.id.make)!!
//        body_type = dialog.findViewById(R.id.body_type)!!
        model = dialog.findViewById(R.id.model)!!
        model_year = dialog.findViewById(R.id.year)!!
//        categories = dialog.findViewById(R.id.categories)!!
        drive_type = dialog.findViewById(R.id.drive_type)!!
        fuel_type = dialog.findViewById(R.id.fuel_type)!!
        transmission = dialog.findViewById(R.id.transmission)!!
        listview = dialog.findViewById(R.id.filterValuesRV)!!
        recyclerView = dialog.findViewById(R.id.filter_show)!!


        make!!.setOnClickListener {
            make.setBackgroundResource(R.color.white)
            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)
            categories.setBackgroundResource(R.color.lite_blue)

            val URL = "http://motortraders.zydni.com/api/buyers/make"
            var type:String
            type ="make"
            fitcat(URL,type)
        }
        model!!.setOnClickListener {
            make.setBackgroundResource(R.color.lite_blue)
            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.white)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)
            categories.setBackgroundResource(R.color.lite_blue)
            val URL = "http://motortraders.zydni.com/api/buyers/car-models"
            var type:String
            type ="make"
            fitcat(URL,type)
        }
        model_year!!.setOnClickListener {
            make.setBackgroundResource(R.color.lite_blue)
            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.white)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)
            categories.setBackgroundResource(R.color.lite_blue)

            val URL = "http://motortraders.zydni.com/api/buyers/model-years"
            var type:String
            type ="year"
            fitcat(URL,type)
        }
        drive_type!!.setOnClickListener {
            make.setBackgroundResource(R.color.lite_blue)
            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.white)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.lite_blue)
            categories.setBackgroundResource(R.color.lite_blue)
            val URL = "http://motortraders.zydni.com/api/buyers/drive-types"
            var type:String
            type ="body_type"
            fitcat(URL,type)
        }
        fuel_type!!.setOnClickListener {
            make.setBackgroundResource(R.color.lite_blue)
            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.white)
            transmission.setBackgroundResource(R.color.lite_blue)
            categories.setBackgroundResource(R.color.lite_blue)

            val URL = "http://motortraders.zydni.com/api/buyers/fuel-types"
            var type:String
            type ="make"
            fitcat(URL,type)
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
            body_type.setBackgroundResource(R.color.lite_blue)
            model_year.setBackgroundResource(R.color.lite_blue)
            model.setBackgroundResource(R.color.lite_blue)
            drive_type.setBackgroundResource(R.color.lite_blue)
            fuel_type.setBackgroundResource(R.color.lite_blue)
            transmission.setBackgroundResource(R.color.white)
            categories.setBackgroundResource(R.color.lite_blue)

            val URL = "http://motortraders.zydni.com/api/buyers/transmissions"
            var type:String
            type ="body_type"
            fitcat(URL,type)
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

    private fun fitcat(URL : String,type :String) {
        val queue = Volley.newRequestQueue(activity)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                val res = JSONArray(response)
                listview!!.layoutManager = LinearLayoutManager(activity)
                recyclerView!!.layoutManager = LinearLayoutManager(activity)

                val  name = ArrayList<MakeModel>()
                val  name2 = ArrayList<FilterShowModel>()


                for (i in 0 until res.length()) {


                    if (type.equals("make")) {
                        val data: JSONObject = res.getJSONObject(i)
                        data2 = data.getString("name")
                    }else if (type.equals("body_type")) {
                        val data: JSONObject = res.getJSONObject(i)
                        data2 = data.getString("title")
                    }else if (type.equals("year")){
                        val years = res.getString(i)
                        data2 = years.toString()
                    }




//                    name.add(FilterModel(data2))



                }
                var adapter = FilterAdapter(name)
                var adapter2 = FilterShowAdapter(name2)
                // Toast.makeText(activity,data1.toString(),Toast.LENGTH_LONG).show()

                listview!!.adapter = adapter



                adapter.setOnItemClickListener(object :FilterAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val model: MakeModel = name.get(position)

                        var text = model.text1


//                                val model2:FilterShowModel = name2.get(position)
//                                var text2 = model2.text1


//                        name2.add(FilterShowModel(text))

                        for (i in 0 until name2.size){

                            if (name2.get(i).equals(text)){
                                adapter.notifyItemRemoved(i)
                            }
                        }


                        recyclerView!!.adapter = adapter2


                    }

                })
                adapter.setOnItemClickListener2(object :FilterAdapter.onItemClickListener2{
                    override fun onItemClick2(position: Int) {

                        val model: MakeModel = name.get(position)

                        var text = model.text1
//
//
////                                val model2:FilterShowModel = name2.get(position)
////                                var text2 = model2.text1
//
//
//                        name2.add(FilterShowModel(text))



//                        recyclerView!!.adapter = adapter2
                    }
                })





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


            }, Response.ErrorListener { error ->


            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
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

//    internal class ViewPagerAdapter(manager: FragmentManager?) :
//        FragmentPagerAdapter(manager!!) {
//        private val mFragmentList: MutableList<Fragment> = ArrayList()
//        private val mFragmentTitleList: MutableList<String> = ArrayList()
//        override fun getItem(position: Int): Fragment {
//            return mFragmentList[position]
//        }
//
//        override fun getCount(): Int {
//            return mFragmentList.size
//        }
//
//        fun addFrag(fragment: Fragment, title: String) {
//            mFragmentList.add(fragment)
//            mFragmentTitleList.add(title)
//        }
//
//        override fun getPageTitle(position: Int): CharSequence? {
//            return mFragmentTitleList[position]
//        }
//    }
}


//@Suppress("DEPRECATION")
//internal class Adapter(
//    var context: Context,
//    fm: FragmentManager,
//    var totalTabs: Int
//) :
//    FragmentPagerAdapter(fm) {
//    override fun getItem(position: Int): Fragment {
//        return when (position) {
//            0 -> {
//                CurrentBidsFragment()
//
//            }
//            1 -> {
//                WonBidsFragment()
//            }
//            2->{
//                DeclinedBidsFragment()
//            }
//
//            else -> getItem(position)
//        }
//    }
//    override fun getCount(): Int {
//        return totalTabs
//    }
//
//}