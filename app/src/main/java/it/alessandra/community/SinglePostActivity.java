package it.alessandra.community;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SinglePostActivity extends AppCompatActivity  {

    private Community community;
    private TextView titolo;
    private TextView autore;
    private TextView body;
    private TextView data;
    private TextView textUser;
    private Gruppo gruppo;
    private SharedPreferences preferences;
    private String username;
    private List<Post> listaPost;
    private Post post;
    private String nomAutore;
    private String title;
    private Date dataCreazione;
    private String idPost;
    private String bodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        autore = findViewById(R.id.textautore);
        titolo = findViewById(R.id.texttitolo);
        body = findViewById(R.id.textbody);
        data = findViewById(R.id.textdata);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("USERNAME","user");
        textUser = findViewById(R.id.user);
        textUser.setText(username);

        Intent i = getIntent();
        idPost = i.getStringExtra("IdPost");
        String nomeGruppo = preferences.getString("NomeGruppo", "");

        community = (Community) InternalStorage.readObject(getApplicationContext(),"GRUPPI");
        gruppo = community.getGroupByName(nomeGruppo);
        post = gruppo.getPostById(idPost); // così ottengo il post su cui ho cliccato

        nomAutore = post.getAutore();
        title = post.getTitolo();
        dataCreazione = post.getDataCreazione();
        bodyText = post.getBody();

        autore.setText(nomAutore);
        titolo.setText(title);
        data.setText(formatDate(dataCreazione));
        body.setText(bodyText);
    }

    public String formatDate(Date date){
        Format format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        return format.format(date);
    }
}
