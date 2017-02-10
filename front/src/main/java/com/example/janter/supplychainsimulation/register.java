package com.example.janter.supplychainsimulation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.ToastShow;


public class register extends Activity {

    EditText etPhone, etName, etPass, etEmail;
    Button btn_regConfirm, btn_regCancel;
    String register_URL = HttpUtil.BASE_URL+"/register.jsp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etPhone = (EditText)findViewById(R.id.editText_phone);
        etName = (EditText)findViewById(R.id.editText_name);
        etPass = (EditText)findViewById(R.id.editText_password);
        etEmail = (EditText)findViewById(R.id.editText_email);

        btn_regConfirm = (Button) findViewById(R.id.button_regConfirm);
        btn_regConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = etPhone.getText().toString();
                String name = etName.getText().toString();
                String pass = etPass.getText().toString();
                String email = etEmail.getText().toString();

                final Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone);
                params.put("name", name);
                params.put("pass", pass);
                params.put("email", email);

                String message = HttpUtil.postRequest(register_URL, params);
                if(message.trim().equals("Congratulations! Register successfully"))
                {
                    ToastShow.showToast(register.this, message);
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                    finish();
                }
                else ToastShow.showToast(register.this, message);



            }
        });

        btn_regCancel = (Button) findViewById(R.id.button_regCancel);
        btn_regCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
