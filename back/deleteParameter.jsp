<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,java.sql.*"
 %>

<% 
String workId = "";
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
String workID = request.getParameter("workID");

String sql1 = "select workID from ruler where phone ="+phone;
rs = stmt.executeQuery(sql1);
if(rs.next()) {workId = rs.getString(1);}

if(workID.equals(workId))
{
String sql = "delete from parameter where phone ="+phone;
stmt.executeUpdate(sql);
out.print("Delete Successfully");
}
else out.print("Wrong phone number or workID");
	

%>

