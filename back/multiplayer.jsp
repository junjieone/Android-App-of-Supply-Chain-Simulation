<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="org.json.simple.JSONObject,java.io.*,java.sql.*"%>

<%
String name = "";
Connection con;
Statement stmt;
ResultSet rs;
Class.forName("com.mysql.jdbc.Driver");
String dbUrl ="jdbc:mysql://localhost:3306/supplychain?useUnicode=true&characterEncoding=GB2312";
String dbUser="root";
String dbPwd="19920804wjj.";
con=java.sql.DriverManager.getConnection(dbUrl,dbUser,dbPwd);
stmt = con.createStatement();

String phone = request.getParameter("phone");
String node = request.getParameter("node");;

String sql = "select * from parameter where phone ="+phone;
rs = stmt.executeQuery(sql);

String rounds="", chains="", distribution="", mean="", variance="", retailerOverstock="", retailerShortage="", retailerHolding="", retailerPrice="", supplierOverstock="", supplierShortage="", supplierHolding="", supplierPrice="", manufacturerOverstock="", manufacturerShortage="", manufacturerHolding="", manufacturerPrice="";
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
obj.put("rounds",rounds);
obj.put("chains",chains);
obj.put("mean",mean);
obj.put("variance",variance);
obj.put("distribution",distribution);
if(node.equals("Retailer"))
{
	obj.put("overstock",retailerOverstock);
	obj.put("shortage",retailerShortage);
	obj.put("cost",retailerHolding);
	obj.put("price",retailerPrice);
	out.print(obj);
    out.flush();
}
else if(node.equals("Supplier"))
{
	obj.put("overstock",supplierOverstock);
	obj.put("shortage",supplierShortage);
	obj.put("cost",supplierHolding);
	obj.put("price",supplierPrice);
	out.print(obj);
    out.flush();
}
else
{
	obj.put("overstock",manufacturerOverstock);
	obj.put("shortage",manufacturerShortage);
	obj.put("cost",manufacturerHolding);
	obj.put("price",manufacturerPrice);
	out.print(obj);
    out.flush();
}



			
    /*obj.put("name","foo");
    obj.put("num",id);
    obj.put("balance",new Double(1000.21));
    obj.put("is_vip",new Boolean(true));
    obj.put("nickname",name);*/
    
%>