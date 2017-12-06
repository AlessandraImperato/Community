package it.alessandra.community;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private String control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = "";

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        control = preferences.getString("USERNAME","noLog"); //noLog = valore di default

        if(control.equals("noLog")){
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }else{
            Intent i = new Intent(getApplicationContext(),GroupActivity.class);
            startActivity(i);
        }

    }
}
