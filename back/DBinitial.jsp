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

for(int s = 1; s<11; s++)
{
for(int c = 1; c<101; c++)
{
for(int n = 1; n<4; n++)
{
	int x = s*10000+c*10+n;
String sql = "insert into node (`nodeID`, `status`) values('"+x+"','0')";
	stmt.executeUpdate(sql);
	}
}
}



%>