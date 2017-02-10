package com.example.janter.supplychainsimulation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ChooseOperation extends Activity {

    Button bnManage, bnQuery, bnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_operation);



        bnManage = (Button)findViewById(R.id.button_chooseManage);
        bnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //phone = getIntent().getExtras().getString("phone");
                Intent intent = new Intent(ChooseOperation.this, index_ruler.class);
                startActivity(intent);
                finish();
            }
        });

        bnQuery = (Button)findViewById(R.id.button_chooseQuery);
        bnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseOperation.this, QueryResult.class);
                startActivity(intent);
                finish();
            }
        });

        bnExit = (Button)findViewById(R.id.button_chooseExit);
        bnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseOperation.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_operation, menu);
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
