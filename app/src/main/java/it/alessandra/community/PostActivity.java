package it.alessandra.community;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class PostActivity extends AppCompatActivity implements TaskDelegate{

    private RecyclerView recyclerPost;
    private List<Post> listapost;
    private Gruppo gruppo;
    private LinearLayoutManager linearLayoutManager;
    private SharedPreferences preferences;
    private String username;
    private TextView textView;
    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private PostAdapter postAdapter;
    private Community community;
    private String nomeGruppo;
    //private SharedPreferences preferencesNameGroup;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String urlDataChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //ricevo l'intent da groupactivity e per quel gruppo mi prendo tutti i post e in una recycler view metto tutti i post relativi
        //con autore titolo e data

        Intent i = getIntent();
        nomeGruppo = i.getStringExtra("NOMEGRUPPO");

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("USERNAME","user");

        //preferencesNameGroup = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NomeGruppo",nomeGruppo);
        editor.commit();

        textView = findViewById(R.id.textUser);
        textView.setText(username);

        recyclerPost = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerPost.setLayoutManager(linearLayoutManager);

        delegate = this;
        community = (Community) InternalStorage.readObject(getApplicationContext(),"GRUPPI");
        gruppo = community.getGroupByName(nomeGruppo);
        listapost = gruppo.getListaPost();
        urlDataChange = "Communities/" + nomeGruppo + "/LastDataChange.json";

        // controllo, SEMPRE APPENA APRO I POST, se ci sono state modifiche
        restCallLastChangeDate(urlDataChange);

        mSwipeRefreshLayout = findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restCallLastChangeDate(urlDataChange);
            }
        });

       /* if(listapost.size() == 0){
            //rest
            String url = "Communities/" + nomeGruppo + ".json";
            restCallPost(url);
        }else{
            //stampiamo i post
            postAdapter = new PostAdapter(listapost,getApplication());
            recyclerPost.setAdapter(postAdapter);
        }*/
    }

    public void restCallPost(String url){
        dialog = new ProgressDialog(PostActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();

        FirebaseRestClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    try {
                        listapost = JsonParse.getListPost(text);
                        gruppo.setListaPost(listapost);
                        delegate.TaskCompletionResult("Post caricati");
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

    public void restCallLastChangeDate(String url){
        FirebaseRestClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    String data = text.replace('"',' ');
                    Date lastDataChange = formatToDate(data);

                    if (lastDataChange.after(gruppo.getLastChange())){ // se la data su firebase è maggiore della data del gruppo =>
                        String newUrl = "Communities/" + nomeGruppo + "/Post.json";// => devo aggiornare con una nuova chiamata rest
                        restCallPost(newUrl);
                        gruppo.setLastChange(lastDataChange); //aggiorno la data del gruppo
                        mSwipeRefreshLayout.setRefreshing(false);
                    }else{
                        Toast.makeText(getApplicationContext(),"Lista post aggiornata",Toast.LENGTH_LONG).show();
                        postAdapter = new PostAdapter(listapost,getApplication());
                        recyclerPost.setAdapter(postAdapter);
                        mSwipeRefreshLayout.setRefreshing(false);
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
        postAdapter = new PostAdapter(listapost,getApplication());
        recyclerPost.setAdapter(postAdapter);
        InternalStorage.writeObject(getApplicationContext(),"GRUPPI",community);
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }

    public static Date formatToDate(String dateString){ // trasformo la data da stringa a Date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.ITALY);
        return format.parse(dateString,new ParsePosition(0));
    }

    public void fabAddPost(View view) {
        Intent i = new Intent(getApplicationContext(),NewPostActivity.class);
        startActivity(i);
    }
}
