package com.appxperts.volleystudy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";
    String url = "http://babesbabesapp.appspot.com/fetchbabesjson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doWork(View v) {

        Cache cache = VolleyStudyApplication.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
                Log.d(VolleyStudyApplication.TAG, "CACHED Data " + data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d(VolleyStudyApplication.TAG, "UnsupportedEncodingException : " + e.toString()
                );
            }
        } else {
            // Cached response doesn't exists. Make network call here
            talkToServer();
        }
    }

    private void talkToServer() {
        // Tag used to cancel the request
        String tag_string_req = "string_req";

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(VolleyStudyApplication.TAG, response.toString());
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(VolleyStudyApplication.TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

            /**
             * Passing some request params
             * */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("password", "password123");

                return params;
            }

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return headers;
            }
        };

// -------------->>Adding request to request queue
        VolleyStudyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);

        VolleyStudyApplication.getInstance().getRequestQueue().getCache().invalidate(url, true);

    }


    public void useBasicVolley(View v) {

        stringRequestWork();

        //ImageRequest
        //JsonRequest
        //JsonArrayRequest
        //JsonObjectRequest


    }


    private void stringRequestWork() {

        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading .. ");
        pd.show();
        //StringRequest----->>
        StringRequest st = new StringRequest(Request.Method.GET, "http://gufranscifi.appspot.com/GetAllScienceFactJSON", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Success Response " + response);
                pd.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                System.out.println("Volley Error------>> ");
                System.out.println("Message " + error.getMessage());
                System.out.println("Network Time ms " + error.getNetworkTimeMs());
                if (error.networkResponse != null) {
                    System.out.println("    Network Response---- " + error.networkResponse.statusCode);
                    System.out.println("        Status Code : " + error.networkResponse.statusCode);
                    System.out.println("        NetworkTime ms : " + error.networkResponse.networkTimeMs);
                    System.out.println("        Not Modified : " + error.networkResponse.notModified);
                    System.out.println("        Data : " + error.networkResponse.data.toString());
                    System.out.println("        Headers : " + error.networkResponse.headers.toString());
                }

            }
        });



        rq.add(st);
    }


    private void stringRequestWithParam() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading .. ");
        pd.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };


    }
}
