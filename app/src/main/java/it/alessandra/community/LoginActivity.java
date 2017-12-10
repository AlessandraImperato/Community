package it.alessandra.community;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements TaskDelegate{

    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private SharedPreferences preferences;
    private EditText user;
    private EditText pass;
    private Button bLogin;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.editUser);
        pass = (EditText) findViewById(R.id.editPass);
        bLogin = (Button) findViewById(R.id.bLogin);

        delegate = this;

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user.getText().toString();
                password = pass.getText().toString();
                String url ="Users/"+username+"/password.json";

                restCall(url);
            }
        });

    }

    public void restCall(String url){
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();
        FirebaseRestClient.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    if(text.equals(password)){
                        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("USERNAME", username);
                        editor.commit();
                        delegate.TaskCompletionResult("Accesso effettuato!");
                        Intent i = new Intent(getApplicationContext(),GroupActivity.class);
                        startActivity(i);
                    }
                    else {
                        delegate.TaskCompletionResult("Accesso negato!");
                    }
                    }
                }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.TaskCompletionResult("Accesso negato!");
            }
        });
    }

    @Override
    public void TaskCompletionResult(String result) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
}
