package com.example.janter.supplychainsimulation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.ToastShow;


public class register_ruler extends Activity {
    EditText etPhone, etName, etPass, etEmail, etWorkId;
    Button btn_regRuleConfirm, btn_regRuleCancel;
    String register_ruler_URL = HttpUtil.BASE_URL+"/register_ruler.jsp";
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
        setContentView(R.layout.activity_register_ruler);

        etPhone = (EditText) findViewById(R.id.editText_rulephone);
        etName = (EditText) findViewById(R.id.editText_rulename);
        etPass = (EditText) findViewById(R.id.editText_rulepassword);
        etEmail = (EditText) findViewById(R.id.editText_ruleemail);
        etWorkId = (EditText) findViewById(R.id.editText_ruleworkId);

        final String id = getIntent().getExtras().getString("workId");
        etWorkId.setText(id);



        btn_regRuleConfirm = (Button) findViewById(R.id.button_regRuleConfirm);
        btn_regRuleConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = etPhone.getText().toString();
                String name = etName.getText().toString();
                String pass = etPass.getText().toString();
                String email = etEmail.getText().toString();
                String workId = id;

                final Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phone);
                params.put("name", name);
                params.put("pass", pass);
                params.put("email", email);
                params.put("workId", workId);

                String message = HttpUtil.postRequest(register_ruler_URL, params);
                if(message.trim().equals("Congratulations! Register successfully"))
                {
                    ToastShow.showToast(register_ruler.this, message);
                    Intent intent = new Intent(register_ruler.this, login.class);
                    startActivity(intent);
                    finish();
                }
                else ToastShow.showToast(register_ruler.this, message);

            }

        });
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_ruler, menu);
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
