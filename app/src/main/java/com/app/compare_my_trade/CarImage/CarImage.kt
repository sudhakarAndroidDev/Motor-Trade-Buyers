package com.app.compare_my_trade.CarImage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails
import com.app.compare_my_trade.CarImageAdapter2
import com.app.compare_my_trade.CarImageModel2
import com.app.compare_my_trade.R
import com.bumptech.glide.Glide
import com.example.kotlin_project1.CurrentBid.CurrentBidsAdapter
import com.example.kotlin_project1.CurrentBid.CurrentBidsModel
import org.json.JSONObject
import java.util.HashMap

class CarImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_image)

        var back = findViewById(R.id.back) as TextView

        back.setOnClickListener {
            super.onBackPressed()
        }

        var id = intent.getStringExtra("id").toString()

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)






        val URL = "http://motortraders.zydni.com/api/buyers/car-show/"+id

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                val res = JSONObject(response)
                val hiddenSeller: JSONObject
                var vehicleDetail: JSONObject
                vehicleDetail = res.getJSONObject("vehicleDetail")
                hiddenSeller = res.getJSONObject("hiddenSeller")








                var front_image= vehicleDetail.getString("front_image")
                var rear_image= vehicleDetail.getString("rear_image")
                var left_side_image= vehicleDetail.getString("left_side_image")
                var interior_image= vehicleDetail.getString("interior_image")
                var cargo_area_image= vehicleDetail.getString("cargo_area_image")
                var engine_bay_image= vehicleDetail.getString("engine_bay_image")
                var roof_image= vehicleDetail.getString("roof_image")
                var wheels_image= vehicleDetail.getString("wheels_image")
                var keys_image= vehicleDetail.getString("keys_image")


                recyclerview.layoutManager = LinearLayoutManager(this)

                val data = ArrayList<CarImageModel>()

                data.add(CarImageModel(front_image, "Front"))
                if(rear_image.equals("null")){

                }else
                {
                    data.add(CarImageModel(rear_image, "Rear"))
                }

                if(left_side_image.equals("null")) {
                }
                else{
                    data.add(CarImageModel(left_side_image, "Left Side"))
                }

                if(interior_image.equals("null")) {
                }else{
                    data.add(CarImageModel(interior_image, "Interior"))
                }
                if(cargo_area_image.equals("null")) {
                }
                else {
                    data.add(CarImageModel(cargo_area_image, "Cargo Area"))
                }
                if(engine_bay_image.equals("null")) {
                }else{
                    data.add(CarImageModel(engine_bay_image, "Dash Engine Bay"))
                }
                if(roof_image.equals("null")) {
                }else{
                    data.add(CarImageModel(roof_image, "Roof"))
                }
                if(wheels_image.equals("null")) {
                }else{
                    data.add(CarImageModel(wheels_image, "Wheels"))
                }
                if(keys_image.equals("null")) {
                }else{
                    data.add(CarImageModel(keys_image, "Keys"))
                }


                val adapter = CarImageAdapter(data)


                recyclerview.adapter = adapter







            }, Response.ErrorListener { error ->


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
}









data class CarImageModel (val image: String , var text1:String){
}






class  CarImageAdapter (private val mList: List<CarImageModel>) : RecyclerView.Adapter<CarImageAdapter.ViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.car_image, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val CarImageModel = mList[position]


        Glide.with(holder.itemView).load(CarImageModel.image).fitCenter().into(holder.imageView)


        holder.textView.text = CarImageModel.text1


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val textView: TextView = itemView.findViewById(R.id.text)



        init {

        }

    }
}