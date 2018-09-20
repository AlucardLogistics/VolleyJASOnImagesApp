package com.example.sadic.volleyjasonimagesapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;
    MyRecyclerView adapter;
    ArrayList<Album> albumList = new ArrayList<>();
    ProgressDialog pd;
    Album album;
    Button btLoad, btObjJSOn;
    TextView txtResponse;

    private String urlJsonObj = "https://api.androidhive.info/volley/person_object.json";
    private String urlJSONarr = "http://jsonplaceholder.typicode.com/photos/";
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLoad = findViewById(R.id.btLoad);
        btObjJSOn = findViewById(R.id.btObj);
        recyclerView = findViewById(R.id.myRecView);
        txtResponse = findViewById(R.id.txtResponse);



        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        adapter = new MyRecyclerView(albumList, this);

        btLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJSOnArrReq();
            }
        });

        btObjJSOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonObjectRequest();
            }
        });

        pd = new ProgressDialog(this);
        pd.setTitle("My Progress Dialog");
        pd.setMessage("Fetching data from the database!");
        pd.setCancelable(false);
    }

    private void makeJSOnArrReq() {
        showDialog();

        JsonArrayRequest req = new JsonArrayRequest(
                urlJSONarr,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: response: " + response.toString());
                        try {
                            jsonResponse = "";
                                for(int i = 0; i < response.length(); i++) {

                                    JSONObject albumObj = (JSONObject) response.get(i);

                                    String albumId = albumObj.getString("albumId");
                                    String id = albumObj.getString("id");
                                    String title = albumObj.getString("title");
                                    String imgUrl = albumObj.getString("url");
                                    String thumbUrl = albumObj.getString("thumbnailUrl");

                                    album = new Album(albumId, id, title, imgUrl, thumbUrl);
                                    //album = new Album(albumId, id, title);
                                    albumList.add(album);

                                    recyclerView.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                //e.printStackTrace();
                            Log.d(TAG, "onResponse: message: " + e.getMessage());
                            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            dismissDialog();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "onErrorResponse message: " + error.getMessage());
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        dismissDialog();
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(req);
    }

    private void makeJsonObjectRequest() {

        showDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("name");
                    String email = response.getString("email");
                    JSONObject phone = response.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");

                    jsonResponse = "";
                    jsonResponse += "Name: " + name + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";
                    jsonResponse += "Home: " + home + "\n\n";
                    jsonResponse += "Mobile: " + mobile + "\n\n";

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                dismissDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                dismissDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showDialog() {
        if(!pd.isShowing()) {
            pd.show();
        }
    }

    private void dismissDialog() {
        if(pd.isShowing()) {
            pd.dismiss();
        }
    }
}
