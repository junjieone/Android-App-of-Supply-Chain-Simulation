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
String chain = request.getParameter("chain");
String system = request.getParameter("system");
String status = "";

int n = Integer.parseInt(node);
int c = Integer.parseInt(chain);
int s = Integer.parseInt(system);
int id = n*1+c*10+s*10000;


String sql1 = "select status from node where nodeID ="+id;
rs = stmt.executeQuery(sql1);
if(rs.next()) {status = rs.getString(1);}

if(status.equals("0"))
{
	String sql2 = "update node set status = '1' where nodeID ="+id;
	stmt.executeUpdate(sql2);
	out.print("Set successfully");
}
else out.print("This node is chosen by others");


%>