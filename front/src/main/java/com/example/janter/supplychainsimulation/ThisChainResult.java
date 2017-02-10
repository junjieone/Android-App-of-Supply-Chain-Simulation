package com.example.janter.supplychainsimulation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.TableAdapter;
import client.setListViewHeight;


public class ThisChainResult extends Activity {

    String chain, system, rounds;
    String retailer, supplier,  manufacturer, market, averageProfit;
    String thisChainResult_URL = HttpUtil.BASE_URL+"/thisChainResult.jsp";
    ListView lv;
    Button btn_thisConfirm;
    TextView ed_thisTotal;
    Double totalAverage = 0.0, totalAvePro;
    int totalRound;

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
        setContentView(R.layout.activity_this_chain_result);

        chain = getIntent().getExtras().getString("chain");
        system = getIntent().getExtras().getString("system");
        rounds = getIntent().getExtras().getString("rounds");

        lv = (ListView) this.findViewById(R.id.listView);
        final ArrayList<TableAdapter.TableRow> table = new ArrayList<TableAdapter.TableRow>();
        TableAdapter.TableCell[] titles = new TableAdapter.TableCell[6];
        titles[0] = new TableAdapter.TableCell("Round", 89, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[1] = new TableAdapter.TableCell("Market", 100, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[2] = new TableAdapter.TableCell("Retailer", 105, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[3] = new TableAdapter.TableCell("Supplier", 105, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[4] = new TableAdapter.TableCell("Manufacturer", 180, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[5] = new TableAdapter.TableCell("Average Profit", 200, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        table.add(new TableAdapter.TableRow(titles));

        int totalRound = Integer.parseInt(rounds);
        for(int i = 1; i <= totalRound; i++)
        {
            String round = String.valueOf(i);
            final Map<String, String> params = new HashMap<String, String>();
            params.put("system", system);
            params.put("chain", chain);
            params.put("round", round);
            String result = HttpUtil.postRequest(thisChainResult_URL, params);

            try {
                JSONObject jsonObject = new JSONObject(result);
                retailer = jsonObject.getString("retailer");
                supplier = jsonObject.getString("supplier");
                manufacturer = jsonObject.getString("manufacturer");
                market = jsonObject.getString("market");
                averageProfit = jsonObject.getString("averageProfit");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Double total = Double.parseDouble(averageProfit);
            TableAdapter.TableCell[] cells = new TableAdapter.TableCell[6];
            cells[0] = new TableAdapter.TableCell(i, 89, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
            cells[1] = new TableAdapter.TableCell(market, 100, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
            cells[2] = new TableAdapter.TableCell(retailer, 105, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
            cells[3] = new TableAdapter.TableCell(supplier, 105, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
            cells[4] = new TableAdapter.TableCell(manufacturer, 180, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
            cells[5] = new TableAdapter.TableCell(averageProfit, 200, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
            table.add(new TableAdapter.TableRow(cells));

            TableAdapter tableAdapter = new TableAdapter(ThisChainResult.this, table);
            lv.setAdapter(tableAdapter);
            setListViewHeight.setListViewHeight(lv);

            //加总平均值
            totalAverage = total + totalAverage;
        }
        BigDecimal b = new BigDecimal(totalAverage);
        double average = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        totalRound = Integer.parseInt(rounds);
        totalAvePro = average / totalRound;
        ed_thisTotal = (TextView)findViewById(R.id.textView_thisTotal);
        ed_thisTotal.setText(""+totalAvePro);

        btn_thisConfirm = (Button)findViewById(R.id.button_thisConfirm);
        btn_thisConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThisChainResult.this, login.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_this_chain_result, menu);
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
