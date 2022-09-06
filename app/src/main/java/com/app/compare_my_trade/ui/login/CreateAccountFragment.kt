package com.app.compare_my_trade.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.android.volley.*
import com.android.volley.toolbox.Volley

import com.app.compare_my_trade.R
import com.app.compare_my_trade.ui.MotorViewModel
import com.app.compare_my_trade.ui.base.BaseFragment
import com.app.compare_my_trade.ui.base.BaseNavigator
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity
import kotlinx.android.synthetic.main.prograssbar.*
import org.koin.android.ext.android.inject
import java.util.*
import com.android.volley.toolbox.StringRequest

//import com.facebook.CallbackManager
//import com.facebook.FacebookCallback
//import com.facebook.FacebookException
//import com.facebook.FacebookSdk
//import com.facebook.login.LoginResult
//import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import org.json.JSONException



import org.json.JSONObject

import org.json.JSONArray
import java.io.UnsupportedEncodingException


import java.nio.charset.Charset
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.compare_my_trade.CarImage.CarImage
import com.app.compare_my_trade.CarImageAdapter2
import com.app.compare_my_trade.CarImageModel2
import com.app.compare_my_trade.Filter.FilterAdapter
import com.app.compare_my_trade.Filter.MakeModel
import com.app.compare_my_trade.Filter.FilterShowAdapter
import com.app.compare_my_trade.Filter.FilterShowModel
import com.app.compare_my_trade.utils.PreferenceUtils
import com.github.ybq.android.spinkit.SpinKitView
import java.lang.Error


class CreateAccountFragment: AppCompatActivity(){


    private val motorViewModel: MotorViewModel by inject()


    lateinit var last_name  : EditText
    lateinit var email  : EditText
    lateinit var phone  : EditText
    lateinit var address_line  : EditText
    lateinit var post_code  : EditText
    lateinit var password  : EditText
//    private var facebook_login: LoginButton? = null
    lateinit var relativeLayout: Button
    lateinit var google_button:Button
    lateinit var state:Spinner
    lateinit var Error:TextView
    lateinit var Error2:TextView
    lateinit var Error3:TextView
    lateinit var Error4:TextView
    lateinit var Error5:TextView
    lateinit var Error6:TextView
    lateinit var Error7:TextView

    lateinit var spinKitView: SpinKitView


//    lateinit var FirstName  : String
//    lateinit var LastName  : String
//    lateinit var EmailAddress  : String
//    lateinit var PhoneNo  : String
//    lateinit var AddressLine  : String
//    lateinit var PostCode  : String
//    lateinit var SetPassword  : String

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
//    private var callbackManager: CallbackManager? = null

    var State_id: String? =null
    var status:String? = null

    lateinit var login_tv:TextView
    lateinit var back:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_account)

        var contine_button = findViewById<Button>(R.id.continue_btn)
        last_name = findViewById(R.id.full_name)
        email = findViewById(R.id.email_address)
        phone = findViewById(R.id.phone)
        address_line = findViewById(R.id.address_line)
        post_code = findViewById(R.id.post_code)
        password = findViewById(R.id.set_password)
        state = findViewById(R.id.state)
        Error = findViewById(R.id.error)
        Error2 = findViewById(R.id.error2)
        Error3 = findViewById(R.id.error3)
        Error4 = findViewById(R.id.error4)
        Error5 = findViewById(R.id.error5)
        Error6 = findViewById(R.id.error6)
        Error7 = findViewById(R.id.error7)


        spinKitView = findViewById<SpinKitView>(R.id.progressBar)

        login_tv = findViewById(R.id.login_tv)
        login_tv.setOnClickListener {
            val intent = Intent(this, LoginFragment::class.java)
            startActivity(intent)
        }
        back = findViewById(R.id.back)

        val URL = "http://motortraders.zydni.com/api/common/locations"

        val queue = Volley.newRequestQueue(this)
        val name = ArrayList<String>()
        name.add("Location")
        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->

                status = "true"
                val res = JSONArray(response)

//                var courses = arrayOf(
//                    ""
//                )
//                name.add("State")

                for (i in 0 until res.length()) {
                    var jsonObject: JSONObject = res.getJSONObject(i)


                        var st = jsonObject.getString("name")


                        name.add(st)


                }


                val Adapter1 = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, name
                )
                Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                state.setAdapter(Adapter1)

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
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)


        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()

                var State_name:String? = null
                State_name = state.selectedItem.toString()

                val URL2 = "http://motortraders.zydni.com/api/common/locations"

                val queue2 = Volley.newRequestQueue(view.context)


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
                                State_id = jsonObject.getString("id")

//                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
                            }

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


            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }




//
//        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())
//
//        callbackManager = CallbackManager.Factory.create()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//        relativeLayout = view.findViewById(R.id.Relative) as Button
        google_button = findViewById(R.id.google_login) as Button

        google_button.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, Req_Code)
        }
//        facebook_login = view.findViewById<View>(R.id.facebook_login) as LoginButton
//
//
//        facebook_login!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
//            //            fun onSuccess(loginResult: LoginResult) {
////                info.setText(
////                    """
////                User ID: ${loginResult.accessToken.userId}
////                Auth Token: ${loginResult.accessToken.token}
////                """.trimIndent()
////                )
////            }
//            override fun onSuccess(result: LoginResult?) {
//
//                if (result != null) {
////                    Log.i("fgdhfdfsgsh",result.accessToken.userId)
////                    Toast.makeText(activity,result.accessToken.userId,Toast.LENGTH_SHORT).show()
////                    val intent = Intent(activity, HomeActivity::class.java)
////                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
////                    startActivity(intent)
//
//
//                }
//
//
//            }
//
//            override fun onCancel() {
//
//                Log.i("fgdhfdfsgsh","cancel")
//            }
//
//            override fun onError(e: FacebookException) {
//                Log.i("fgdhfdfsgsh","error")
//            }
//
//
//        })
//
//
//
//
//
//        relativeLayout.setOnClickListener {
//
//            facebook_login!!.performClick()
//        }

        back.setOnClickListener {

            super.onBackPressed()
        }


        contine_button.setOnClickListener {
            spinKitView.visibility = View.VISIBLE
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if (status.equals("true")) {
                Error.visibility = View.GONE
                Error2.visibility = View.GONE
                Error3.visibility = View.GONE
                Error4.visibility = View.GONE
                Error5.visibility = View.GONE
                Error6.visibility = View.GONE
                Error7.visibility = View.GONE


                var State_name: String? = null
                State_name = state.selectedItem.toString()

                if (State_name.equals("Location")) {
                    Error5.setText("The location is required.")
                    Error5.visibility = View.VISIBLE
//                            Toast.makeText(this, "The make is required.", Toast.LENGTH_LONG)
//                                .show()
                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                } else {


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
                                    var state_id = jsonObject.getString("id")

                                    continebutton(state_id);
//                                Toast.makeText(view.context,State_id,Toast.LENGTH_LONG).show()
                                }

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

    }

    private fun continebutton(state_id:String) {
        Error.visibility= View.GONE


         var LastName= last_name.text.toString()
         var EmailAddress= email.text.toString()
         var PhoneNo= phone.text.toString()
         var AddressLine= address_line.text.toString()
         var PostCode= post_code.text.toString()
         var SetPassword= password.text.toString()





        Error.visibility = View.GONE
        Error2.visibility = View.GONE
        Error3.visibility = View.GONE
        Error4.visibility = View.GONE
        Error5.visibility = View.GONE
        Error6.visibility = View.GONE
        Error7.visibility = View.GONE













            val URL = "http://motortraders.zydni.com/api/buyers/register"

            val queue = Volley.newRequestQueue(this)


            val request: StringRequest = object : StringRequest(
                Method.POST, URL,
                Response.Listener { response ->
                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                var res = JSONObject(response)
//
//                var access_token = res.getString("access_token")
//
//                PreferenceUtils.saveTokan(access_token,activity)

                    val intent = Intent(this, LoginFragment::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)

                    Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()


                }, Response.ErrorListener { error ->



                    spinKitView.visibility = View.GONE
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);



                    if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                        Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
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
                                Error.setText(email.getString(0))
                                Error.visibility = View.VISIBLE
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
                            ps = errors.getJSONArray("email")
                            if (ps.equals(null)) {


                            } else {
                                val password: JSONArray = errors.getJSONArray("email")
                                Error2.setText(password.getString(0))
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

                            var em: JSONArray? = null


                            em = errors.getJSONArray("business_phone")
                            if (em.equals(null)) {


                            } else {
                                val email: JSONArray = errors.getJSONArray("business_phone")
                                Error3.setText(email.getString(0))
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
                        try {

                            val charset: Charset = Charsets.UTF_8

                            val jsonObject = String(error.networkResponse.data, charset)
                            val data = JSONObject(jsonObject)
                            val errors: JSONObject = data.getJSONObject("errors")

                            var ps: JSONArray? = null

//
                            ps = errors.getJSONArray("password")
                            if (ps.equals(null)) {


                            } else {
                                val password: JSONArray = errors.getJSONArray("password")
                                Error7.setText(password.getString(0))
                                Error7.visibility = View.VISIBLE
                            }

                        } catch (e: JSONException) {
                        } catch (error: UnsupportedEncodingException) {
                        }

                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept", "application/json")

                    return headers
                }

                override fun getParams(): Map<String, String>? {

                    val params: MutableMap<String, String> = HashMap()




                    params["first_name"] = LastName
                    params["email"] = EmailAddress
                    params["password"] = SetPassword

//                params["business_name"]=""
                    params["business_phone"] = PhoneNo
//                params["business_email"]= EmailAddress
//                params["abn"]= ""
//                params["buyer_license_no"]= ""
//                params["business_registration_document"]= ""

                    params["address_line"] = AddressLine
                    params["location_id"] = state_id
                    params["postal_code"] = PostCode



                    return params


                }


            }
            request.retryPolicy = DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(request)

//




    }













    override    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }

    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)

//            val intent = Intent(activity, HomeActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//            startActivity(intent)


            jkf()


        } catch (e: ApiException) {

        }


    }

    private fun jkf() {
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
//                val personName = acct.displayName
//                val personGivenName = acct.givenName
//                val personFamilyName = acct.familyName
//                val personEmail = acct.email
//                val personId = acct.id
            var userName = (acct!!.displayName)
            var userEmail =(acct.email)
            var userId = (acct.id)

            val requestQueue = Volley.newRequestQueue(this)
            val URL = "http://motortraders.zydni.com/api/buyers/social-login"


            val request: StringRequest = object : StringRequest(
                Method.POST, URL,
                com.android.volley.Response.Listener { response ->


                    var res = JSONObject(response)
                    Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
                    var access_token = res.getString("access_token")
//                    var user = res.getJSONObject("user")
//                    var subscription_status = user.getJSONObject("subscription_status")
//
//                    var status = subscription_status.getString("status")



                    PreferenceUtils.saveTokan(access_token,this)

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
//                    Toast.makeText(
//                            activity,
//                        res.toString(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        Log.e("fdsfsd",res.toString())


                }, com.android.volley.Response.ErrorListener { error ->

                    if (error.toString().equals("com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"motortraders.zydni.com\": No address associated with hostname")){
                        Toast.makeText(this,"Check your internet connection", Toast.LENGTH_LONG).show()
                    }else {

                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    }
//                        Toast.makeText(
//                            activity,
//                            "Error: password in Abcd@5 this format",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        Log.e("fdsfsd",error.toString())
                }) {



                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Accept","application/json")

                    return headers
                }

                override fun getParams()  : Map<String, String>?  {

                    val params: MutableMap<String, String> = HashMap()





                    params["email"]=userEmail.toString()
                    params["first_name"]=userName.toString()
                    params["google_id"]=userId.toString()




                    return params






                }





            }
            request.retryPolicy = DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            requestQueue.add(request)


//            Toast.makeText(activity, userEmail, Toast.LENGTH_SHORT).show()
//            Toast.makeText(activity, userId, Toast.LENGTH_SHORT).show()
//            Toast.makeText(activity, userName, Toast.LENGTH_SHORT).show()
//
//            Log.i("fhiufhfofa", userEmail.toString())
//            Log.i("fhiufhfofa", userId.toString())
//            Log.i("fhiufhfofa", userName.toString())
        }
    }

}
