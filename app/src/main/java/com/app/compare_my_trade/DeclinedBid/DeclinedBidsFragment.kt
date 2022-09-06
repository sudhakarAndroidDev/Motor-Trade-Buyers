package com.example.kotlin_project1.DeclinedBid

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.R
import com.app.compare_my_trade.utils.PreferenceUtils
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.CurrentBid.CurrentBidsModel
import com.github.ybq.android.spinkit.SpinKitView
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class DeclinedBidsFragment : Fragment() {


    lateinit var recyclerview:RecyclerView

    var adapter: DeclinedBidsAdapter? = null

    lateinit var progresstext: TextView
    lateinit var spinKitView: SpinKitView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_declined_bids, container, false)

        recyclerview = view.findViewById(R.id.recyclerview3)


        recyclerview.layoutManager = LinearLayoutManager(activity)

        progresstext = view.findViewById(R.id.progressText)
        spinKitView = view.findViewById(R.id.progressBar)

        declinedbids()

        return view
    }

    private fun declinedbids() {

        spinKitView.visibility = View.VISIBLE

        val URL = "http://motortraders.zydni.com/api/buyers/biddings"

        val queue = Volley.newRequestQueue(activity)
        val model = ArrayList<DeclinedBidsModel>()

        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                spinKitView.visibility = View.GONE


                    progresstext.visibility = View.VISIBLE


                val res = JSONObject(response)

                var successfulBids = res.getJSONArray("declinedBids")
                recyclerview.layoutManager = LinearLayoutManager(activity)



                for (i in 0 until successfulBids.length()) {
                    val data: JSONObject = successfulBids.getJSONObject(i)


                    try {


                        progresstext.visibility = View.GONE

                        var bid_price =data.getString("bid_price")
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




                        model.add(DeclinedBidsModel(front_image, published_at,v_name,v_model,advertisement_id,bid_price,product_id,bid_id))


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



                adapter = DeclinedBidsAdapter(model)

                recyclerview.adapter = adapter

                recyclerview.setItemViewCacheSize(10000)
                Log.wtf("size_of_my_list", Integer.toString(model.size));
                adapter!!.notifyDataSetChanged()
                Log.d("debugMode", "The onCreateView method has been launched");

                adapter!!.setOnItemClickListener(object : DeclinedBidsAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {

                        val mod: DeclinedBidsModel = model.get(position)

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
                            putExtra("type","declined")
                            putExtra("my_bid",price)
                        }
                        startActivity(intent)
                    }

                })






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
    }

}