package com.example.janter.supplychainsimulation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.Information;
import client.ToastShow;


public class index_ruler extends Activity {

    EditText etRounds, etChains, etMean, etVariance, etROverstock, etRShortage, etRHolding, etRPrice,
    etSOverstock, etSShortage, etSHolding, etSPrice, etMOverstock, etMShortage, etMHolding, etMPrice;
    Button bnConfirm, bnCancel, bnDelete, bnChange;
    RadioGroup rg_d;
    RadioButton rb_uniform, rb_mean;
    String distribution;
    String parameter_URL = HttpUtil.BASE_URL+"/parameter.jsp";
    String delete_URL = HttpUtil.BASE_URL+"/deleteParameter.jsp";
    String changeVerification_URL = HttpUtil.BASE_URL+"/changeVerification.jsp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_ruler);

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
        final String phone = Information.getPhone();

        etRounds = (EditText) findViewById(R.id.editText_inRuleRounds);
        etChains = (EditText) findViewById(R.id.editText_inRuleChains);
        etMean = (EditText) findViewById(R.id.editText_inRuleMean);
        etVariance = (EditText) findViewById(R.id.editText_inRuleVariance);
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

        rb_uniform = (RadioButton)findViewById(R.id.radioButton_uniform);
        rb_mean = (RadioButton)findViewById(R.id.radioButton_mean);
        rg_d = (RadioGroup)findViewById(R.id.radioGroup_inRuleDistribution);
        rg_d.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_uniform.isChecked())
                    distribution = "1";
                else if (rb_mean.isChecked())
                    distribution = "2";
            }
        });

        bnConfirm = (Button)findViewById(R.id.button_inRuleConfirm);
        bnConfirm.setOnClickListener(new View.OnClickListener() {
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

                String message = HttpUtil.postRequest(parameter_URL, params);
                ToastShow.showToast(index_ruler.this, message);
            }
        });

        bnCancel = (Button)findViewById(R.id.button_inRuleCancel);
        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(index_ruler.this, ChooseOperation.class);
                startActivity(intent);
                finish();
            }
        });

        bnDelete = (Button)findViewById(R.id.button_inRuleDelete);
        bnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View loginDialog = getLayoutInflater().inflate(
                        R.layout.dialog_deleteparameter, null);
                new AlertDialog.Builder(index_ruler.this)
                        .setView(loginDialog)
                        .setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        final String phone = ((EditText) loginDialog
                                                .findViewById(R.id.delete_phone)).getText()
                                                .toString();
                                        final String workID = ((EditText) loginDialog
                                                .findViewById(R.id.delete_workID)).getText()
                                                .toString();

                                        final Map<String, String> params = new HashMap<String, String>();
                                        params.put("phone", phone);
                                        params.put("workID", workID);
                                        String message = HttpUtil.postRequest(delete_URL, params);

                                        ToastShow.showToast(index_ruler.this, message);

                                    }
                                }).setNegativeButton("Cancel", null).show();
            }
        });

        bnChange = (Button)findViewById(R.id.button_inRuleChange);
        bnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View loginDialog = getLayoutInflater().inflate(
                        R.layout.dialog_changparameter, null);
                new AlertDialog.Builder(index_ruler.this)
                        .setView(loginDialog)
                        .setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        final String phone = ((EditText) loginDialog
                                                .findViewById(R.id.change_phone)).getText()
                                                .toString();

                                        final Map<String, String> params = new HashMap<String, String>();
                                        params.put("phone", phone);
                                        String message = HttpUtil.postRequest(changeVerification_URL, params);
                                        if(message.trim().equals("Show Parameter"))
                                        {
                                            Intent intent = new Intent(index_ruler.this, ChangeParameter.class);
                                            Bundle b = new Bundle();
                                            b.putString("phone", phone);
                                            intent.putExtras( b );
                                            startActivity(intent);
                                            finish();
                                        }
                                        else ToastShow.showToast(index_ruler.this, message);

                                    }
                                }).setNegativeButton("Cancel", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index_ruler, menu);
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
