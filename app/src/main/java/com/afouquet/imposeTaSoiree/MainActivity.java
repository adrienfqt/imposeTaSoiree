package com.afouquet.imposeTaSoiree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afouquet.imposeTaSoiree.daos.DaoMembre;
import com.afouquet.imposeTaSoiree.daos.DelegateAsyncTask;
import com.afouquet.imposeTaSoiree.net.WSConnexionHTTPS;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.buttonInscrire)).setOnClickListener((View view) -> {
            Intent intent = new Intent(this, Incription.class);
            startActivityForResult(intent,2);
        });

        ((Button) findViewById(R.id.buttonIdentifierAccueil)).setOnClickListener((View view) -> {

            String login = ((TextView) findViewById(R.id.loginAccueil)).getText().toString();
            String passwd = ((TextView) findViewById(R.id.passwordAccueil)).getText().toString();

            DaoMembre.getInstance().connexionMembre(login, passwd, new DelegateAsyncTask() {
                @Override
                public void whenWSConnexionIsTerminated(Object result) {
                    if ((boolean) result ) {
                        Intent leIntent = new Intent(MainActivity.this, ListeSoirees.class);
                        startActivityForResult(leIntent,1);
                    }else{
                        Toast.makeText(MainActivity.this,"Identification échouée ",Toast.LENGTH_LONG).show();
                    }

                }
            });

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            ((TextView) findViewById(R.id.loginAccueil)).setText("");
            ((TextView) findViewById(R.id.passwordAccueil)).setText("");
        }else if(requestCode==resultCode){
            ((TextView) findViewById(R.id.loginAccueil)).setText(data.getStringExtra("login"));
            ((TextView) findViewById(R.id.passwordAccueil)).setText(data.getStringExtra("mdp"));
        }
    }
}