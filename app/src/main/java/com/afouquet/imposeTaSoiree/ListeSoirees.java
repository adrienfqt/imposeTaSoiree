package com.afouquet.imposeTaSoiree;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.afouquet.imposeTaSoiree.beans.Soiree;
import com.afouquet.imposeTaSoiree.daos.DaoMembre;
import com.afouquet.imposeTaSoiree.daos.DaoSoiree;
import com.afouquet.imposeTaSoiree.daos.DelegateAsyncTask;

public class ListeSoirees extends AppCompatActivity {

    private ArrayAdapter<Soiree> arrayAdapterSoiree;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_soirees);
        arrayAdapterSoiree = new ArrayAdapter<Soiree>(this, android.R.layout.simple_list_item_1, DaoSoiree.getInstance().getLocalSoirees());
        ((ListView)findViewById(R.id.listSoirees)).setAdapter(arrayAdapterSoiree);
        DaoSoiree.getInstance().getSoiree(new DelegateAsyncTask() {
            @Override
            public void whenWSConnexionIsTerminated(Object result) {
                if((boolean)result!= true){
                    arrayAdapterSoiree.notifyDataSetChanged();
                }else{
                    Toast.makeText(ListeSoirees.this,"liste vide",Toast.LENGTH_LONG).show();
                }

            }
        });
        findViewById(R.id.buttonDécoListSoiree).setOnClickListener((View view)->{
            DaoMembre.getInstance().deconnectMembre( new DelegateAsyncTask() {
                @Override
                public void whenWSConnexionIsTerminated(Object result) {

                    if((boolean) result == true){
                        Toast.makeText(ListeSoirees.this,"déconnexion effectuée. A bientôt",Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(ListeSoirees.this,"déconnexion échouée",Toast.LENGTH_LONG).show();
                    }
                }
            });

        });


        findViewById(R.id.buttonSupprCompteSoirees).setOnClickListener((View views)->{
            DaoMembre.getInstance().supprMembre(new DelegateAsyncTask() {
                @Override
                public void whenWSConnexionIsTerminated(Object result) {
                    if((boolean) result == true){
                        Toast.makeText(ListeSoirees.this,"Supression du compte en cours...",Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(ListeSoirees.this,"Supression du compte impossible...",Toast.LENGTH_LONG).show();
                    }
                }
            });

        });

        ((ListView)findViewById(R.id.listSoirees)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Soiree s = (Soiree)arrayAdapterSoiree.getItem(position);
                Intent leIntent = new Intent(ListeSoirees.this,DetailSoiree.class);
                leIntent.putExtra("laSoiree",s);
                startActivity(leIntent);
            }
        });
    }
}