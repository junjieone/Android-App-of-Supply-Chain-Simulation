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
import android.widget.AdapterView;

import client.AverageProfit;
import client.AverageRandom;
import client.TableAdapter;
import client.TableAdapter.TableCell;
import client.TableAdapter.TableRow;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client.HttpUtil;
import client.NormalRandom;
import client.ToastShow;
import client.setListViewHeight;


public class Multiplayer extends Activity {

    Button btn_multiConfirm, btn_multiRounds;
    String node,z, chain, system, phone, round = "1";
    TextView location, ed_rounds, ed_chains, ed_mean, ed_variance, ed_overstock, ed_shortage, ed_holding, ed_price, ed_decision, ed_multiRound;
    String rounds, chains, distribution,  mean, variance, overstock, shortage, cost, price, decision;
    double rand;
    int r_last = 0;
    String retailer1="", supplier1="", manufacturer1="", market1="";
    String retailer2="", supplier2="", manufacturer2="", market2="";
    String retailerOverstock="", retailerShortage="", retailerHolding="", retailerPrice="",
            supplierOverstock="", supplierShortage="", supplierHolding="", supplierPrice="",
            manufacturerOverstock="", manufacturerShortage="", manufacturerHolding="", manufacturerPrice="";
    ListView lv;



    String multi_URL = HttpUtil.BASE_URL+"/multiplayer.jsp";
    String decision_URL = HttpUtil.BASE_URL+"/decision.jsp";
    String round_URL = HttpUtil.BASE_URL+"/round.jsp";
    String table_URL = HttpUtil.BASE_URL+"/table.jsp";
    String average_URL = HttpUtil.BASE_URL+"/average.jsp";



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
        setContentView(R.layout.activity_multiplayer);

        z = getIntent().getExtras().getString("node");
        chain = getIntent().getExtras().getString("chain");
        system = getIntent().getExtras().getString("system");
        phone = getIntent().getExtras().getString("phone");

        if(z.equals("1")) node = "Retailer";
        else if(z.equals("2")) node = "Supplier";
        else node = "Manufacturer";

        location = (TextView)findViewById(R.id.textView_location);
        location.setText("You are the "+node+" of chain "+chain);
        ed_multiRound = (TextView)findViewById(R.id.textView_round);
        ed_multiRound.setText(round);

        ed_rounds = (TextView)findViewById(R.id.editText_multiRounds);
        ed_chains = (TextView)findViewById(R.id.editText_multiChains);
        ed_mean = (TextView)findViewById(R.id.editText_multiMean);
        ed_variance = (TextView)findViewById(R.id.editText_multiVar);
        ed_overstock = (TextView)findViewById(R.id.editText_multiOverstock);
        ed_shortage = (TextView)findViewById(R.id.editText_multiShortage);
        ed_holding = (TextView)findViewById(R.id.editText_multiCost);
        ed_price = (TextView)findViewById(R.id.editText_multiPrice);

        ed_decision = (TextView)findViewById(R.id.editText_multiDecision);

        //初始化表格
        lv = (ListView) this.findViewById(R.id.listView);
        final ArrayList<TableRow> table = new ArrayList<TableRow>();
        TableCell[] titles = new TableCell[6];
        titles[0] = new TableCell("Round", 89, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
        titles[1] = new TableCell("Market", 100, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
        titles[2] = new TableCell("Retailer", 105, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
        titles[3] = new TableCell("Supplier", 105, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
        titles[4] = new TableCell("Manufacturer", 180, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
        titles[5] = new TableCell("Average Profit", 200, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
        table.add(new TableRow(titles));
        TableAdapter tableAdapter = new TableAdapter(Multiplayer.this, table);
        lv.setAdapter(tableAdapter);







        //根据phone提取管理者设定的参数
        final Map<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("node", node);
        String result = HttpUtil.postRequest(multi_URL, params);
        //解析JSON
        try {
            JSONObject jsonObject = new JSONObject(result);
            rounds = jsonObject.getString("rounds");
            chains = jsonObject.getString("chains");
            distribution = jsonObject.getString("distribution");
            mean = jsonObject.getString("mean");
            variance = jsonObject.getString("variance");
            overstock = jsonObject.getString("overstock");
            shortage = jsonObject.getString("shortage");
            cost = jsonObject.getString("cost");
            price = jsonObject.getString("price");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //显示参数
        ed_rounds.setText(rounds);
        ed_chains.setText(chains);
        ed_mean.setText(mean);
        ed_variance.setText(variance);
        ed_overstock.setText(overstock);
        ed_shortage.setText(shortage);
        ed_holding.setText(cost);
        ed_price.setText(price);


        btn_multiConfirm = (Button) findViewById(R.id.button_multiConfirm);
        btn_multiConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //获取决策值
                decision = ed_decision.getText().toString();

                final Map<String, String> params = new HashMap<String, String>();
                if(node.equals("Retailer") && distribution.equals("1"))
                {
                    Double miu = Double.parseDouble(mean);
                    Double sigma = Double.parseDouble(variance);
                    rand = NormalRandom.normalRandom(miu, sigma);
                    rand = (int)(rand*100);
                    String random = String.valueOf(rand);
                    params.put("random", random);
                }
                if(node.equals("Retailer") && distribution.equals("2"))
                {
                    Double p1 = Double.parseDouble(mean);
                    Double p2 = Double.parseDouble(variance);
                    rand = AverageRandom.averageRandom(p1, p2);
                    rand = (int)(rand*100);
                    String random = String.valueOf(rand);
                    params.put("random", random);
                }
                params.put("decision", decision);
                params.put("system", system);
                params.put("chain", chain);
                params.put("round", round);
                params.put("node", node);
                String message = HttpUtil.postRequest(decision_URL, params);
                ToastShow.showToast(Multiplayer.this,message);
            }
        }
        );



        btn_multiRounds = (Button) findViewById(R.id.button_multiRounds);
        btn_multiRounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String, String> params = new HashMap<String, String>();
                params.put("system", system);
                params.put("chain", chain);
                params.put("round", round);
                String message = HttpUtil.postRequest(round_URL, params);

                if(message.trim().equals("This Round Has not finished"))
                {
                    ToastShow.showToast(Multiplayer.this, message);
                }
                int r = Integer.parseInt(round);
                int r_rounds = Integer.parseInt(rounds);

                if(r_last == (r_rounds+2))
                {
                    ToastShow.showToast(Multiplayer.this, "Simulation is complete");
                    Intent intent = new Intent(Multiplayer.this, ThisChainResult.class);
                    Bundle b = new Bundle();
                    b.putString("chain", chain);
                    b.putString("system", system);
                    b.putString("rounds", rounds);
                    intent.putExtras( b );
                    startActivity(intent);
                    finish();
                }
                if(message.trim().equals("Next Round is available"))
                {
                    r = r + 1;
                    if(r <= r_rounds) {
                        ToastShow.showToast(Multiplayer.this, message);
                        String round1 = Integer.toString(r);
                        round = round1;
                        ed_multiRound.setText(round);

                        //发送请求提取decision数据
                        final Map<String, String> params_table = new HashMap<String, String>();
                        params_table.put("system", system);
                        params_table.put("chain", chain);
                        params_table.put("round", round);
                        params_table.put("phone", phone);
                        String result = HttpUtil.postRequest(table_URL, params_table);

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            retailer1 = jsonObject.getString("retailer1");
                            supplier1 = jsonObject.getString("supplier1");
                            manufacturer1 = jsonObject.getString("manufacturer1");
                            market1 = jsonObject.getString("market1");
                            retailer2 = jsonObject.getString("retailer2");
                            supplier2 = jsonObject.getString("supplier2");
                            manufacturer2 = jsonObject.getString("manufacturer2");
                            market2 = jsonObject.getString("market2");

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

                        //计算上轮次平均利润
                        Double averageProfit = AverageProfit.calculateProfit(retailer1, supplier1, manufacturer1, market1,
                                retailer2, supplier2, manufacturer2, market2,
                                retailerOverstock, retailerShortage, retailerHolding, retailerPrice,
                                supplierOverstock, supplierShortage, supplierHolding, supplierPrice,
                                manufacturerOverstock, manufacturerShortage, manufacturerHolding, manufacturerPrice);
                        BigDecimal b = new BigDecimal(averageProfit);
                        double average = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                        //将平均利润存入decision表格
                        String averagePro = Double.toString(average);
                        final Map<String, String> params_average = new HashMap<String, String>();
                        params_table.put("system", system);
                        params_table.put("chain", chain);
                        params_table.put("round", round);
                        params_table.put("averagePro", averagePro);
                        HttpUtil.postRequest(average_URL, params_table);

                        int round0 = Integer.parseInt(round);
                        String round_table = Integer.toString(round0 - 1);

                        TableCell[] cells = new TableCell[6];
                        cells[0] = new TableCell(round_table, 89, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[1] = new TableCell(market2, 100, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[2] = new TableCell(retailer2, 105, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[3] = new TableCell(supplier2, 105, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[4] = new TableCell(manufacturer2, 180, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[5] = new TableCell(average, 200, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        table.add(new TableRow(cells));

                        TableAdapter tableAdapter = new TableAdapter(Multiplayer.this, table);
                        lv.setAdapter(tableAdapter);
                        setListViewHeight.setListViewHeight(lv);
                    }
                    else if(r == (r_rounds+1))
                    {
                        String round1 = Integer.toString(r);
                        round = round1;
                        final Map<String, String> params_table = new HashMap<String, String>();
                        params_table.put("system", system);
                        params_table.put("chain", chain);
                        params_table.put("round", round);
                        params_table.put("phone", phone);
                        String result = HttpUtil.postRequest(table_URL, params_table);

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            retailer1 = jsonObject.getString("retailer1");
                            supplier1 = jsonObject.getString("supplier1");
                            manufacturer1 = jsonObject.getString("manufacturer1");
                            market1 = jsonObject.getString("market1");
                            retailer2 = jsonObject.getString("retailer2");
                            supplier2 = jsonObject.getString("supplier2");
                            manufacturer2 = jsonObject.getString("manufacturer2");
                            market2 = jsonObject.getString("market2");

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

                        //计算上轮次平均利润
                        Double averageProfit = AverageProfit.calculateProfit(retailer1, supplier1, manufacturer1, market1,
                                retailer2, supplier2, manufacturer2, market2,
                                retailerOverstock, retailerShortage, retailerHolding, retailerPrice,
                                supplierOverstock, supplierShortage, supplierHolding, supplierPrice,
                                manufacturerOverstock, manufacturerShortage, manufacturerHolding, manufacturerPrice);
                        BigDecimal b = new BigDecimal(averageProfit);
                        double average = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                        //将平均利润存入decision表格
                        String averagePro = Double.toString(average);
                        final Map<String, String> params_average = new HashMap<String, String>();
                        params_table.put("system", system);
                        params_table.put("chain", chain);
                        params_table.put("round", round);
                        params_table.put("averagePro", averagePro);
                        HttpUtil.postRequest(average_URL, params_table);

                        int round0 = Integer.parseInt(round);
                        String round_table = Integer.toString(round0 - 1);

                        TableCell[] cells = new TableCell[6];
                        cells[0] = new TableCell(round_table, 89, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[1] = new TableCell(market2, 100, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[2] = new TableCell(retailer2, 105, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[3] = new TableCell(supplier2, 105, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[4] = new TableCell(manufacturer2, 180, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        cells[5] = new TableCell(average, 200, WindowManager.LayoutParams.FILL_PARENT, TableCell.STRING);
                        table.add(new TableRow(cells));

                        TableAdapter tableAdapter = new TableAdapter(Multiplayer.this, table);
                        lv.setAdapter(tableAdapter);
                        setListViewHeight.setListViewHeight(lv);

                        r_last = r_rounds+2;
                    }

                }


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiplayer, menu);
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
