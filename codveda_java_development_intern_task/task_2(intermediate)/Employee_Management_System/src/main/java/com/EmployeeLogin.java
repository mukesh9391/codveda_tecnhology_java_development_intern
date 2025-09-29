package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EmployeeLogin extends GenericServlet
{

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException 
	{
	    String emailid = 	servletRequest.getParameter("emailid");
		  String tempdeptno = servletRequest.getParameter("deptno");
		  int deptno = Integer.parseInt(tempdeptno);
		  String select ="select * from employee_details where employee_emailid=? and Employee_Dept_No=?";
		  try {
          	Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection	= DriverManager.getConnection("jdbc:mysql://localhost:3306/teca_64_advance_java?user=root&password=12345");
			PreparedStatement preparedStatement = connection.prepareStatement(select);
			preparedStatement.setString(1, emailid);
			preparedStatement.setDouble(2, deptno);
			  ResultSet resultset = preparedStatement.executeQuery();
			  PrintWriter writer = servletResponse.getWriter();
			  servletResponse.setContentType("text/html");
			  if (resultset.next()) 
			  {
				writer.println("<center><h3>"+resultset.getString("Employee_Name") +" Login successfully</h3></center>");
				writer.println("<center>"
						+" <table border=\"1\">"
						+ "        <tr>"
						+ "          <th>Employee Name</th>"
						+ "          <th>Employee Dept No</th>"
						+ "          <th>Employee Salary</th>"
						+ "          <th>Employee Email id</th>"
						+ "        </tr>"
						+ "        <tr>"
						+ "          <td>"+resultset.getString("Employee_Name")+"</td>"
						+ "          <td>"+resultset.getInt("Employee_Dept_No")+"</td>"
						+ "          <td>"+resultset.getDouble("Employee_Salary")+"</td>"
						+ "          <td>"+resultset.getString("employee_emailid")+"</td>"
						+ "        </tr>"
						+ "      </table>"
						+ "</center>"
					);
			}
			  else
			  {
				  writer.println("<center><h3>Invalid Data</h3></center>");
				   
			  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
