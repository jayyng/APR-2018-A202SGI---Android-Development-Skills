package com.example.tjng.dolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    //Registration

    MySQLiteHelper mMySQLiteHelper=new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText Name=(EditText) findViewById(R.id.etName);
        final EditText Username=(EditText) findViewById(R.id.etUsername);
        final EditText Password=(EditText) findViewById(R.id.etPassword);
        final EditText Password2=(EditText) findViewById(R.id.etPassword2);

        final Button bRegister=(Button) findViewById(R.id.bRegister);
        final Button bBack=(Button) findViewById(R.id.bBack);

        bRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //To register
                final String name=Name.getText().toString();
                final String username=Username.getText().toString();
                final String password=Password.getText().toString();
                final String password2=Password2.getText().toString();

                if ((name.equals(""))||(username.equals(""))||(password.equals(""))||(password2.equals(""))){
                    //if a field is left empty
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Error Code")
                            .setMessage("All fields are required to fill in")
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                if((!password.equals(password2))||(password.equals(""))) {
                    if ((!name.equals("")) && (!username.equals(""))) {
                        Toast pass =Toast.makeText(RegisterActivity.this,"Password doesn't match",Toast.LENGTH_SHORT);
                        pass.show();

                    }
                }
                //when the passwords does not match


                else {
                    //if all field are filled up and the passwords match
                    String user = mMySQLiteHelper.searchuser(username);
                    if (username.equals(user)) {
                        Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    } else if (!username.equals(user)) {
                        Contact c = new Contact();
                        c.setName(name);
                        c.setPassword(password);
                        c.setUsername(username);

                        mMySQLiteHelper.insertContact(c);

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

            }
        });
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent=new Intent(RegisterActivity.this,LoginActivity.class);
                RegisterActivity.this.startActivity(backintent);
                //return to login page
            }
        });

    }
}
