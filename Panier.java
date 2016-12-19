package com.example.menuiserie.premierprojet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by menuiserie on 18/12/2016.
 */

public class Panier extends MainActivity{


    private RecyclerView rv;

    public RecyclerView getRv(){
        return rv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);


        // String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        //
        //textView.setText("ahah");
        rv = (RecyclerView) findViewById(R.id.rv_panier);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //JSONObject jsonPanier = new JSONObject(BiersAdapter.getPanier());

        //rv.setAdapter(new BiersAdapter(jsonPanier.getJSONArray().toString());
        rv.setAdapter(new BiersAdapter( BiersAdapter.getPanier().toString()));

        //ViewGroup layout = (ViewGroup) findViewById(R.id.swapactivity);
        //layout.addView(textView);
    }
    /*public JSONArray getJSONPan(){
        JSONObject object = new JSONObject();



             JSONArray jArray = new JSONArray();


            jArray.put(BiersAdapter.getPanier().toString());

            return jArray;//construction du tableau

    }*/


}
