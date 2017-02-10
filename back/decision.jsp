<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,java.sql.*"
 %>
 
 
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
String decision = request.getParameter("decision");
String random = request.getParameter("random");

String d = "";


int r = Integer.parseInt(round);
int c = Integer.parseInt(chain);
int s = Integer.parseInt(system);

int id = r*1+c*100+s*10000;

if(node.equals("Retailer"))
{
	String sql = "insert into decision (`decisionID`, `retailer`, `market`) values('"+id+"','"+decision+"','"+random+"')";
		stmt.executeUpdate(sql);
	out.print("Successful Decision");
}

if(node.equals("Supplier"))
{
	String sql1 = "select retailer from decision where decisionID ="+id;
	rs = stmt.executeQuery(sql1);
    if(rs.next()) {d = rs.getString(1);}
	if(d.equals(""))
	{
		out.print("Retailer has not finished deciding");
	}
	else
	{
		String sql2 = "update decision set supplier ='"+decision+"' where decisionID ="+id;
	stmt.executeUpdate(sql2);
	out.print("Set successfully");
	}
}

if(node.equals("Manufacturer"))
{
	String sql1 = "select supplier from decision where decisionID ="+id;
	rs = stmt.executeQuery(sql1);
    if(rs.next()) {d = rs.getString(1);}
	
	if(d == null) out.print("Supplier has not finished deciding");
	else if(d.equals("")) out.print("Retailer has not finished deciding");
	else
	{
		String sql2 = "update decision set manufacturer ='"+decision+"' where decisionID ="+id;
	stmt.executeUpdate(sql2);
	out.print("Set successfully");
	}
}


%>