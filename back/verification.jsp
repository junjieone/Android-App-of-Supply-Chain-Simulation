<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,java.sql.*"
 %>

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

String workId = request.getParameter("workId");
String dialogName = request.getParameter("dialogName");


String sql = "select name from verification where workID ="+workId;
rs = stmt.executeQuery(sql);

if(rs.next()) {name = rs.getString(1);}

if(name.equals(dialogName))
out.print("Existing Faculty");
else 
out.print("Inexistent Faculty");


%>

