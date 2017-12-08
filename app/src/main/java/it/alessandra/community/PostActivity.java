package it.alessandra.community;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //ricevo l'intent da groupactivity e per quel gruppo mi prendo tutti i post e in una recycler view metto tutti i post relativi
        //con autore titolo e data
        Intent i = getIntent();
        String nomeGruppo = i.getStringExtra("NOMEGRUPPO");
    }
}
