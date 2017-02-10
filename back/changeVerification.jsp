<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,java.sql.*"
 %>

<% 
String phoneNumber = "";
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


String sql = "select phone from parameter where phone ="+phone;
rs = stmt.executeQuery(sql);

if(rs.next()) {phoneNumber = rs.getString(1);}

if(phoneNumber.equals(""))
out.print("No Parameter");
else out.print("Show Parameter");


%>

