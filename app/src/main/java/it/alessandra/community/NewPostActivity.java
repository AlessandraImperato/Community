package it.alessandra.community;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class NewPostActivity extends AppCompatActivity implements TaskDelegate{

    private EditText editTitolo;
    private EditText editBody;
    private Button bInsert;
    private TextView textUser;
    private SharedPreferences preferences;
    private Community community;
    private String username;
    private String nomeGruppo;
    private Post newPost;
    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private static FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        delegate = this;

        editTitolo = findViewById(R.id.editTitolo);
        editBody = findViewById(R.id.editBody);
        bInsert = findViewById(R.id.bInsert);
        textUser = findViewById(R.id.textUser);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("USERNAME","user");
        nomeGruppo = preferences.getString("NomeGruppo","");
        textUser.setText(username);

        database = FirebaseDatabase.getInstance();
        String url = "https://dbcommunity-cf2e7.firebaseio.com/Communities/" + nomeGruppo + "/Post/";
        String url2 = "https://dbcommunity-cf2e7.firebaseio.com/Communities/" + nomeGruppo +"/LastDataChange";
        databaseReference = database.getReferenceFromUrl(url);
        databaseReferenceDate = database.getReferenceFromUrl(url2);

        bInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitolo.getText().toString();
                String body = editBody.getText().toString();

                if(title.equals("") || body.equals("")){
                    Toast.makeText(getApplicationContext(),"Inserire tutti i campi",Toast.LENGTH_LONG).show();
                }
                else {
                    newPost = new Post(username,title,new Date(),body);
                    String url = "Communities/" + nomeGruppo + "/Post.json";
                    restCallAddPost(url);
                }

            }
        });

    }

    public void restCallAddPost(String url){
        dialog = new ProgressDialog(NewPostActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();
        FirebaseRestClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    int index = JsonParse.key(text);
                    newPost.setId(generateKey(index));
                    String dataCreazione = DataSet.formatToString(newPost.getDataCreazione());
                    databaseReference.child(generateKey(index)).child("Titolo").setValue(newPost.getTitolo());
                    databaseReference.child(generateKey(index)).child("Autore").setValue(username);
                    databaseReference.child(generateKey(index)).child("Data").setValue(dataCreazione);
                    databaseReference.child(generateKey(index)).child("Body").setValue(newPost.getBody());
                    databaseReferenceDate.setValue(dataCreazione);
                    delegate.TaskCompletionResult("Post aggiunto");
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public String generateKey(int index){
        String keyG = "";
        keyG = "post " + index;
        return keyG;
    }

    @Override
    public void TaskCompletionResult(String result) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
}
