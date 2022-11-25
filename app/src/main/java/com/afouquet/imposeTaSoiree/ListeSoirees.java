package com.afouquet.imposeTaSoiree;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afouquet.imposeTaSoiree.beans.Soiree;
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

    }
}