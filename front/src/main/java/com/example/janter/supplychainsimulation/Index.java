package com.example.janter.supplychainsimulation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.ToastShow;


public class Index extends Activity {

    RadioGroup rg;
    TextView inChain, inSystem, inPhone;
    String node, chain, system, phone;
    Button bnMulti, bnSingle;
    RadioButton rb_retailer, rb_supplier, rb_manufacture;

    String index_URL = HttpUtil.BASE_URL+"/index.jsp";

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
        setContentView(R.layout.activity_index);


        rg = (RadioGroup)findViewById(R.id.radioGroup_index);
        inChain = (TextView)findViewById(R.id.editText_inChain);
        inSystem = (TextView)findViewById(R.id.editText_inSystem);
        inPhone = (TextView)findViewById(R.id.editText_inPhone);

        rb_retailer = (RadioButton)findViewById(R.id.radioButton_retailer);
        rb_supplier = (RadioButton)findViewById(R.id.radioButton_supplier);
        rb_manufacture = (RadioButton)findViewById(R.id.radioButton_manufacturer);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rb_retailer.isChecked())
                    node = "1";
                else if(rb_supplier.isChecked())
                    node = "2";
                else if(rb_manufacture.isChecked())
                    node = "3";
            }
        });

        bnMulti = (Button) findViewById(R.id.button_inMulti);
        bnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chain = inChain.getText().toString();
                system = inSystem.getText().toString();
                phone = inPhone.getText().toString();

                if(chain.isEmpty()||system.isEmpty()||phone.isEmpty())
                    ToastShow.showToast(Index.this,"Please fullfill all parameters");

                else {
                    final Map<String, String> params = new HashMap<String, String>();
                    params.put("node", node);
                    params.put("chain", chain);
                    params.put("system", system);
                    String message = HttpUtil.postRequest(index_URL, params);
                    if(message.trim().equals("Set successfully"))
                    {
                        Intent intent = new Intent(Index.this, Multiplayer.class);
                        Bundle b = new Bundle();
                        b.putString("node", node);
                        b.putString("chain", chain);
                        b.putString("system", system);
                        b.putString("phone", phone);
                        intent.putExtras( b );
                        startActivity(intent);
                        finish();
                    }
                    else
                    ToastShow.showToast(Index.this,message);

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index, menu);
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
