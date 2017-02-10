package com.example.bipin.b_map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    private EditText email_field;
    private EditText password_field;
    private TextView token;
    private Button log_in_btn;
    public static String token_key;
    public static int Req_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email_field = (EditText) findViewById(R.id.email);
        password_field = (EditText) findViewById(R.id.password);
        token = (TextView) findViewById(R.id.token);
        log_in_btn = (Button) findViewById(R.id.log_in_btn);

        email_field.setText("admin@example.com");
        password_field.setText("password");

        log_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject json = new JsonObject();
                json.addProperty("email", email_field.getText().toString());
                json.addProperty("password", password_field.getText().toString());

                Ion.with(getApplicationContext())
                        .load("https://serene-badlands-22797.herokuapp.com/api/v1/authenticate")
                        .setJsonObjectBody(json)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (result != null) {
                                    if (result.get("auth_token") != null){
                                        String str = result.get("auth_token").getAsString();
                                        int req_id = result.get("req_id").getAsInt();
                                        Req_Id = req_id;
                                        token_key = str;
                                        Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_LONG).show();
                                        start_user_page(str, req_id);
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private void start_user_page(String token_key, int req_id){
        Intent intent = new Intent(getApplicationContext(), UserPage.class);
        intent.putExtra("TOKEN", token_key);
        intent.putExtra("ID", req_id);
        startActivity(intent);
    }
}


