<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,java.sql.*"
 %>

<% 
String password = "";
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
String pass = request.getParameter("pass");


String sql = "select password from ruler where phone ="+phone;
rs = stmt.executeQuery(sql);

if(rs.next()) {password = rs.getString(1);}

if(password.equals(""))
out.print("Unexisted User");
else if(password.equals(pass))
out.print("Welcome");
else out.print("Wrong Password");


			

%>