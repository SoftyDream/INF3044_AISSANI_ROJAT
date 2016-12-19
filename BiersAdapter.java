package com.example.menuiserie.premierprojet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder>
 {

    private View view;

    public View getView()
    {
        return view;
    }

    private static int m = 0;

     public static  int getM(){
         return m;
     }
    private JSONArray biers;
     private String text;
    private JSONObject o;

     private static HashMap<String,Integer> quantity;
     private static HashMap<String,Integer> panier;



     public static HashMap<String,Integer> getPanier(){
         return panier;
     }

     public static void setPanier(HashMap<String,Integer> pnr){
         panier = pnr;
     }




     ArrayList<Integer> listprice;

    public BiersAdapter(JSONArray js) {
     this.biers=js;
     quantity = new HashMap<String,Integer>();
     panier = new HashMap<String,Integer>();
     listprice = new ArrayList<>();
     listprice.add(0,3);
     listprice.add(1,4);
     listprice.add(2,5);
     listprice.add(3,6);

    }

     public BiersAdapter(String js) {
        this.text = js;
     }

    public void setNewBiere( JSONArray biers)
    {
        this.biers=biers;
        notifyDataSetChanged();
    }

    public JSONArray getBiers()
    {
        return biers;
    }

    @Override
    public BiersAdapter.BierHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_bier_element,parent,false);
        BierHolder bh = new BierHolder(view);
        return bh;
    }

    @Override
    public void onBindViewHolder(BiersAdapter.BierHolder holder, int position)
    {
        try
        {
            o = (JSONObject) biers.get(position);
            String name = o.getString("name");
            holder.name.setText(name);

            //holder.name.setText(text);
            Random r = new Random();
            int randomValue = r.nextInt(4-0) + 4; //valeur aléatoire du prix d'une bière
            holder.spinbier.setTag(name);
            holder.btnajt.setTag(name);
            holder.price.setText(String.valueOf(randomValue)+ "€/pièce");

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return biers.length();
    }


     public class BierHolder extends RecyclerView.ViewHolder
     {

         public TextView name;
         public TextView price;
         public Button btnajt;
         public Spinner spinbier;
         Button buttonpanier ;
        // public int j = 0;


         public BierHolder(View itemView)
         {
             super(itemView);
             name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);
             btnajt = (Button) itemView.findViewById(R.id.btnajouter);
             price = (TextView) itemView.findViewById(R.id.price);
             buttonpanier = (Button) itemView.findViewById(R.id.panierbutton);


             //btnajt.setEnabled(false);
             spinbier = (Spinner) itemView.findViewById(R.id.bier_spinner);


             List<String> spinnerArray =  new ArrayList<String>();
             for(int i = 1; i<11;i++)
             {
                 spinnerArray.add(String.valueOf(i));
             }
             ArrayAdapter<String> arradat = new ArrayAdapter<String>(name.getContext(), android.R.layout.simple_spinner_item,spinnerArray);

             spinbier.setAdapter(arradat);

             spinbier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
             {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                 {

                     Spinner sp = (Spinner) parent;
                     String biername = (String) sp.getTag();
                     quantity.put(biername,position+1);

                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {

                 }
             });


             btnajt.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick(View v)
                 {

                     String biername = (String) v.getTag();
                     int qty = quantity.get(biername);
                     if(panier.containsKey(biername))
                     {
                         int qt = panier.get(biername);
                         panier.put(biername,qty  + qt);
                     }
                     else
                     {
                         panier.put(biername,qty);
                     }
                     String message= String.valueOf(qty) + "x bières " + biername + " ajoutée" ;

                     Toast.makeText(v.getContext(),message,Toast.LENGTH_SHORT).show();

                 }
             });

            /* buttonpanier.setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick(View v)
                 {

                       MainActivity act = new MainActivity();
                        act.changeActivity(v);

                 }
             });
*/
         }
     }

}
