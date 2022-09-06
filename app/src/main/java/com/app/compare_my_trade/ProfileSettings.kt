package com.app.compare_my_trade

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import com.app.compare_my_trade.utils.PreferenceUtils
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_profile_settings.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset
import java.util.*


class ProfileSettings : AppCompatActivity() {


    lateinit var First_name  : TextView
    lateinit var Full_name  : EditText
    lateinit var Email  : TextView
    lateinit var Email_adress  : EditText
    lateinit var Abn  : EditText
    lateinit var post_code  : EditText
    lateinit var Business_phone  : EditText
    //    lateinit var License_no  : EditText
    lateinit var state: Spinner
    //    lateinit var Verification  : TextView
    lateinit var Avatar  : ImageView
    lateinit var updatebtn: Button
    lateinit var cancel: Button
    var l_id:String? = null
    lateinit var image_upload: FloatingActionButton

    private val ROOT_URL = "http://motortraders.zydni.com/api/buyers/update-avatar"
    private lateinit var bitmap: Bitmap

    private val CAMERA_REQUEST1 = 2
    private val MY_CAMERA_PERMISSION_CODE = 100
    private val SELECT_PHOTO = 1

    private val PERMISSION_REQUEST_CODE = 200
    private val PICK_FROM_GALLERY = 201

    lateinit var Error1:TextView
    lateinit var Error2:TextView
    lateinit var Error3:TextView
    lateinit var Error4:TextView
    lateinit var Error5:TextView
    lateinit var Error6:TextView

    lateinit var  currentphoto:String


    lateinit var spinKitView: SpinKitView
//    lateinit var visibale:RelativeLayout

    var status:String? = null

    var State_id: String? = null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)


        get()

        First_name = findViewById(R.id.name)
        Full_name = findViewById(R.id.full_name)
        Email = findViewById(R.id.email)
        Email_adress = findViewById(R.id.email_address)
        Abn = findViewById(R.id.abn)
        post_code = findViewById(R.id.post_code)

        Business_phone = findViewById(R.id.business_phone)
//        License_no = findViewById(R.id.license_no)
        Avatar = findViewById(R.id.avatar)
//        Verification = findViewById(R.id.email_verified)
        state = findViewById(R.id.state)
        updatebtn = findViewById(R.id.update)
        cancel = findViewById(R.id.cancel)
        image_upload = findViewById(R.id.upload_img)


        Error1 = findViewById(R.id.error1)
        Error2 = findViewById(R.id.error2)
        Error3 = findViewById(R.id.error3)
        Error4 = findViewById(R.id.error4)
        Error5 = findViewById(R.id.error5)
        Error6 = findViewById(R.id.error6)


        spinKitView = findViewById<SpinKitView>(R.id.progressBar)
//        visibale = findViewById(R.id.visibale)


        var back = findViewById(R.id.back) as ImageView

        back.setOnClickListener {
            super.onBackPressed()
        }
        cancel.setOnClickListener {

//            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//            } else {
//                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                startActivityForResult(cameraIntent, CAMERA_REQUEST)
//            }
            super.onBackPressed()
        }

        image_upload.setOnClickListener {

            val dialog =  BottomSheetDialog(this)

            dialog!!.setContentView(R.layout.choose_image)

            val close = dialog.findViewById<TextView>(R.id.close)
            val file = dialog.findViewById<TextView>(R.id.file)
            val camera = dialog.findViewById<TextView>(R.id.camera)

            file!!.setOnClickListener {

                dialog.dismiss()
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE),PICK_FROM_GALLERY)
                } else {

                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(intent, SELECT_PHOTO)
                }
            }
            camera!!.setOnClickListener {

                dialog.dismiss()

//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST1)
//                }

                if (checkPermission()) {

                    val filename = "Photo"
                    val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    try {
                        val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                        currentphoto = imagefile.absolutePath
                        val imageuri = FileProvider.getUriForFile(this,
                            "com.app.compare_my_trade.fileprovider2",
                            imagefile)
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                        startActivityForResult(intent, CAMERA_REQUEST1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }else {
                    requestPermission()
                }


//                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
//                        } else {
//                val filename = "Photo"
//                val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                try {
//                    val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
//                    currentphoto = imagefile.absolutePath
//                    val imageuri = FileProvider.getUriForFile(this,
//                        "com.app.compare_my_trade.fileprovider",
//                        imagefile)
//                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
//                    startActivityForResult(intent, CAMERA_REQUEST1)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }


            }


            close!!.setOnClickListener {

                dialog.dismiss()
            }


            dialog.setCancelable(false)
            dialog.behavior.peekHeight = 10000
            dialog!!.show()
        }





//        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//
//
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//
//                var State_name:String? = null
//                State_name = state.selectedItem.toString()
//
//                val URL2 = "http://motortraders.zydni.com/api/common/locations"
//
//                val queue2 = Volley.newRequestQueue(view.context)
//
//
//                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
//                object : StringRequest(
//                    Method.GET, URL2,
//                    Response.Listener { response ->
//
//
//                        val res = JSONArray(response)
//
////                var courses = arrayOf(
////                    ""
////                )
////                name.add("State")
//                        for (i in 0 until res.length()) {
//                            var jsonObject: JSONObject = res.getJSONObject(i)
//                            if (jsonObject.getString("name").equals(State_name)) {
//                                var id = jsonObject.getString("id")
//
//                                State_id = id
////                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
//                            }
//
//                        }
//
//
//                    }, Response.ErrorListener { error ->
//
//
//                    }) {
//
//
//                    @Throws(AuthFailureError::class)
//                    override fun getHeaders(): Map<String, String> {
//                        val headers = HashMap<String, String>()
//                        headers.put("Accept", "application/json")
//                        // headers.put("Authorization","Bearer 112|9MrQvKhGRJOnZ01nVA7lU9JtxvmfsN5YcozCPNFU")
//
//                        return headers
//                    }
//
//                }
//                request2.retryPolicy = DefaultRetryPolicy(
//                    10000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//                )
//                queue2.add(request2)
//
//
//
//            } // to close the onItemSelected
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//
//            }
//        }

        updatebtn.setOnClickListener {

            if (status.equals("true")) {
                spinKitView.visibility = View.VISIBLE
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                var s_id: String? = null
                var State_name: String? = null
                State_name = state.selectedItem.toString()

                val URL2 = "http://motortraders.zydni.com/api/common/locations"

                val queue2 = Volley.newRequestQueue(this)


                val request2: StringRequest = @SuppressLint("ClickableViewAccessibility")
                object : StringRequest(
                    Method.GET, URL2,
                    Response.Listener { response ->


                        val res = JSONArray(response)

//                var courses = arrayOf(
//                    ""
//                )
//                name.add("State")
                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)
                            if (jsonObject.getString("name").equals(State_name)) {
                                var id = jsonObject.getString("id")

                                s_id = id
                                update(s_id.toString())
//                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
                            }



                        }
                        if (s_id.equals(null)){
                            update(s_id.toString())
                        }


                    }, Response.ErrorListener { error ->


                    }) {


                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers.put("Accept", "application/json")
                        // headers.put("Authorization","Bearer 112|9MrQvKhGRJOnZ01nVA7lU9JtxvmfsN5YcozCPNFU")

                        return headers
                    }

                }
                request2.retryPolicy = DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
                queue2.add(request2)
            }

        }


    }




    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }



    private fun uploadBitmap(bitmap: Bitmap) {
        val volleyMultipartRequest: VolleyMultipartRequest =
            object : VolleyMultipartRequest(
                Request.Method.POST, ROOT_URL,
                Response.Listener { response ->
                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    try {

//                        val obj = JSONObject(String(response.data))
                        Toast.makeText(
                            applicationContext,

                            "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        Avatar.setImageBitmap(bitmap)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->

                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                        Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                    }
                }) {
                override fun getByteData(): Map<String, DataPart> {
                    val params: MutableMap<String, DataPart> = HashMap()
                    val imagename = System.currentTimeMillis()
                    params["avatar"] = DataPart("$imagename.png", getFileDataFromDrawable(bitmap))
                    return params
                }
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept","application/json")
                    headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@ProfileSettings))

                    return headers
                }
            }

        volleyMultipartRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest)
    }


    private fun get() {
        val URL = "http://motortraders.zydni.com/api/buyers/detail"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                val res = JSONObject(response)

                var fisrt_name = res.getString("first_name")
                var last_name = res.getString("last_name")
                var email = res.getString("email")
                var email_verified = res.getString("email_verified_at")

                var business_phone = res.getString("business_phone")

                var abn = res.getString("abn")

                var postal_code= res.getString("postal_code")
                var avatar = res.getString("avatar")
                l_id = res.getString("location_id")

                val name = ArrayList<String>()
                var Adapter1 = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, name
                )

                Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val URL = "http://motortraders.zydni.com/api/common/locations"

                val queue = Volley.newRequestQueue(this)


                val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
                object : StringRequest(
                    Method.GET, URL,
                    Response.Listener { response ->
                        status = "true"
                        spinKitView.visibility = View.GONE
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        val res = JSONArray(response)

//                var courses = arrayOf(
//                    ""
//                )
//                name.add("State")
                        if (l_id.equals("null")){
                            name.add("Location")
                        }
                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)
                            if (jsonObject.getString("id").equals(l_id)) {
                                var st = jsonObject.getString("name")
                                name.add(st)




                            }

                        }

                        for (i in 0 until res.length()) {
                            var jsonObject: JSONObject = res.getJSONObject(i)

                            if (jsonObject.getString("id").equals(l_id)) {

                            } else {
                                var st = jsonObject.getString("name")
                                name.add(st)

                            }

                        }


                        state.setAdapter(Adapter1)




                    }, Response.ErrorListener { error ->

                        spinKitView.visibility = View.GONE


                        if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                            Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
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



//                State_id = l_id

//                Toast.makeText(this,l_id,Toast.LENGTH_LONG).show()
//                try {
//                    var buyer_license_no = res.getString("buyer_license_no")
//                    License_no.setText(buyer_license_no)
//                } catch (e: JSONException ) {
//                    e.printStackTrace()
//
//                }

                if (fisrt_name != "null"){
                    First_name.text = fisrt_name
                }else{
                    First_name.text = "-"
                }
                if (fisrt_name != "null") {
                    Full_name.setText(fisrt_name)
                }else{
                    Full_name.setHint("-")
                }
                if (email != "null") {
                    Email.text = email
                }else{
                    Email.text = "-"
                }
                if (abn != "null") {
                    Abn.setText(abn)
                }else{
                    Abn.setHint("-")
                }
                if (postal_code != "null") {
                    post_code.setText(postal_code)
                }else{
                    post_code.setHint("-")
                }
                if (email != "null") {
                    Email_adress.setText(email)
                }else{
                    Email_adress.setHint("-")
                }
                if (business_phone != "null") {
                    Business_phone.setText(business_phone)
                }else{
                    Business_phone.setHint("-")
                }

//                Verification.text = email_verified

                    Glide.with(getApplicationContext()).load(avatar).fitCenter().into(Avatar)




//                Toast.makeText(this,fisrt_name.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd",response.toString())



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
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@ProfileSettings))

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

    private fun update(s_id:String) {
        spinKitView.visibility =View.VISIBLE
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//        Toast.makeText(this,s_id,Toast.LENGTH_LONG).show()

//        var name = First_name.text.toString()
        var name =Full_name.text.toString()
        var email =Email_adress.text.toString()
        var abn = Abn.text.toString()
        var pc =post_code.text.toString()

        var ph =Business_phone.text.toString()


        Error1.visibility = View.GONE
        Error2.visibility = View.GONE
        Error3.visibility = View.GONE
        Error4.visibility = View.GONE
        Error5.visibility = View.GONE
        Error6.visibility = View.GONE
//        State_id?.let { Log.i("yghgj", it) }

//        spinKitView.visibility = View.VISIBLE
//
//        visibale.visibility = View.VISIBLE



//        var Al =License_no.text.toString()

        val URL = "http://motortraders.zydni.com/api/buyers/edit-details"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = object : StringRequest(
            Method.POST, URL,
            Response.Listener { response ->
                spinKitView.visibility =View.GONE
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                spinKitView.visibility = View.GONE
//                visibale.visibility = View.GONE
                Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                get()


            }, Response.ErrorListener { error ->
                spinKitView.visibility = View.GONE
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);



                if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                    Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
//                spinKitView.visibility = View.GONE
//                visibale.visibility = View.GONE

                    try {

                        val charset: Charset = Charsets.UTF_8

                        val jsonObject = String(error.networkResponse.data, charset)
                        val data = JSONObject(jsonObject)
                        val errors: JSONObject = data.getJSONObject("errors")

                        var em: JSONArray? = null


                        em = errors.getJSONArray("first_name")
                        if (em.equals(null)) {


                        } else {
                            val email: JSONArray = errors.getJSONArray("first_name")
                            Error1.setText(email.getString(0))
                            Error1.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                    } catch (error: UnsupportedEncodingException) {
                    }

                    try {

                        val charset: Charset = Charsets.UTF_8

                        val jsonObject = String(error.networkResponse.data, charset)
                        val data = JSONObject(jsonObject)
                        val errors: JSONObject = data.getJSONObject("errors")

                        var ps: JSONArray? = null

//
                        ps = errors.getJSONArray("business_phone")
                        if (ps.equals(null)) {


                        } else {
                            val password: JSONArray = errors.getJSONArray("business_phone")
                            Error3.setText(password.getString(0))
                            Error3.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                    } catch (error: UnsupportedEncodingException) {
                    }
                    try {

                        val charset: Charset = Charsets.UTF_8

                        val jsonObject = String(error.networkResponse.data, charset)
                        val data = JSONObject(jsonObject)
                        val errors: JSONObject = data.getJSONObject("errors")

                        var em: JSONArray? = null


                        em = errors.getJSONArray("email")
                        if (em.equals(null)) {


                        } else {
                            val email: JSONArray = errors.getJSONArray("email")
                            Error2.setText(email.getString(0))
                            Error2.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                    } catch (error: UnsupportedEncodingException) {
                    }

                    try {

                        val charset: Charset = Charsets.UTF_8

                        val jsonObject = String(error.networkResponse.data, charset)
                        val data = JSONObject(jsonObject)
                        val errors: JSONObject = data.getJSONObject("errors")

                        var ps: JSONArray? = null

//
                        ps = errors.getJSONArray("address_line")
                        if (ps.equals(null)) {


                        } else {
                            val password: JSONArray = errors.getJSONArray("address_line")
                            Error4.setText(password.getString(0))
                            Error4.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                    } catch (error: UnsupportedEncodingException) {
                    }
                    try {

                        val charset: Charset = Charsets.UTF_8

                        val jsonObject = String(error.networkResponse.data, charset)
                        val data = JSONObject(jsonObject)
                        val errors: JSONObject = data.getJSONObject("errors")

                        var em: JSONArray? = null


                        em = errors.getJSONArray("location_id")
                        if (em.equals(null)) {


                        } else {
                            val email: JSONArray = errors.getJSONArray("location_id")
                            Error5.setText("The location field is required.")
                            Error5.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                    } catch (error: UnsupportedEncodingException) {
                    }

                    try {

                        val charset: Charset = Charsets.UTF_8

                        val jsonObject = String(error.networkResponse.data, charset)
                        val data = JSONObject(jsonObject)
                        val errors: JSONObject = data.getJSONObject("errors")

                        var ps: JSONArray? = null

//
                        ps = errors.getJSONArray("postal_code")
                        if (ps.equals(null)) {


                        } else {
                            val password: JSONArray = errors.getJSONArray("postal_code")
                            Error6.setText(password.getString(0))
                            Error6.visibility = View.VISIBLE
                        }

                    } catch (e: JSONException) {
                    } catch (error: UnsupportedEncodingException) {
                    }


                }


            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@ProfileSettings))
                return headers
            }

            override fun getParams()  : Map<String, String>?  {

                val params: MutableMap<String, String> = HashMap()



                params["first_name"]=name
                params["email"]=email
                params["business_phone"]=ph

                params["address_line"]= abn
                params["location_id"]= s_id
                params["postal_code"]= pc
                params["_method"]="PUT"

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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//    if (resultCode == RESULT_OK) {
//        bitmap = data!!.extras!!["data"] as Bitmap?
//        front_image.setImageBitmap(bitmap)
//
//
//    }
        if (requestCode == SELECT_PHOTO && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, path)
                uploadBitmap(bitmap)
                spinKitView.visibility = View.VISIBLE
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode === CAMERA_REQUEST1 && resultCode === RESULT_OK) {

            try {
//                bitmap = data!!.extras!!.get("data") as Bitmap
//                Avatar.setImageBitmap(bitmap)
//                uploadBitmap(bitmap)

                bitmap = BitmapFactory.decodeFile(currentphoto)

                uploadBitmap(bitmap)
                spinKitView.visibility = View.VISIBLE
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            } catch (e: IOException) {
                e.printStackTrace()
            }


        }

    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val filename = "Photo"
                val storagedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                try {
                    val imagefile = File.createTempFile(filename, ".jpg", storagedirectory)
                    currentphoto = imagefile.absolutePath
                    val imageuri = FileProvider.getUriForFile(this,
                        "com.app.compare_my_trade.fileprovider2",
                        imagefile)
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri)
                    startActivityForResult(intent, CAMERA_REQUEST1)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            PICK_FROM_GALLERY -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, SELECT_PHOTO)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java).apply {



        }
        startActivity(intent)
    }

}