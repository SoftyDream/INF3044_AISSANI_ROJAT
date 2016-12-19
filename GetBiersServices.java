package com.example.menuiserie.premierprojet;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;


import static android.content.ContentValues.TAG;



public class GetBiersServices extends IntentService
{

    private static final String ACTION_GET_ALL_BIERS = "com.example.menuiserie.premierprojet.action.GET_ALL_BEARS";


    public GetBiersServices()
    {
        super("GetBiersServices");
    }

    public static void startActionBears(Context context)
    {
        Toast.makeText(context,context.getString(R.string.dl),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, GetBiersServices.class);
        context.startService(intent);
        //Toast.makeText(context,context.getString(R.string.finish),Toast.LENGTH_LONG).show();
    }

    /*public static void toasterChoice(Context context)
    {
        String message = "ahah";
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, GetBiersServices.class);
        context.startService(intent);
        //Toast.makeText(context,context.getString(R.string.finish),Toast.LENGTH_LONG).show();
    }*/

    /*public static Context getContext(Context context){
        context.getApplicationContext();
        return context;
    }*/


    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            handleActionBears();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(intent));
        }
    }

    private void copyInputStreamToFile(InputStream in, File file)
    {
        try
        {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0)
            {
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void handleActionBears()
    {
        Log.d(TAG, "Thread service name:" + Thread.currentThread().getName());
        URL url = null;
        try
        {
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode())
            {
                //en broadcast receiver + o n toast avant getservices du main
                //Toast.makeText(MainActivity.this,getString(R.string.dl), Toast.LENGTH_LONG).show();
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "bieres.json"));
                Log.d(TAG, "Bieres json downloaded !");
               // Toast.makeText(Main2Activity.class,getString(R.string.finish), Toast.LENGTH_LONG).show();
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException f)
        {
            f.printStackTrace();
        }
    }



}


