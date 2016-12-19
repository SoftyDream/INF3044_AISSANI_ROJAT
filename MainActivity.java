package com.example.menuiserie.premierprojet;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    public static final String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    //public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
/***********ajouté pour activity*******/
    private JSONArray js;

    private RecyclerView rv;

    public RecyclerView getRv(){
        return rv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetBiersServices.startActionBears(this);

        IntentFilter intentFilter = new  IntentFilter(BIERS_UPDATE);
        //Toast.makeText(MainActivity.this,getString(R.string.app_name),Toast.LENGTH_LONG).show();

        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);
        Toast.makeText(MainActivity.this,getString(R.string.finish),Toast.LENGTH_LONG).show();
        setContentView(R.layout.activitymain);

        rv = (RecyclerView) findViewById(R.id.rv_biere);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

            rv.setAdapter(new BiersAdapter(getBiersFromFile()));
       // btnpanier(rv);

        /**************************************/
       // final Button switchButton = (Button) findViewById()
        /**************************************/
    }

    /*
    *
     *ajouté pour l'activity
    *
    */
    /*public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Panier.class);
        EditText editText = (EditText) findViewById(R.id.changeActivity);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }*/


    public JSONArray getBiersFromFile(){

        try{
            InputStream is = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));//construction du tableau


        }catch(IOException e) {
            e.printStackTrace();
            return new JSONArray();

        }catch(JSONException e ){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public void btnpanier(View view){
        Button buttonpanier = (Button) view.findViewById(R.id.panierbutton);
         buttonpanier.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

               changeActivity(v);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }



    public void changeActivity(View view){ //changement de vue pour le panier

        Intent intent = new Intent(this, Panier.class);
        intent.putExtra(BiersAdapter.getPanier().toString(),true);

        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.changeActivity :
                //Toast.makeText(MainActivity.this,getString(R.string.String),Toast.LENGTH_LONG).show();

                /*
                Intent intent = new Intent(this, Panier.class);
                EditText editText = (EditText) findViewById(R.id.edit_message);
                String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);*/
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this).setSmallIcon(android.R.drawable.sym_def_app_icon).setContentTitle("BierShop Panier")
                                .setContentText("Votre panier de bière : " + BiersAdapter.getPanier().toString());
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0, mBuilder.build());
                break;

            case R.id.quitApp :


               System.exit(0);
                break;

           /* case R.id.cc :

                GetBiersServices.startActionBears(getApplicationContext());

                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    public class BierUpdate extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Toast.makeText(context,context.getString(R.string.dl),Toast.LENGTH_LONG).show();
            ((BiersAdapter) rv.getAdapter()).setNewBiere(getBiersFromFile());
        }
    }
}
