package com.trendinganalysis.conetrading;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationService extends Service {
    final static int notifyID = 9000;
    int numMessages = 0;
    DataHandler dataHandler = MainActivity.dataHandler;
    DatabaseHandler databaseHandler = MainActivity.databaseHandler;
    SharedPrefs sharedPrefs = MainActivity.sharedPrefs;


    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i("C-One Notif Service", "Service was Created");
//		Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i("C-One Notif Service", "Service Started");
//		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Intent resultIntent = new Intent(this, UpdateManager.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, notifyID,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Sets an ID for the notification, so it can be updated
        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentText("tap to open update options")
                .setTicker("Notifications from C-One Trading Trend Analysis")
//                .setContentTitle(dataHandler.getNotifMessage())
                .setSmallIcon(R.mipmap.ic_launcher);
        // Set pending intent
        mNotifyBuilder.setContentIntent(resultPendingIntent);
        // Set Vibrate, Sound and Light
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;

        mNotifyBuilder.setDefaults(defaults);
        // Set the content for Notification
//        if(intent.getStringExtra("intntdata")!=null)mNotifyBuilder.setContentTitle(intent.getStringExtra("intntdata"));
        // Set autocancel
        mNotifyBuilder.setAutoCancel(true);

        // Post a notification
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());

        broadcast();


        stopSelf();
    }

    @Override
    public void onDestroy() {
        Log.i("C-One Notif Service", "Service Destroyed");
//		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }

    private boolean isNotificationVisible() {
        Intent notificationIntent = new Intent(this, UpdateManager.class);
        PendingIntent test = PendingIntent.getActivity(this, notifyID, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }

    public void broadcast() {

        RequestParams params = new RequestParams();

//        for (int id_series : databaseHandler.getAllServerID()) {
//            params.add("id_series[]", "" + id_series);
//        }
//
        params.put("id_series", databaseHandler.commaDelimited(databaseHandler.getAllServerID()));

        Log.i("id_series:", params.toString());

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(this, sharedPrefs.getValueStr(SharedPrefs.KEY_DOMAIN_NAME) + "/synch/getAllNewData.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                List<Integer> ids = new ArrayList<>();

                try {
                    // Extract JSON array from the response
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    // If no of array elements is not zero
                    if (arr.length() != 0) {
                        // Loop through each array element, get JSON object which has userid and username
                        for (int i = 0; i < arr.length(); i++) {

                            JSONObject json = arr.getJSONObject(i);
                            ids.add(json.getInt("id"));
                        }

//                        Toast.makeText(getApplicationContext(), "mao ni gikan sa remote ddatabaseHandler: "+ ids, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {

                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();
                }
            }


        });
    }

}