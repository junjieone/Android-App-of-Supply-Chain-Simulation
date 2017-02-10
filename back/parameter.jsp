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
String rounds = request.getParameter("rounds");
String chains = request.getParameter("chains");
String distribution = request.getParameter("distribution");
String mean = request.getParameter("mean");
String variance = request.getParameter("variance");
String rOverstock = request.getParameter("rOverstock");
String rShortage = request.getParameter("rShortage");
String rHolding = request.getParameter("rHolding");
String rPrice = request.getParameter("rPrice");
String sOverstock = request.getParameter("sOverstock");
String sShortage = request.getParameter("sShortage");
String sHolding = request.getParameter("sHolding");
String sPrice = request.getParameter("sPrice");
String mOverstock = request.getParameter("mOverstock");
String mShortage = request.getParameter("mShortage");
String mHolding = request.getParameter("mHolding");
String mPrice = request.getParameter("mPrice");



if (rounds.equals("")||chains.equals("")||distribution.equals("")||mean.equals("")||variance.equals("")||rOverstock.equals("")||rShortage.equals("")||rHolding.equals("")||rPrice.equals("")||sOverstock.equals("")||sShortage.equals("")||sHolding.equals("")||sPrice.equals("")||mOverstock.equals("")||mShortage.equals("")||mHolding.equals("")||mPrice.equals(""))
{
	
	out.print("Please fill in all the parameters");
}
else
	{String sql = "insert into parameter (`phone`, `rounds`, `chains`, `distribution`, `mean`, `variance`, `retailerOverstock`, `retailerShortage`, `retailerHolding`, `retailerPrice`, `supplierOverstock`, `supplierShortage`, `supplierHolding`, `supplierPrice`, `manufacturerOverstock`, `manufacturerShortage`, `manufacturerHolding`, `manufacturerPrice`) values('"+phone+"','"+rounds+"','"+chains+"','"+distribution+"','"+mean+"','"+variance+"','"+rOverstock+"','"+rShortage+"','"+rHolding+"','"+rPrice+"','"+sOverstock+"','"+sShortage+"','"+sHolding+"','"+sPrice+"','"+mOverstock+"','"+mShortage+"','"+mHolding+"','"+mPrice+"')";
	stmt.executeUpdate(sql);
	out.print("Set successfully");}

%>