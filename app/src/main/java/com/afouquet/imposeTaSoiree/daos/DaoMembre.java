package com.afouquet.imposeTaSoiree.daos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.afouquet.imposeTaSoiree.beans.Membre;
import com.afouquet.imposeTaSoiree.beans.Soiree;
import com.afouquet.imposeTaSoiree.net.WSConnexionHTTPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaoMembre {

    @SuppressLint("StaticFieldLeak")
    private static DaoMembre instance = null;

    private Membre membreConnected = null;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


    public Membre getMembreConnected() {
        return membreConnected;
    }

    private DaoMembre() {

    }

    public static DaoMembre getInstance() {
        if (instance == null) {
            instance = new DaoMembre();
        }
        return instance;
    }


    public void addMembre(Membre membre, DelegateAsyncTask delegate) {
        String url = "requete=creerCompte" +
                "&login=" + membre.getLogin() +
                "&nom=" + membre.getNom() +
                "&prenom=" + membre.getPrenom() +
                "&ddn=" + membre.getDdn() +
                "&mail=" + membre.getMail() +
                "&password=" + membre.getPassword();
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourAddMembre(s,delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourAddMembre(String s, DelegateAsyncTask delegate) {
        try {
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("response")){

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void connexionMembre(String login, String passwd, DelegateAsyncTask delegate) {
        String url = "requete=connexion" +
                "&login=" + login +
                "&password=" + passwd;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(String s) {
                traiterRetourConnexion(s, delegate, passwd);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourConnexion(String s, DelegateAsyncTask delegate, String pass) {

        boolean result = false;

        JSONObject jo = null;
        try {
            jo = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (jo.getBoolean("success")) {
                JSONObject ja = (JSONObject) jo.get("response");
                Membre m = new Membre((ja.getString("nom")), (ja.getString("prenom")), (formatter.parse(ja.getString("ddn"))), (ja.getString("mail")), (ja.getString("login")), pass);

                membreConnected = m;
                Log.d("user connecté", " " + membreConnected.toString());
            } else {
                Log.d("request failed", "la requête a échouée");
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        delegate.whenWSConnexionIsTerminated(membreConnected != null);

    }

    public void getMembreByLogin(String login,DelegateAsyncTask delegate){
        String url = "requete=getMembreByLogin&login="+login;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS(){
            @Override
            protected void onPostExecute(String s) {
                traiterRetourGetMembreByLogin(s,delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourGetMembreByLogin(String s,DelegateAsyncTask delegate){
        Membre m = null;
        try {
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("success")){
                JSONObject ja = (JSONObject) jo.get("response");
                m = new Membre(ja.getString("nom").toString(), ja.getString("prenom").toString(), (formatter.parse(ja.getString("ddn"))), (ja.getString("mail")), (ja.getString("login")));

            }else{
                Log.d("request failed", "la requête a échouée");
            }
            delegate.whenWSConnexionIsTerminated(m);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }
    public void deconnectMembre(DelegateAsyncTask delegate){
        String url = "requete=deconnexion";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS(){
            @Override
            protected void onPostExecute(String s) {
                traiterRetourdeconnectMembre(s,delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourdeconnectMembre(String s, DelegateAsyncTask delegate){
        try {
            JSONObject jo = new JSONObject(s);
            delegate.whenWSConnexionIsTerminated(jo.getBoolean("success"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void supprMembre(DelegateAsyncTask delegate){
        String url = "requete=supprimerCompte";
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS(){
            @Override
            protected void onPostExecute(String s) {
                traiterRetoursupprMembre(s,delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetoursupprMembre(String s,DelegateAsyncTask delegate){
        try {
            JSONObject jo = new JSONObject(s);
            delegate.whenWSConnexionIsTerminated(jo.getBoolean("response"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getParticipantsByIdSoiree(int id,DelegateAsyncTask delegate){
        String url = "requete=getLesParticipants&soiree="+id;
        WSConnexionHTTPS wsConnexionHTTPS = new WSConnexionHTTPS(){
            @Override
            protected void onPostExecute(String s) {
                traiterRetourgetParticipantsByIdSoiree(s,delegate);
            }
        };
        wsConnexionHTTPS.execute(url);
    }

    private void traiterRetourgetParticipantsByIdSoiree(String s,DelegateAsyncTask delegate){
        List<Membre> lesMembres = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(s);
            if(jo.getBoolean("success")){
                JSONArray ja = jo.getJSONArray("response");
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject soireeJson =ja.getJSONObject(i);
                    Membre m = new Membre((soireeJson.getString("nom")), (soireeJson.getString("prenom")), (formatter.parse(soireeJson.getString("ddn"))), (soireeJson.getString("mail")), (soireeJson.getString("login")));
                    lesMembres.add(m);

                }
                delegate.whenWSConnexionIsTerminated(lesMembres);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

}
