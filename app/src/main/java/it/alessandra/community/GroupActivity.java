package it.alessandra.community;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GroupActivity extends AppCompatActivity implements TaskDelegate{

    private SharedPreferences preferences;
    private String username;
    private TextView textView;
    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private List<Gruppo> gruppi;
    private RecyclerView recyclerView;
    private ButtonAdapter buttonAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("USERNAME","user"); // mi serve per la rest per i gruppi

        textView = (TextView) findViewById(R.id.textUser);
        textView.setText(username);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        gruppi = new ArrayList<>();
        delegate = this;

        String url = "/" + username + "/Gruppi.json";
        restCallGroup(url);

    }

    public void restCallGroup(String url){
        dialog = new ProgressDialog(GroupActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();
        FirebaseRestClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    try {
                        gruppi = JsonParse.getList(text);
                        delegate.TaskCompletionResult("Gruppi caricati");
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
        buttonAdapter = new ButtonAdapter(gruppi,getApplicationContext());
        recyclerView.setAdapter(buttonAdapter);
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
}
