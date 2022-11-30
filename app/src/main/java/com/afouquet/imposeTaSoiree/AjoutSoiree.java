package com.afouquet.imposeTaSoiree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afouquet.imposeTaSoiree.beans.Soiree;
import com.afouquet.imposeTaSoiree.daos.DaoMembre;
import com.afouquet.imposeTaSoiree.daos.DaoSoiree;
import com.afouquet.imposeTaSoiree.daos.DelegateAsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AjoutSoiree extends AppCompatActivity {

    SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    SimpleDateFormat sdfff = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
    SimpleDateFormat shff = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_soiree);

        findViewById(R.id.buttonValiderAddSoiree).setOnClickListener((View view) -> {

            String lib = ((TextView) findViewById(R.id.editLibelleAddSoiree)).getText().toString();
            String desc = ((TextView) findViewById(R.id.editDescriptifAddSoiree)).getText().toString();
            String dateDebu = ((TextView) findViewById(R.id.editDateAddSoiree)).getText().toString();
            String heureDebu = ((TextView) findViewById(R.id.editHeureAddSoiree)).getText().toString();
            String adresse = ((TextView) findViewById(R.id.editAdresseAddSoiree)).getText().toString();
            Log.d("soiree", "    STRING    " + dateDebu);
            DaoSoiree.getInstance().addSoiree(lib, desc, dateDebu, heureDebu, adresse, new DelegateAsyncTask() {
                @Override
                public void whenWSConnexionIsTerminated(Object result) {
                    if ((boolean) result) {
                        Toast.makeText(AjoutSoiree.this, "soirée bien crée", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(AjoutSoiree.this, "Erreur de création de soirée", Toast.LENGTH_LONG).show();
                    }

                }
            });

        });
        findViewById(R.id.buttonRetourAddSoiree).setOnClickListener((View views) -> {
            finish();
        });
    }
}