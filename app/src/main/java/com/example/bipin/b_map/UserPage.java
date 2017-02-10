package com.example.bipin.b_map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by bipin on 2/10/17.
 */

public class UserPage extends AppCompatActivity {

    TextView name_key, name_value;
    TextView email_key, email_value;
    TextView address_key, address_value;
    TextView bank_key, bank_value;
    TextView account_no_key, account_no_value;
    TextView nationality_key, nationality_value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        name_key = (TextView) findViewById(R.id.name_key);
        name_value = (TextView) findViewById(R.id.name_value);
        email_key = (TextView) findViewById(R.id.email_key);
        email_value = (TextView) findViewById(R.id.email_value);
        address_key = (TextView) findViewById(R.id.address_key);
        address_value = (TextView) findViewById(R.id.address_value);
        bank_key = (TextView) findViewById(R.id.bank_key);
        bank_value = (TextView) findViewById(R.id.bank_value);
        account_no_key = (TextView) findViewById(R.id.account_no_key);
        account_no_value = (TextView) findViewById(R.id.account_no_value);
        nationality_key = (TextView) findViewById(R.id.nationality_key);
        nationality_value = (TextView) findViewById(R.id.nationality_value);

        Bundle extra = getIntent().getExtras();
        int id;
        String token;

        if(extra != null){
            id = extra.getInt("ID");
            token = extra.getString("TOKEN");
            //Toast.makeText(getApplicationContext(), id + "token : " + token, Toast.LENGTH_LONG).show();
            getUserInformation(token, id);
        }
    }

    private void getUserInformation(String token, int id){

        Ion.with(getApplicationContext())
                .load("https://serene-badlands-22797.herokuapp.com/api/v1/users/" + Integer.toString(id))
                .setHeader("Authorization", token)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null){
                            JsonObject user = result.get("data").getAsJsonObject();
                            JsonObject personal_detail = user.get("attributes").getAsJsonObject();
                            System.out.println(personal_detail.toString());
                            name_key.setText("Name");
                            name_value.setText(personal_detail.get("name").getAsString());
                            email_key.setText("Email");
                            email_value.setText(personal_detail.get("email").getAsString());
                            address_key.setText("Address");
                            address_value.setText(personal_detail.get("address").getAsString());
                            bank_key.setText("Bank Name");
                            bank_value.setText(personal_detail.get("bank-name").getAsString());
                            account_no_key.setText("Account no");
                            account_no_value.setText(personal_detail.get("account-number").getAsString());
                            nationality_key.setText("Nationality");
                            nationality_value.setText(personal_detail.get("nationality").getAsString());
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Please check yout internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
