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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.ToastShow;


public class ChangeParameter extends Activity {

    EditText etRounds, etChains, etMean, etVariance, etROverstock, etRShortage, etRHolding, etRPrice,
            etSOverstock, etSShortage, etSHolding, etSPrice, etMOverstock, etMShortage, etMHolding, etMPrice;
    String retailerOverstock="", retailerShortage="", retailerHolding="", retailerPrice="",
            supplierOverstock="", supplierShortage="", supplierHolding="", supplierPrice="",
            manufacturerOverstock="", manufacturerShortage="", manufacturerHolding="", manufacturerPrice="";

    String rounds, chains, distribution, mean, variance;
    RadioGroup rg_d;
    RadioButton rb_uniform, rb_mean;
    Button bnChangeUpdate, bnChangeCancel;

    String showParameter_URL = HttpUtil.BASE_URL+"/showParameter.jsp";
    String changeParameter_URL = HttpUtil.BASE_URL+"/changeParameter.jsp";
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
        setContentView(R.layout.activity_change_parameter);

        final String phone = getIntent().getExtras().getString("phone");

        etRounds = (EditText) findViewById(R.id.editText_changeRounds);
        etChains = (EditText) findViewById(R.id.editText_changeChains);
        etMean = (EditText) findViewById(R.id.editText_changeMean);
        etVariance = (EditText) findViewById(R.id.editText_changeVariance);
        etROverstock = (EditText) findViewById(R.id.editText_retailerOverstock);
        etRShortage = (EditText) findViewById(R.id.editText_retailerShortage);
        etRHolding = (EditText) findViewById(R.id.editText_retailerHolding);
        etRPrice = (EditText) findViewById(R.id.editText_retailerPrice);
        etSOverstock = (EditText) findViewById(R.id.editText_supplierOverstock);
        etSShortage = (EditText) findViewById(R.id.editText_supplierShortage);
        etSHolding = (EditText) findViewById(R.id.editText_supplierHolding);
        etSPrice = (EditText) findViewById(R.id.editText_supplierPrice);
        etMOverstock = (EditText) findViewById(R.id.editText_manufacturerOverstock);
        etMShortage = (EditText) findViewById(R.id.editText_manufacturerShortage);
        etMHolding = (EditText) findViewById(R.id.editText_manufacturerHolding);
        etMPrice = (EditText) findViewById(R.id.editText_manufacturerPrice);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("phone",phone);
        String result = HttpUtil.postRequest(showParameter_URL, params);

        try {
            JSONObject jsonObject = new JSONObject(result);
            rounds = jsonObject.getString("rounds");
            chains = jsonObject.getString("chains");
            distribution = jsonObject.getString("distribution");
            mean = jsonObject.getString("mean");
            variance = jsonObject.getString("variance");

            retailerOverstock = jsonObject.getString("retailerOverstock");
            retailerShortage = jsonObject.getString("retailerShortage");
            retailerHolding = jsonObject.getString("retailerHolding");
            retailerPrice = jsonObject.getString("retailerPrice");

            supplierOverstock = jsonObject.getString("supplierOverstock");
            supplierShortage = jsonObject.getString("supplierShortage");
            supplierHolding = jsonObject.getString("supplierHolding");
            supplierPrice = jsonObject.getString("supplierPrice");

            manufacturerOverstock = jsonObject.getString("manufacturerOverstock");
            manufacturerShortage = jsonObject.getString("manufacturerShortage");
            manufacturerHolding = jsonObject.getString("manufacturerHolding");
            manufacturerPrice = jsonObject.getString("manufacturerPrice");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        etRounds.setText(rounds);
        etChains.setText(chains);
        etMean.setText(mean);
        etVariance.setText(variance);

        etROverstock.setText(retailerOverstock);
        etRShortage.setText(retailerShortage);
        etRHolding.setText(retailerHolding);
        etRPrice.setText(retailerPrice);

        etSOverstock.setText(supplierOverstock);
        etSShortage.setText(supplierShortage);
        etSHolding.setText(supplierHolding);
        etSPrice.setText(supplierPrice);

        etMOverstock.setText(manufacturerOverstock);
        etMShortage.setText(manufacturerShortage);
        etMHolding.setText(manufacturerHolding);
        etMPrice.setText(manufacturerPrice);

        rb_uniform = (RadioButton)findViewById(R.id.radioButton_uniform);
        rb_mean = (RadioButton)findViewById(R.id.radioButton_mean);
        if(distribution.equals("1")) rb_uniform.setChecked(true);
        else rb_mean.setChecked(true);

        rg_d = (RadioGroup)findViewById(R.id.radioGroup_changeDistribution);
        rg_d.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_uniform.isChecked())
                    distribution = "1";
                else if (rb_mean.isChecked())
                    distribution = "2";
            }
        });
        bnChangeUpdate = (Button)findViewById(R.id.button_changeUpdate);
        bnChangeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rounds = etRounds.getText().toString();
                String chains = etChains.getText().toString();
                String mean = etMean.getText().toString();
                String variance = etVariance.getText().toString();
                String rOverstock = etROverstock.getText().toString();
                String rShortage = etRShortage.getText().toString();
                String rHolding = etRHolding.getText().toString();
                String rPrice = etRPrice.getText().toString();
                String sOverstock = etSOverstock.getText().toString();
                String sShortage = etSShortage.getText().toString();
                String sHolding = etSHolding.getText().toString();
                String sPrice = etSPrice.getText().toString();
                String mOverstock = etMOverstock.getText().toString();
                String mShortage = etMShortage.getText().toString();
                String mHolding = etMHolding.getText().toString();
                String mPrice = etMPrice.getText().toString();

                final Map<String, String> params = new HashMap<String, String>();
                params.put("phone",phone);
                params.put("rounds",rounds);
                params.put("chains",chains);
                params.put("distribution",distribution);
                params.put("mean",mean);
                params.put("variance",variance);
                params.put("rOverstock",rOverstock);
                params.put("rShortage",rShortage);
                params.put("rHolding",rHolding);
                params.put("rPrice",rPrice);
                params.put("sOverstock",sOverstock);
                params.put("sShortage",sShortage);
                params.put("sHolding",sHolding);
                params.put("sPrice",sPrice);
                params.put("mOverstock",mOverstock);
                params.put("mShortage",mShortage);
                params.put("mHolding",mHolding);
                params.put("mPrice",mPrice);

                String message = HttpUtil.postRequest(changeParameter_URL, params);
                ToastShow.showToast(ChangeParameter.this, message);
            }
        });

        bnChangeCancel = (Button)findViewById(R.id.button_changeCancel);
        bnChangeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeParameter.this, index_ruler.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_parameter, menu);
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
