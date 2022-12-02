package com.afouquet.imposeTaSoiree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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

            Log.d("verif",((TextView) findViewById(R.id.passwordUnInscrire)).getText().toString());
            if(((TextView) findViewById(R.id.passwordUnInscrire)).getText().toString().equals(((TextView) findViewById(R.id.passwordDeuxInscrire)).getText().toString())){
                try {
                    Membre m = new Membre(((TextView)findViewById(R.id.nomInscrire)).getText().toString()
                            ,((TextView) findViewById(R.id.prenomInscrire)).getText().toString()
                            ,formatter.parse(((TextView)findViewById(R.id.ddnInscrire)).getText().toString())
                            ,((TextView) findViewById(R.id.mailInscrire)).getText().toString()
                            ,((TextView) findViewById(R.id.loginInscrire)).getText().toString()
                            ,((TextView) findViewById(R.id.passwordUnInscrire)).getText().toString());
                    Log.d("Recup membre",m.toString());
                    DaoMembre.getInstance().addMembre(m, new DelegateAsyncTask() {
                        @Override
                        public void whenWSConnexionIsTerminated(Object result) {
                            if((boolean)result){
                                Log.d("valide inscri","oui");
                                Toast.makeText(Incription.this, "compte créé, activez le par mail",Toast.LENGTH_LONG).show();
                                setResult(2,new Intent().putExtra("login",((TextView) findViewById(R.id.loginInscrire)).getText().toString())
                                        .putExtra("mdp",((TextView) findViewById(R.id.passwordUnInscrire)).getText().toString()));
                                finish();
                            }else{
                                Toast.makeText(Incription.this, "erreur",Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(this,"password 1 et 2 inégaux. Rentrez le même dans les 2 champs.",Toast.LENGTH_LONG).show();
            }

        });

    }
}