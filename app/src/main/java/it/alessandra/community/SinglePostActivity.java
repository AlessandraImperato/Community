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

public class SinglePostActivity extends AppCompatActivity implements TaskDelegate {

    private Community community;
    private TextView titolo;
    private TextView autore;
    private TextView body;
    private TextView data;
    private TextView textUser;
    private Gruppo gruppo;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences preferences;
    private String username;
    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private List<Post> listaPost;
    private Post post;
   // private SharedPreferences preferencesNomeGruppo;
    private String nomAutore;
    private String title;
    private Date dataCreazione;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        delegate = this;

        autore = findViewById(R.id.textautore);
        titolo = findViewById(R.id.texttitolo);
        body = findViewById(R.id.textbody);
        data = findViewById(R.id.textdata);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("USERNAME","user");

        textUser = findViewById(R.id.user);
        textUser.setText(username);

        Intent i = getIntent();
        String idPost = i.getStringExtra("IdPost");
        //preferencesNomeGruppo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String nomeGruppo = preferences.getString("NomeGruppo", "");

        community = (Community) InternalStorage.readObject(getApplicationContext(),"GRUPPI");
        gruppo = community.getGroupByName(nomeGruppo);
        post = gruppo.getPostById(idPost); // così ottengo il post su cui ho cliccato

        nomAutore = post.getAutore();
        title = post.getTitolo();
        dataCreazione = post.getDataCreazione();

        post = (Post) InternalStorage.readObject(getApplicationContext(),"POST");

        if(post == null){
            String url = "Communities/" + nomeGruppo + "/Post/" + idPost + ".json";
            restCallSinglePost(url);
        }else{
            autore.setText(nomAutore);
            titolo.setText(title);
            data.setText(formatDate(dataCreazione));
            body.setText(post.getBody());
        }

    }

    public void restCallSinglePost(String url){
        dialog = new ProgressDialog(SinglePostActivity.this);
        dialog.setMessage("Caricamento Post");
        dialog.show();

        FirebaseRestClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    try {
                         post = JsonParse.getPost(text);
                        delegate.TaskCompletionResult("Post caricato");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.TaskCompletionResult("");
            }
        });
    }
    @Override
    public void TaskCompletionResult(String result) {
        dialog.dismiss();
        dialog.cancel();
        autore.setText(nomAutore);
        titolo.setText(title);
        data.setText(formatDate(dataCreazione));
        body.setText(post.getBody());
        InternalStorage.writeObject(getApplicationContext(),"GRUPPI",community);
        InternalStorage.writeObject(getApplicationContext(),"POST",post);
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
    public String formatDate(Date date){
        Format format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        return format.format(date);
    }
}
