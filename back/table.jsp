<%@ page contentType="text/html;charset=gbk"%>
<%@page import="org.json.simple.JSONObject,java.io.*,java.sql.*"%>
 
<% 
Connection con;
Statement stmt;
ResultSet rs;
Class.forName("com.mysql.jdbc.Driver");
String dbUrl ="jdbc:mysql://localhost:3306/supplychain?useUnicode=true&characterEncoding=GB2312";
String dbUser="root";
String dbPwd="19920804wjj.";
con=java.sql.DriverManager.getConnection(dbUrl,dbUser,dbPwd);
stmt = con.createStatement();


String node = request.getParameter("node");
String round = request.getParameter("round");
String chain = request.getParameter("chain");
String system = request.getParameter("system");
String phone = request.getParameter("phone");

String decisionID1="", retailer1="", supplier1="", manufacturer1="", market1="", averageProfit1="";
String decisionID2="", retailer2="", supplier2="", manufacturer2="", market2="", averageProfit2="";
String rounds="", chains="", distribution="", mean="", variance="", retailerOverstock="", retailerShortage="", retailerHolding="", retailerPrice="", supplierOverstock="", supplierShortage="", supplierHolding="", supplierPrice="", manufacturerOverstock="", manufacturerShortage="", manufacturerHolding="", manufacturerPrice="";

int r = Integer.parseInt(round);
int c = Integer.parseInt(chain);
int s = Integer.parseInt(system);

int id1 = (r-2)*1+c*100+s*10000;

String sql1 = "select * from decision where decisionID ="+id1;
rs = stmt.executeQuery(sql1);
if(rs.next()) {
decisionID1 = rs.getString(1);
retailer1 = rs.getString(2);
supplier1 = rs.getString(3);
manufacturer1 = rs.getString(4);
market1 = rs.getString(5);
averageProfit1 = rs.getString(6);
}

int id2 = (r-1)*1+c*100+s*10000;
String sql2 = "select * from decision where decisionID ="+id2;
rs = stmt.executeQuery(sql2);
if(rs.next()) {
decisionID2 = rs.getString(1);
retailer2 = rs.getString(2);
supplier2 = rs.getString(3);
manufacturer2 = rs.getString(4);
market2 = rs.getString(5);
averageProfit2 = rs.getString(6);
}

String sql3 = "select * from parameter where phone ="+phone;
rs = stmt.executeQuery(sql3);
if(rs.next()) {
phone = rs.getString(1);
rounds = rs.getString(2);
chains = rs.getString(3);
distribution = rs.getString(4);
mean = rs.getString(5);
variance = rs.getString(6);
retailerOverstock = rs.getString(7);
retailerShortage = rs.getString(8);
retailerHolding = rs.getString(9);
retailerPrice = rs.getString(10);
supplierOverstock = rs.getString(11);
supplierShortage = rs.getString(12);
supplierHolding = rs.getString(13);
supplierPrice = rs.getString(14);
manufacturerOverstock = rs.getString(15);
manufacturerShortage = rs.getString(16);
manufacturerHolding = rs.getString(17);
manufacturerPrice = rs.getString(18);
}



JSONObject obj=new JSONObject();
obj.put("retailer1",retailer1);
obj.put("supplier1",supplier1);
obj.put("manufacturer1",manufacturer1);
obj.put("market1",market1);
obj.put("retailer2",retailer2);
obj.put("supplier2",supplier2);
obj.put("manufacturer2",manufacturer2);
obj.put("market2",market2);
obj.put("retailerOverstock",retailerOverstock);
obj.put("retailerShortage",retailerShortage);
obj.put("retailerHolding",retailerHolding);
obj.put("retailerPrice",retailerPrice);
obj.put("supplierOverstock",supplierOverstock);
obj.put("supplierShortage",supplierShortage);
obj.put("supplierHolding",supplierHolding);
obj.put("supplierPrice",supplierPrice);
obj.put("manufacturerOverstock",manufacturerOverstock);
obj.put("manufacturerShortage",manufacturerShortage);
obj.put("manufacturerHolding",manufacturerHolding);
obj.put("manufacturerPrice",manufacturerPrice);
out.print(obj);
out.flush();
%>