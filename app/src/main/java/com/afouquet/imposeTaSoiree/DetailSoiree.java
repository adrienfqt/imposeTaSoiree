package com.afouquet.imposeTaSoiree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.afouquet.imposeTaSoiree.beans.Membre;
import com.afouquet.imposeTaSoiree.beans.Soiree;

import java.text.SimpleDateFormat;

public class DetailSoiree extends AppCompatActivity {

    SimpleDateFormat sdff = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat shff = new SimpleDateFormat("HH:mm");

    private ArrayAdapter<Membre> arrayAdapterMembre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_soiree);

        //arrayAdapterMembre= new ArrayAdapter<Membre>(this,android.R.layout.simple_list_item_1,)

        //AFFICHAGE SOIREE
        Soiree s = (Soiree)this.getIntent().getSerializableExtra("laSoiree");

        ((TextView)findViewById(R.id.tvTitreDetailSoiree)).setText(s.getLibCourt());
        ((TextView)findViewById(R.id.tvDescriDetail)).setText(s.getDescriptif());
        Log.d("heure et date ",sdff.format(s.getHeureDebut()));
        Log.d("date en clair ", String.valueOf(s.getHeureDebut()));

        ((TextView)findViewById(R.id.tvDateDetail)).setText("Date: "+sdff.format(s.getDateDebut()));
        ((TextView)findViewById(R.id.tvHeureDetail)).setText("Heure: "+shff.format(s.getHeureDebut()));
        ((TextView)findViewById(R.id.tvOrganisateurDetail)).setText("Soirée déposée par: "+s.getOrganisateur());



        //BOUTON RETOUR
        findViewById(R.id.buttonRetourDetail).setOnClickListener((View view)->{
           finish();
        });


        //LISTVIEW DE PARTICIPANTS






    }
}