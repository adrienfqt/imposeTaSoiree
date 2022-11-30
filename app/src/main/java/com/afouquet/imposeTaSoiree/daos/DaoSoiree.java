package com.afouquet.imposeTaSoiree.daos;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.afouquet.imposeTaSoiree.beans.Membre;
import com.afouquet.imposeTaSoiree.beans.Soiree;
import com.afouquet.imposeTaSoiree.net.WSConnexionHTTPS;

public class DaoSoiree {
    public List<Soiree> getLocalSoirees() {
        return soirees;
    }

    private static DaoSoiree instance = null;
    private final List<Soiree> soirees;

    public List<Soiree> getSoirees() {
        return soirees;
    }


    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    SimpleDateFormat formatterHeure = new SimpleDateFormat("HH:mm", Locale.FRANCE);

    private DaoSoiree() {
        soirees = new ArrayList<>();
    }

    public static DaoSoiree getInstance() {
        if (instance == null) {
            instance = new DaoSoiree();
        }
        return instance;
    }

    public void getSoiree(DelegateAsyncTask delegate) {
        String url = "requete=getLesSoirees";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourGetSoiree(s, delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetSoiree(String s, DelegateAsyncTask delegate) {
        soirees.clear();
        Membre leMembre = null;
        try {
            JSONObject jo = new JSONObject(s);
            JSONArray ja = jo.getJSONArray("response");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject soireeJson =ja.getJSONObject(i);
                int id = soireeJson.getInt("id");
                String lib = soireeJson.getString("libelleCourt");
                String desc = soireeJson.getString("descriptif");
                String adresse = soireeJson.getString("adresse");
                Date dateDeb = formatter.parse(soireeJson.getString("dateDebut"));
                Date heureDeb = formatterHeure.parse(soireeJson.getString("heureDebut"));
                Double lat = Double.parseDouble(soireeJson.getString("latitude"));
                Double lng = Double.parseDouble(soireeJson.getString("longitude"));
                String login =soireeJson.getString("login");
                Soiree ss = new Soiree(id,lib,desc,adresse,lat,lng,dateDeb,heureDeb,login);
                soirees.add(ss);
                Log.d("soirée crée",ss.toString());
                delegate.whenWSConnexionIsTerminated(soirees.isEmpty());
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void addSoiree(String lib,String desc,String date,String heure, String adresse,DelegateAsyncTask delegate) {
        String url = "requete=addSoiree&libelleCourt="+lib+"&descriptif="+desc+"&dateDebut="+date
                +"&heureDebut="+heure+"&adresse="+adresse;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetouraddSoiree(s, delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

     private void traiterRetouraddSoiree(String s, DelegateAsyncTask delegate){
         try {
             JSONObject jo = new JSONObject(s);
             delegate.whenWSConnexionIsTerminated(jo.getBoolean("response"));
         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
}
