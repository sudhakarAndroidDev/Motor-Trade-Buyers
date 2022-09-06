package com.app.compare_my_trade.ui.postauthenticationui

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle

import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

import android.widget.*

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment

import com.app.compare_my_trade.R

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.android.volley.*

import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.compare_my_trade.CarDetails

import com.app.compare_my_trade.ui.postauthenticationui.ui.home.HomeFragment
import com.app.compare_my_trade.ui.postauthenticationui.ui.managebids.ManageBidsFragment
import com.app.compare_my_trade.ui.postauthenticationui.ui.moreoptions.MoreOptionsFragment

import com.app.compare_my_trade.ui.postauthenticationui.ui.notifications.NotificationsFragment
import com.app.compare_my_trade.utils.PreferenceUtils

import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

import com.app.compare_my_trade.ui.login.LoginFragment
import com.bumptech.glide.Glide


class HomeActivity : AppCompatActivity() {
    private var backPressedTime:Long = 0

    lateinit var  navView: BottomNavigationView
    lateinit var profile:ImageView
    lateinit var div:RelativeLayout
    lateinit var main_name:TextView


    lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)



        navView = findViewById(R.id.nav_view)
        profile = findViewById(R.id.profile_photo)
        div = findViewById(R.id.div)
        main_name = findViewById(R.id.main_name)

        if (intent.getStringExtra("nav").toString().equals("more")){
            navView.setSelectedItemId(R.id.navigation_more)
        }
        if (intent.getStringExtra("n_success").toString().equals("n_success")){
            val badgeDrawable = navView.getBadge(R.id.navigation_notifications)
            if (badgeDrawable != null) {
                badgeDrawable.isVisible = false
                badgeDrawable.clearNumber()
            }
        }


        profile.setOnClickListener {

            if (PreferenceUtils.getTokan(this) == null) {

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
                        Intent(this, LoginFragment::class.java).apply {
                        }
                    startActivity(intent)
                    dialog.dismiss()
                }

                //   dialog!!.window?.setBackgroundDrawableResource(R.drawable.currentbackground)
                // dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
                dialog.show()



            } else {
                navView.setSelectedItemId(R.id.navigation_more)

            }

        }

        Glide.with(applicationContext).load("http://motortraders.zydni.com/images/unknown.png").fitCenter().into(profile)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signOut()








        if (intent.getStringExtra("nav").toString().equals("more")){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_activity_home, MoreOptionsFragment()).commit()
        }else{
            supportFragmentManager.beginTransaction().replace(R.id.fragment_activity_home, HomeFragment())
                .commit()
        }


        if (PreferenceUtils.getTokan(this) == null) {

            navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->


                when (item.itemId) {

                    R.id.navigation_home -> {
                        div.visibility = View.VISIBLE
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_activity_home, HomeFragment() ).commit()
                        main_name.text = "Home"
                    }
                    R.id.navigation_manage_bids -> {


                        div.visibility = View.VISIBLE

                        main_name.text = "Manage Bids"



                        val dialog = Dialog(this)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
                        dialog.setContentView(R.layout.alert_dialogbox)
                        dialog.setCancelable(false)

                        val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                        val oktext = dialog.findViewById<TextView>(R.id.yestext)
                        val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                        val title = dialog.findViewById<TextView>(R.id.title)
                        val massage = dialog.findViewById<TextView>(R.id.message)

                        title.setText("Alert")
                        oktext.text = "OK"
                        massage.setText("You need sign in or sign up before continuing")

                        cancel.setOnClickListener {
                            val intent =
                                Intent(this, HomeActivity::class.java).apply {
                                }
                            startActivity(intent)
                            dialog.dismiss()
                        }
                        ok.setOnClickListener {

                            val intent =
                                Intent(this, LoginFragment::class.java).apply {
                                }
                            startActivity(intent)
                            dialog.dismiss()
                        }


                        dialog.show()

                    }
                    R.id.navigation_notifications -> {



                        val dialog = Dialog(this)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
                        dialog.setContentView(R.layout.alert_dialogbox)
                        dialog.cancel()

                        val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
                        val oktext = dialog.findViewById<TextView>(R.id.yestext)
                        val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
                        val title = dialog.findViewById<TextView>(R.id.title)
                        val massage = dialog.findViewById<TextView>(R.id.message)
                        dialog.setCancelable(false)
                        title.setText("Alert")
                        oktext.text = "OK"
                        massage.setText("You need sign in or sign up before continuing")

                        cancel.setOnClickListener {
                            val intent =
                                Intent(this, HomeActivity::class.java).apply {
                                }
                            startActivity(intent)
                            dialog.dismiss()
                            dialog.dismiss()
                        }
                        ok.setOnClickListener {

                            val intent =
                                Intent(this, LoginFragment::class.java).apply {
                                }
                            startActivity(intent)
                            dialog.dismiss()
                        }



                        dialog.show()

                    }
                    R.id.navigation_more -> {
                        div.visibility = View.GONE
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_activity_home, MoreOptionsFragment()).commit()
                    }
                }

                true

            })

        }else{
            navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
                var temp: Fragment? = null
                when (item.itemId) {

                    R.id.navigation_home -> {
                        temp = HomeFragment()
                        div.visibility = View.VISIBLE
                        main_name.text = "Home"
                    }
                    R.id.navigation_manage_bids -> {
                        div.visibility = View.VISIBLE
                        temp = ManageBidsFragment()
                        main_name.text = "Manage Bids"

                    }
                    R.id.navigation_notifications -> {
                        div.visibility = View.GONE
                        temp = NotificationsFragment()
                        val badgeDrawable = navView.getBadge( R.id.navigation_notifications)
                        if (badgeDrawable != null) {
                            badgeDrawable.isVisible = false
                            badgeDrawable.clearNumber()
                        }
                    }
                    R.id.navigation_more -> {
                        div.visibility = View.GONE
                        temp = MoreOptionsFragment()
                    }
                }
                supportFragmentManager.beginTransaction().replace(R.id.fragment_activity_home, temp!!).commit()
                true

            })

        }










    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.window!!.setBackgroundDrawableResource(R.drawable.currentbackground)
            dialog.setContentView(R.layout.alert_dialogbox)

            val ok = dialog.findViewById<RelativeLayout>(R.id.yes)
            val oktext = dialog.findViewById<TextView>(R.id.yestext)
            val cancel = dialog.findViewById<RelativeLayout>(R.id.no)
            val cancel_text = dialog.findViewById<TextView>(R.id.no_text)
            val title = dialog.findViewById<TextView>(R.id.title)
            val massage = dialog.findViewById<TextView>(R.id.message)

            title.setText("Alert")
            cancel_text.text = "No"
            oktext.text = "YES"
            massage.setText("Are you sure want to Exit")

            cancel.setOnClickListener {

                dialog.dismiss()
            }
            ok.setOnClickListener {
                super.onBackPressed()
                finishAffinity()
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




            return
        } else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // ...
            }
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // ...
            }
    }




    private fun res() {


        val URL = "http://motortraders.zydni.com/api/buyers/detail"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            { response ->

                val res = JSONObject(response)




                try {
                    var avatar = res.getString("avatar")


                    if(applicationContext !=null) {

                        Glide.with(applicationContext).load(avatar).fitCenter().into(profile)

                    }
                }catch (e: Exception){

                }









//                Toast.makeText(this,fisrt_name.toString(),Toast.LENGTH_LONG).show()
//                Log.i("jdhfisd",response.toString())



            }, { error ->


            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@HomeActivity))

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












    override fun onStart() {
        super.onStart()


        val URL = "http://motortraders.zydni.com/api/buyers/notification/list"

        val queue = Volley.newRequestQueue(this)


        val request: StringRequest = @SuppressLint("ClickableViewAccessibility")
        object : StringRequest(
            Method.GET, URL,
            Response.Listener { response ->



                val res = JSONObject(response)

                var successfulBids = res.getJSONArray("data")
                var total = res.getInt("total")

                val last_notification= 0
                try {
                    var last = 0

                    try {
                        for (i in 0 until successfulBids.length()) {
                            val data: JSONObject = successfulBids.getJSONObject(i)


                            var is_read =data.getInt("is_read")
                            var id =data.getString("id")
                            var title =data.getString("title")
                            var notification_message =data.getString("notification_message")
                            var car_id =data.getString("car_id")

                            if (is_read.equals(0)){
                                last++

                            }

                            if (is_read.equals(0) && last_notification.equals(i)){
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    val channel = NotificationChannel("My Notification",
//                                        "My Notification",
//                                        NotificationManager.IMPORTANCE_HIGH)
//                                    val manager =
//                                        getSystemService(NotificationManager::class.java)
//                                    manager.createNotificationChannel(channel)
//                                }
//                                val uri =
//                                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//                                val builder: NotificationCompat.Builder =
//                                    NotificationCompat.Builder(this, "My Notification")
//                                        .setSound(uri)
//                                        .setContentTitle(title)
//                                        .setContentText(notification_message)
//                                        .setAutoCancel(true)
//                                        .setSmallIcon(R.drawable.logo)
//                                        .setLargeIcon(BitmapFactory.decodeResource(applicationContext.getResources(), R.drawable.logo))
//                                        .setVibrate(longArrayOf(1000, 1000, 1000,
//                                            1000, 1000))
//                                        .setOnlyAlertOnce(true)
//
//
//
//                                val notificationIntent =
//                                    Intent(this, CarDetails::class.java).apply {
//                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                                        putExtra("v_id",car_id)
//                                        putExtra("n_id",id)
//                                    }
//                                val contentIntent =
//                                    PendingIntent.getActivity(this, 0, notificationIntent,
//                                        PendingIntent.FLAG_UPDATE_CURRENT)
//                                builder.setContentIntent(contentIntent)
//
//                                // Add as notification
//
//                                // Add as notification
//                                val manager =
//                                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//                                manager.notify(0, builder.build())

                            }




//                            Log.i("frfwfwefwefdwede",last_notification.toString() +"  "+i)

//                            if (PreferenceUtils.getLength(this) == 0) {
//                                PreferenceUtils.saveLength(successfulBids.length(), this)
//                            } else if (PreferenceUtils.getLength(this) < i) {
//                                PreferenceUtils.saveLength(successfulBids.length(),
//                                    this)
//                                try {
//
//
//                                } catch (e: JSONException) {
//                                }
//                            }


                        }
                        if (last != 0){
                            var badge = navView.getOrCreateBadge(R.id.navigation_notifications)
                            badge.isVisible = true

                            badge.number = last
                        }
                    } catch (e: Exception) {

                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->




            }) {



            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept","application/json")
                headers.put("Authorization","Bearer "+ PreferenceUtils.getTokan(this@HomeActivity))

                return headers
            }



        }
        request.retryPolicy = DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(request)
        res()

    }


}