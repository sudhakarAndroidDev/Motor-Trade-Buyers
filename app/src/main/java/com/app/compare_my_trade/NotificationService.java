package com.app.compare_my_trade;

import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.app.Service ;
import android.content.Intent ;
import android.os.Handler ;
import android.os.IBinder ;
//import android.support.v4.app.NotificationCompat ;
import android.util.Log ;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.compare_my_trade.ui.postauthenticationui.HomeActivity;
import com.app.compare_my_trade.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer ;
import java.util.TimerTask ;
public class NotificationService extends Service {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    Timer timer ;
    TimerTask timerTask ;
    String TAG = "Timers" ;
    int Your_X_SECS = 5 ;

    String id = null,title = null,notification_message = null;

    @Override
    public IBinder onBind (Intent arg0) {
        return null;
    }
    @Override
    public int onStartCommand (Intent intent , int flags , int startId) {
        Log. e ( TAG , "onStartCommand" ) ;
        super .onStartCommand(intent , flags , startId) ;
        startTimer() ;
        return START_STICKY ;
    }
    @Override
    public void onCreate () {
        Log. e ( TAG , "onCreate" ) ;
    }
    @Override
    public void onDestroy () {
        Log. e ( TAG , "onDestroy" ) ;
        stopTimerTask() ;
        super .onDestroy() ;
    }
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler() ;
    public void startTimer () {
        timer = new Timer() ;
        initializeTimerTask() ;
        timer .schedule( timerTask , 5000 , Your_X_SECS * 1000 ) ; //
    }
    public void stopTimerTask () {
        if ( timer != null ) {
            timer .cancel() ;
            timer = null;
        }
    }
    public void initializeTimerTask () {
        timerTask = new TimerTask() {
            public void run () {
                handler .post( new Runnable() {
                    public void run () {
                        createNotification() ;
                    }
                }) ;
            }
        } ;
    }
    private void createNotification () {











        String URL = "http://motortraders.zydni.com/api/buyers/notification/list";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject res = new JSONObject(response);
                    JSONArray successfulBids = res.getJSONArray("data");

                    for (int i = 0 ;i<successfulBids.length();i++) {
                        JSONObject data = successfulBids.getJSONObject(i);

                        int last = successfulBids.length() - 1;
                        if (PreferenceUtils.getLength(NotificationService.this) == 0 ) {
                            PreferenceUtils.saveLength(successfulBids.length(), NotificationService.this);
                        } else if (PreferenceUtils.getLength(NotificationService.this) < successfulBids.length()) {

                            PreferenceUtils.saveLength(successfulBids.length(), NotificationService.this);


                            try {

                                id =data.getString("id");
                                title =data.getString("title");
                                notification_message =data.getString("notification_message");


                                NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
                                mBuilder.setContentTitle( title ) ;
                                mBuilder.setContentText(notification_message);
                                mBuilder.setAutoCancel(true);
                                mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                                mBuilder.setOnlyAlertOnce(true);
                                mBuilder.setSmallIcon(R.drawable. logo ) ;
                                mBuilder.setAutoCancel( true ) ;
                                if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
                                    int importance = NotificationManager. IMPORTANCE_HIGH ;
                                    NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                                    mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                                    assert mNotificationManager != null;
                                    mNotificationManager.createNotificationChannel(notificationChannel) ;
                                }
                                assert mNotificationManager != null;
                                mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;


                            } catch ( JSONException e) {

                            }

                        }


                    }

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + PreferenceUtils.getTokan(NotificationService.this));

                return params;
            }


        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);






    }
}