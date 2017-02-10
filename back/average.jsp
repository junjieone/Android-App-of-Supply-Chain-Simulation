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

String round = request.getParameter("round");
String chain = request.getParameter("chain");
String system = request.getParameter("system");
String averageProfit = request.getParameter("averagePro");

String d = "";


int r = Integer.parseInt(round);
int c = Integer.parseInt(chain);
int s = Integer.parseInt(system);

int id = (r-1)*1+c*100+s*10000;

String sql = "update decision set averageProfit ='"+averageProfit+"' where decisionID ="+id;
stmt.executeUpdate(sql);

%>