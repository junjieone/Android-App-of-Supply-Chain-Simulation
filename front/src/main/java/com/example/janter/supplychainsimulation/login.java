package com.example.janter.supplychainsimulation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.methods.HttpPost;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.Information;
import client.ToastShow;


public class login extends Activity {

    Button bnLogin, bnRegister;
    String login_URL = HttpUtil.BASE_URL+"/login.jsp";
    String login_ruler_URL = HttpUtil.BASE_URL+"/login_ruler.jsp";
    String verification_URL = HttpUtil.BASE_URL+"/verification.jsp";
    int u;
    RadioGroup rg_u;
    RadioButton rb_user, rb_ruler;

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
        setContentView(R.layout.activity_login);

        /*rg_u = (RadioGroup)findViewById(R.id.radiogroup_u);
        rg_u.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton_user)
                    u = 1;
                else if (i == R.id.radioButton_ruler)
                    u = 2;
            }
        });*/


        rb_user = (RadioButton)findViewById(R.id.radioButton_user);
        rb_ruler = (RadioButton)findViewById(R.id.radioButton_ruler);
        rg_u = (RadioGroup)findViewById(R.id.radiogroup_u);
        rg_u.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rb_user.isChecked())
                    u = 1;
                else if (rb_ruler.isChecked())
                    u = 2;
            }
        });




        bnLogin = (Button) findViewById(R.id.button_logLogin);
        bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(u == 1) {
                    EditText etLogPhone = (EditText) findViewById(R.id.editText_logPhone);
                    EditText etLogPassword = (EditText) findViewById(R.id.editText_logPassword);

                    String logPhone = etLogPhone.getText().toString();
                    String logPass = etLogPassword.getText().toString();

                    final Map<String, String> params = new HashMap<String, String>();
                    params.put("phone", logPhone);
                    params.put("pass", logPass);

                    String message = HttpUtil.postRequest(login_URL, params);

                    ToastShow.showToast(login.this, message);

                    if (message.trim().equals("Welcome")) {
                        Intent intent = new Intent(login.this, Index.class);
                        startActivity(intent);
                        finish();
                    }
                }
                if(u == 2){
                    EditText etLogPhone = (EditText) findViewById(R.id.editText_logPhone);
                    EditText etLogPassword = (EditText) findViewById(R.id.editText_logPassword);

                    String logPhone = etLogPhone.getText().toString();
                    String logPass = etLogPassword.getText().toString();

                    final Map<String, String> params = new HashMap<String, String>();
                    params.put("phone", logPhone);
                    params.put("pass", logPass);

                    String message = HttpUtil.postRequest(login_ruler_URL, params);

                    ToastShow.showToast(login.this, message);

                    if (message.trim().equals("Welcome")) {
                        Information.setPhone(logPhone);
                        Intent intent = new Intent(login.this, ChooseOperation.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
        bnRegister = (Button) findViewById(R.id.button_logRegister);
        bnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u == 1) {
                    Intent intent = new Intent(login.this, register.class);
                    startActivity(intent);
                    finish();
                }
                if(u == 2){
                    final View loginDialog = getLayoutInflater().inflate(
                            R.layout.dialog_rulerreg, null);
                    new AlertDialog.Builder(login.this)
                            .setView(loginDialog)
                            .setPositiveButton("Confirm",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            final String workId = ((EditText) loginDialog
                                                    .findViewById(R.id.WorkId)).getText()
                                                    .toString();
                                            final String dialogName = ((EditText) loginDialog
                                                    .findViewById(R.id.dialog_name)).getText()
                                                    .toString();

                                            final Map<String, String> params = new HashMap<String, String>();
                                            params.put("workId", workId);
                                            params.put("dialogName", dialogName);
                                            String message = HttpUtil.postRequest(verification_URL, params);


                                            if (message.trim().equals("Existing Faculty")) {
                                                Intent intent = new Intent(login.this, register_ruler.class);
                                                Bundle b = new Bundle();
                                                b.putString("workId", workId);
                                                //此处使用putExtras，接受方就响应的使用getExtra
                                                intent.putExtras( b );
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                ToastShow.showToast(login.this, message);
                                            }


                                        }
                                    }).setNegativeButton("Cancel", null).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
