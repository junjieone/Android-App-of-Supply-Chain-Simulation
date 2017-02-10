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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.TableAdapter;
import client.ToastShow;
import client.setListViewHeight;


public class QueryResult extends Activity {

    TextView ed_querySystem, ed_queryChain, ed_queryTotal;
    Button bnQuery, bnCancel;
    ListView lv;

    String system, chain;
    String retailer, supplier,  manufacturer, market, averageProfit;
    Double totalAverage = 0.0, totalAvePro;
    int i;

    String queryResult_URL = HttpUtil.BASE_URL+"/queryResult.jsp";
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
        setContentView(R.layout.activity_query_result);

        ed_querySystem = (TextView)findViewById(R.id.editText_querySystem);
        ed_queryChain = (TextView)findViewById(R.id.editText_queryChain);

        lv = (ListView) this.findViewById(R.id.listView_query);

        final TableAdapter.TableCell[] titles = new TableAdapter.TableCell[6];
        titles[0] = new TableAdapter.TableCell("Round", 89, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[1] = new TableAdapter.TableCell("Market", 100, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[2] = new TableAdapter.TableCell("Retailer", 105, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[3] = new TableAdapter.TableCell("Supplier", 105, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[4] = new TableAdapter.TableCell("Manufacturer", 180, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);
        titles[5] = new TableAdapter.TableCell("Average Profit", 200, WindowManager.LayoutParams.FILL_PARENT, TableAdapter.TableCell.STRING);

        ArrayList<TableAdapter.TableRow> table = new ArrayList<TableAdapter.TableRow>();
        table.add(new TableAdapter.TableRow(titles));
        TableAdapter tableAdapter1 = new TableAdapter(QueryResult.this, table);
        lv.setAdapter(tableAdapter1);
        setListViewHeight.setListViewHeight(lv);

        bnQuery = (Button)findViewById(R.id.button_queryQuery);
        bnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                system = ed_querySystem.getText().toString();
                chain = ed_queryChain.getText().toString();
                totalAverage = 0.0;
                ArrayList<TableAdapter.TableRow> table = new ArrayList<TableAdapter.TableRow>();
                table.add(new TableAdapter.TableRow(titles));
                TableAdapter tableAdapter1 = new TableAdapter(QueryResult.this, table);
                lv.setAdapter(tableAdapter1);
                setListViewHeight.setListViewHeight(lv);

                if (system.equals("") || chain.equals("")) {
                    ToastShow.showToast(QueryResult.this, "Please choose system and chain");
                } else {
                    for (i = 1; i <= 99; i++) {
                        String round = String.valueOf(i);
                        final Map<String, String> params = new HashMap<String, String>();
                        params.put("system", system);
                        params.put("chain", chain);
                        params.put("round", round);
                        String result = HttpUtil.postRequest(queryResult_URL, params);
                        if (result.trim().equals("No Information")) {
                            ToastShow.showToast(QueryResult.this, "No Result");
                            break;
                        }
                        if (result.trim().equals("Final Round")) {
                            break;
                        } else {
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

                            TableAdapter tableAdapter = new TableAdapter(QueryResult.this, table);
                            lv.setAdapter(tableAdapter);
                            setListViewHeight.setListViewHeight(lv);

                            //加总平均值
                            totalAverage = total + totalAverage;
                        }
                    }

                    totalAvePro = totalAverage / (i - 1);
                    ed_queryTotal = (TextView) findViewById(R.id.textView_queryTotal);
                    ed_queryTotal.setText("" + totalAvePro);

                }
            }
        });

        bnCancel = (Button)findViewById(R.id.button_queryBack);
        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QueryResult.this, ChooseOperation.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_query_result, menu);
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
