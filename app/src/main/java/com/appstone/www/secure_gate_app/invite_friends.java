package com.appstone.www.secure_gate_app;
import android.widget.EditText;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.VolleyError;
import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.*;


public class invite_friends extends AppCompatActivity {
EditText apartment,friendnumber,noofpeople;
    Button insert, show;
    TextView results;

    RequestQueue requestQueue;
    String insertUrl = "http://192.168.0.4/SecureGateApp/insertInvitation.php";
    String showUrl = "http://192.168.0.4/SecureGateApp/showInvitation.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);

        apartment = (EditText) findViewById(R.id.apartment);
        friendnumber = (EditText) findViewById(R.id.friendnumber);
        noofpeople = (EditText) findViewById(R.id.noofpeople);
        insert = (Button) findViewById(R.id.insert);
        show = (Button) findViewById(R.id.show);
        results = (TextView) findViewById(R.id.results);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,showUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray invitation = response.getJSONArray("invitation");
                            for (int i = 0; i < invitation.length(); i++) {

                                JSONObject invitations = invitation.getJSONObject(i);

                                String apartment = invitations.getString("apartment");
                                String friendnumber = invitations.getString("friendnumber");
                                String noofpeople = invitations.getString("noofpeople");
                                results.append(apartment + " " + noofpeople + " " + friendnumber + "\n");
                            }
                            results.append("===\n");

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                    requestQueue.add(jsonObjectRequest);
            }

        });



    }
}
