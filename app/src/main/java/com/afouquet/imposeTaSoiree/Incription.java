package com.afouquet.imposeTaSoiree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afouquet.imposeTaSoiree.beans.Membre;
import com.afouquet.imposeTaSoiree.daos.DaoMembre;
import com.afouquet.imposeTaSoiree.daos.DelegateAsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Incription extends AppCompatActivity {
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incription);

        findViewById(R.id.buttonAnnulerInscrire).setOnClickListener((View view)->{
            finish();
        });

        findViewById(R.id.buttonValiderInscrire).setOnClickListener((View view)->{
            Toast.makeText(this,"début de l'inscription",Toast.LENGTH_LONG).show();

            if(findViewById(R.id.passwordUnInscrire).toString()==findViewById(R.id.passwordDeuxInscrire).toString() ){
                try {
                    Membre m = new Membre((findViewById(R.id.nomInscrire).toString())
                            ,findViewById(R.id.prenomInscrire).toString()
                            ,formatter.parse(findViewById(R.id.ddnInscrire).toString())
                            ,findViewById(R.id.mailInscrire).toString()
                            ,findViewById(R.id.loginInscrire).toString()
                            ,findViewById(R.id.passwordUnInscrire).toString());
                    Log.d("Recup membre",m.toString());
                    DaoMembre.getInstance().addMembre(m, new DelegateAsyncTask() {
                        @Override
                        public void whenWSConnexionIsTerminated(Object result) {

                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(this,"password 1 et 2 inégaux",Toast.LENGTH_LONG).show();
            }

        });

    }
}