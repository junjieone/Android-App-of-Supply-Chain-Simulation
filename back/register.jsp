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

String phone = request.getParameter("phone");
String name = request.getParameter("name");
String pass = request.getParameter("pass");
String email = request.getParameter("email");
if (phone.equals("")||name.equals("")||pass.equals("")||email.equals(""))
{
	out.print("Please fullfill your information");
}
else
	{String sql = "insert into user (`phoneNum`, `name`, `password`, `email`) values('"+phone+"','"+name+"','"+pass+"','"+email+"')";
	stmt.executeUpdate(sql);
	out.print("Congratulations! Register successfully");}


			

%>


 
 