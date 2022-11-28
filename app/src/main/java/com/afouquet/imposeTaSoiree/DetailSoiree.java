package com.afouquet.imposeTaSoiree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afouquet.imposeTaSoiree.beans.Membre;
import com.afouquet.imposeTaSoiree.beans.Soiree;
import com.afouquet.imposeTaSoiree.daos.DaoMembre;
import com.afouquet.imposeTaSoiree.daos.DelegateAsyncTask;

import java.text.SimpleDateFormat;
import java.util.List;

public class DetailSoiree extends AppCompatActivity {

    SimpleDateFormat sdff = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat shff = new SimpleDateFormat("HH:mm");

    private ArrayAdapter<Membre> arrayAdapterMembre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_soiree);

        Soiree s = (Soiree) this.getIntent().getSerializableExtra("laSoiree");

        arrayAdapterMembre = new ArrayAdapter<Membre>(this, android.R.layout.simple_list_item_1);
        ((ListView)findViewById(R.id.lvMembresSoireeDetail)).setAdapter(arrayAdapterMembre);

        //AFFICHAGE SOIREE
        ((TextView) findViewById(R.id.tvTitreDetailSoiree)).setText(s.getLibCourt());
        ((TextView) findViewById(R.id.tvDescriDetail)).setText(s.getDescriptif());
        ((TextView) findViewById(R.id.tvDateDetail)).setText("Date: " + sdff.format(s.getDateDebut()));
        ((TextView) findViewById(R.id.tvHeureDetail)).setText("Heure: " + shff.format(s.getHeureDebut()));
        ((TextView) findViewById(R.id.tvOrganisateurDetail)).setText("Soirée déposée par: " + s.getOrganisateur());

        //LISTVIEW DE PARTICIPANTS
        DaoMembre.getInstance().getParticipantsByIdSoiree(s.getId(), new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                for (Membre m:((List<Membre>) result)){
                }
                if (!((List<Membre>) result).isEmpty()) {
                    arrayAdapterMembre.addAll(((List<Membre>) result));

                }else{
                    Toast.makeText(DetailSoiree.this, "liste vide", Toast.LENGTH_LONG).show();
                }
            }
        });
        arrayAdapterMembre.notifyDataSetChanged();


        //BOUTON RETOUR
        findViewById(R.id.buttonRetourDetail).setOnClickListener((View view) ->{
                    finish();
                });
    }
}