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
import java.util.ArrayList;
import java.util.List;

public class DetailSoiree extends AppCompatActivity {

    SimpleDateFormat sdff = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat shff = new SimpleDateFormat("HH:mm");

    private ArrayAdapter<Membre> arrayAdapterMembre;
    private List<Membre>lesMembresDeLaSoiree= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_soiree);

        Soiree s = (Soiree) this.getIntent().getSerializableExtra("laSoiree");

        arrayAdapterMembre = new ArrayAdapter<Membre>(this, android.R.layout.simple_list_item_1);
        ((ListView) findViewById(R.id.lvMembresSoireeDetail)).setAdapter(arrayAdapterMembre);

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

                if (!((List<Membre>) result).isEmpty()) {
                    arrayAdapterMembre.addAll(((List<Membre>) result));

                } else {
                    Toast.makeText(DetailSoiree.this, "liste vide", Toast.LENGTH_LONG).show();
                }
            }
        });
        arrayAdapterMembre.notifyDataSetChanged();

        //BOUTON RETOUR
        findViewById(R.id.buttonRetourDetail).setOnClickListener((View view) -> {
            finish();
        });

        //BOUTON VISIBLES OU PAS SELON PARTICIPANT / ORGANISATEUR,ETC...
        Log.d("zobi", DaoMembre.getInstance().getMembreConnected().getLogin());
        if (s.getOrganisateur().equals(DaoMembre.getInstance().getMembreConnected().getLogin())) {//c'est ma soirée
            findViewById(R.id.buttonDesinscrireDetail).setVisibility(View.INVISIBLE);
            findViewById(R.id.buttonInscrireDetail).setVisibility(View.INVISIBLE);
        }

        DaoMembre.getInstance().isInscrit(s.getId(), new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                Log.d("inscrit", String.valueOf((boolean) result));
                if ((boolean) result) {
                    findViewById(R.id.buttonInscrireDetail).setVisibility(View.INVISIBLE);
                    findViewById(R.id.buttonSupprDetail).setVisibility(View.INVISIBLE);

                } else {
                    findViewById(R.id.buttonInscrireDetail).setVisibility(View.VISIBLE);
                    findViewById(R.id.buttonDesinscrireDetail).setVisibility(View.INVISIBLE);
                }
                if (s.getOrganisateur().equals(DaoMembre.getInstance().getMembreConnected().getLogin())) {//c'est ma soirée

                    findViewById(R.id.buttonSupprDetail).setVisibility(View.VISIBLE);
                }
            }
        });

        //Mise en place de la fonctionnalité des boutons

        //Désinscription
        findViewById(R.id.buttonDesinscrireDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DaoMembre.getInstance().desinscrireSoiree(s.getId(), new DelegateAsyncTask() {
                    @Override
                    public void whenWSConnexionIsTerminated(Object result) {
                        if ((boolean) result) {
                            findViewById(R.id.buttonDesinscrireDetail).setVisibility(View.INVISIBLE);
                            findViewById(R.id.buttonInscrireDetail).setVisibility(View.VISIBLE);
                            arrayAdapterMembre.notifyDataSetChanged();
                            Toast.makeText(DetailSoiree.this, "désinscription effectuée", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DetailSoiree.this, "Erreur désinscription annulée ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //Inscription
        findViewById(R.id.buttonInscrireDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoMembre.getInstance().inscrireSoiree(s.getId(), new DelegateAsyncTask() {
                    @Override
                    public void whenWSConnexionIsTerminated(Object result) {
                        if ((boolean) result) {
                            findViewById(R.id.buttonInscrireDetail).setVisibility(View.INVISIBLE);
                            findViewById(R.id.buttonDesinscrireDetail).setVisibility(View.VISIBLE);
                            arrayAdapterMembre.notifyDataSetChanged();
                            Toast.makeText(DetailSoiree.this, "Inscription effectuée", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DetailSoiree.this, "Erreur Inscription annulée ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //Suppression soirée
        findViewById(R.id.buttonSupprDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoMembre.getInstance().delSoiree(s.getId(), new DelegateAsyncTask() {
                    @Override
                    public void whenWSConnexionIsTerminated(Object result) {
                        if ((boolean) result) {
                            setResult(RESULT_OK);
                            finish();
                            Toast.makeText(DetailSoiree.this, "Suppression soirée effectuée", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(DetailSoiree.this, "Erreur Suppression soirée annulée ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
