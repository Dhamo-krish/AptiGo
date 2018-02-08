package com.example.hp.test.New_UI_HHS.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.test.R;

/**
 * Created by nadus on 19-10-2017.
 */

public class Admin_login extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_admin_login);

        final EditText master = (EditText) findViewById(R.id.master);
        Button master_login = (Button) findViewById(R.id.master_login);

        master_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = master.getText().toString();
                if(pass.equals("linenbomb"))
                {
                    startActivity(new Intent(Admin_login.this,Admin_Home.class));
                    Admin_login.this.finish();
                }
                else
                {
                    Toast.makeText(Admin_login.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
