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


String round = request.getParameter("round");
String chain = request.getParameter("chain");
String system = request.getParameter("system");

String decisionID="", retailer="", supplier="", manufacturer="", market="", averageProfit="";

int r = Integer.parseInt(round);
int c = Integer.parseInt(chain);
int s = Integer.parseInt(system);

int id = r*1+c*100+s*10000;

String sql1 = "select * from decision where decisionID ="+id;
rs = stmt.executeQuery(sql1);
if(rs.next()) {
	decisionID = rs.getString(1);
	retailer = rs.getString(2);
	supplier = rs.getString(3);
	manufacturer = rs.getString(4);
	market = rs.getString(5);
	averageProfit = rs.getString(6);
	}
	if(round.equals("1")&&decisionID.equals(""))
	{
		out.print("No Information");
	}
	else if(decisionID.equals(""))
	{
		out.print("Final Round");
	}
	else
	{
	JSONObject obj=new JSONObject();
	obj.put("retailer",retailer);
	obj.put("supplier",supplier);
	obj.put("manufacturer",manufacturer);
	obj.put("market",market);
	obj.put("averageProfit",averageProfit);
	out.print(obj);
	out.flush();
	}

%>