package it.alessandra.community;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PostActivity extends AppCompatActivity implements TaskDelegate{

    private RecyclerView recyclerPost;
    private TextView textUser;
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

        textView = (TextView) findViewById(R.id.textUser);
        textView.setText(username);

        recyclerPost = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerPost.setLayoutManager(linearLayoutManager);

        delegate = this;
        community = (Community) InternalStorage.readObject(getApplicationContext(),"GRUPPI");

        gruppo = community.getGroupByName(nomeGruppo);
        //gruppo.setNome(nomeGruppo);
        listapost = gruppo.getListaPost();

        if(listapost.size() == 0){
            //rest
            String url = "Communities/" + nomeGruppo + ".json";
            restCallPost(url);
        }else{
            //stampiamo i post
            postAdapter = new PostAdapter(listapost,getApplication());
            recyclerPost.setAdapter(postAdapter);
        }
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
                        gruppo.setNome(nomeGruppo);
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

    @Override
    public void TaskCompletionResult(String result) {
        dialog.dismiss();
        dialog.cancel();
        postAdapter = new PostAdapter(listapost,getApplication());
        recyclerPost.setAdapter(postAdapter);
        InternalStorage.writeObject(getApplicationContext(),"GRUPPI",community);
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
}
