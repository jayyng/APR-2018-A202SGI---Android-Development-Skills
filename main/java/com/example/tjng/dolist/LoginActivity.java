package com.example.tjng.dolist;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    MySQLiteHelper mMySQLiteHelper=new MySQLiteHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText Username=(EditText) findViewById(R.id.etUsername);
        final EditText Password=(EditText) findViewById(R.id.etPassword);
        final Button bLogin=(Button) findViewById(R.id.bLogin);
        final TextView registerLink=(TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //enter register page
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }

        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String username = Username.getText().toString();
                final String password = Password.getText().toString();
                if ((username.equals(""))||(password.equals(""))){
                    //if the fields are not filled in
                    AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("ERROR")
                            .setMessage("Invalid Username/Password")
                            .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //nothing is done
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

                else if (v.getId() == R.id.bLogin) {

                    String pass3 = mMySQLiteHelper.searchPass(username);
                    //call the searchpass function

                    if (password.equals(pass3)) {
                        //if the credentials are the same
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(i);
                    }
                    if (!password.equals(pass3)){
                        //when the password does not match the username
                        Toast temp = Toast.makeText(LoginActivity.this, "Username and Password does not match", Toast.LENGTH_SHORT);
                        temp.show();
                    }
                }
            }

        });


    }
}
